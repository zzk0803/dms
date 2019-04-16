package zzk.project.dms.ui.dormitory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataCommunicator;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataProvider;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zzk.project.dms.domain.DormitoryManageException;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.entities.DormitorySpaceType;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.DormitorySpaceService;
import zzk.project.dms.domain.services.TenementService;
import zzk.project.dms.domain.utilies.Dormitories;

import java.util.Collection;
import java.util.List;

@Configuration
public class BeanConfigurationForDormitoryView {

    @Autowired
    DormitorySpaceService dormitorySpaceService;

    @Autowired
    TenementService tenementService;

    @Autowired
    DormitoryHierarchicalDataProvider dormitoryHierarchicalDataProvider;

    //--------------------------------------------------------------------------------
    //------------                       宿舍管理页面                             -----------------
    //--------------------------------------------------------------------------------

    @Bean
    @UIScope
    public TreeGrid<DormitorySpace> spaceTreeGrid() {
        TreeGrid<DormitorySpace> spaceTreeGrid = new TreeGrid<>();
        spaceTreeGrid.addHierarchyColumn(DormitorySpace::getName).setHeader("名称&编号").setFlexGrow(1).setResizable(true);
        spaceTreeGrid.setSelectionMode(Grid.SelectionMode.NONE);
        spaceTreeGrid.addColumn(space -> space.getType().getCn()).setHeader("层级").setFlexGrow(1).setResizable(true);
        spaceTreeGrid.addColumn(space -> space.isOperational() ? "已启用" : "已停用").setHeader("是否启用").setFlexGrow(1).setResizable(true);
        spaceTreeGrid.addColumn(space -> space.isAvailable() ? "可用" : "已占用").setHeader("是否占用").setFlexGrow(1).setResizable(true);
        spaceTreeGrid.addColumn(DormitorySpace::getCapacity).setHeader("容积").setFlexGrow(1).setResizable(true);
        spaceTreeGrid.addColumn(DormitorySpace::getHasDivided).setHeader("已分割").setFlexGrow(1).setResizable(true);
        spaceTreeGrid.addColumn(DormitorySpace::getHasOccupy).setHeader("已被住户占有").setFlexGrow(1).setResizable(true);
        spaceTreeGrid.addComponentColumn(selectSpace -> {
            HorizontalLayout group = new HorizontalLayout();
            group.setAlignItems(FlexComponent.Alignment.BASELINE);

            //编辑
            Button edit = new Button(VaadinIcon.EDIT.create(), click -> {
                spaceTreeGrid.getParent().ifPresent(component -> {
                    if (component instanceof DormitoryView) {
                        DormitoryView dormitoryView = (DormitoryView) component;
                        dormitoryView.getEditDialog().warp(selectSpace);
                        dormitoryView.getEditDialog().open();
                    }
                });
            });
            edit.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SMALL);
            group.add(edit);

            //划分
            if (selectSpace.getType().hasSmaller() && selectSpace.getHasDivided() < selectSpace.getCapacity()) {
                Button divide = new Button(VaadinIcon.ROAD_SPLIT.create(), click -> {
                    Dialog dialog = new Dialog();
                    dialog.setCloseOnEsc(true);
                    dialog.setCloseOnOutsideClick(true);

                    spaceTreeGrid.getParent().ifPresent(component -> {
                        if (component instanceof DormitoryView) {
                            DormitoryView dormitoryView = (DormitoryView) component;
                            TreeGrid<DormitorySpace> treeGrid = dormitoryView.getSpaceTreeGrid();
                            buildDivideDialog(selectSpace, dialog, treeGrid);
                        }
                    });

                    dialog.open();
                });
                divide.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SMALL);
                group.add(divide);
            }

            //查看住户人员
            Button search = new Button(VaadinIcon.SEARCH.create(), click -> {
                Dialog dialog = buildInspectTenementDialog(selectSpace);
                dialog.open();
            });
            search.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SMALL);
            group.add(search);

            //删除
            Button delete = new Button(VaadinIcon.CLOSE_CIRCLE.create(), click -> {
                if (!dormitorySpaceService.hasChildSpace(selectSpace)) {
                    if (tenementService.countTenementInSpace(selectSpace) == 0) {
                        dormitorySpaceService.delete(selectSpace);
                        spaceTreeGrid.getParent().ifPresent(component -> {
                            if (component instanceof DormitoryView) {
                                spaceTreeGrid.getDataProvider().refreshAll();
                                spaceTreeGrid.getDataCommunicator().reset();
                            }
                        });
                    } else {
                        Notification.show("该区域还有住宿的人员，请妥善处理住宿的人员后（如迁移），再删除该区域", 5000, Notification.Position.MIDDLE);
                    }
                } else {
                    Notification.show("该区域还有子区域，请先删除未有住户占有的子区域后，再删除该区域", 5000, Notification.Position.MIDDLE);
                }
            });
            delete.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
            group.add(delete);
            return group;
        }).setHeader("可用操作").setFlexGrow(1).setResizable(true);
        spaceTreeGrid.setDataProvider(dormitoryHierarchicalDataProvider);
        spaceTreeGrid.setWidth("100%");
        return spaceTreeGrid;
    }

    private Dialog buildInspectTenementDialog(DormitorySpace selectSpace) {
        Dialog dialog = new Dialog();
        dialog.setWidth("50vw");
        VerticalLayout layout = new VerticalLayout();
        layout.add(new H3("占用空间的用户"));
        Grid<Tenement> tenementGrid = new Grid<>();
        tenementGrid.addColumn(Tenement::getName).setHeader("姓名").setFlexGrow(1).setResizable(true);
        tenementGrid.addColumn(tenement -> Dormitories.getFullName(tenement.getDormitorySpace())).setHeader("位置").setFlexGrow(1).setResizable(true);
        Collection<Tenement> tenements = tenementService.findTenementBySpace(selectSpace);
        tenementGrid.setSizeUndefined();
        tenementGrid.setItems(tenements);
        layout.add(tenementGrid);
        if (selectSpace.getHasOccupy() != tenements.size()) {
            Button fixButton = new Button("尝试修复");
            fixButton.addClickListener(fixClick -> {
                dialog.close();
            });
            layout.add(fixButton);
        }
        dialog.addComponentAsFirst(layout);
        return dialog;
    }

    private void buildDivideDialog(DormitorySpace dormitorySpace,
                                   Dialog dialog,
                                   TreeGrid<DormitorySpace> treeGrid) {
        HierarchicalDataProvider<DormitorySpace, SerializablePredicate<DormitorySpace>> dataProvider = treeGrid.getDataProvider();
        HierarchicalDataCommunicator<DormitorySpace> dataCommunicator = treeGrid.getDataCommunicator();

        VerticalLayout container = new VerticalLayout();
        H4 header = new H4(String.format("从%s划分出%s", dormitorySpace.getName(), dormitorySpace.getType().smaller().getCn()));
        container.add(header);

        RadioButtonGroup<DormitoryDivideApproach> approachRadioButtonGroup = new RadioButtonGroup<>();
        approachRadioButtonGroup.setLabel("分割方法");
        approachRadioButtonGroup.setItems(DormitoryDivideApproach.values());
        approachRadioButtonGroup.setRenderer(new TextRenderer<DormitoryDivideApproach>() {
            @Override
            public Component createComponent(DormitoryDivideApproach dormitoryDivideApproach) {
                return new Text(dormitoryDivideApproach.getCn());
            }
        });
        container.add(approachRadioButtonGroup);

        int remain = dormitorySpace.getCapacity() - dormitorySpace.getHasDivided();
        NumberField numberField = new NumberField();
        numberField.setVisible(false);
        numberField.setPreventInvalidInput(true);
        numberField.setMin(1);
        numberField.setMax(remain);
        numberField.setPlaceholder(String.format("%d~%d", 1, remain));
        container.add(numberField);

        approachRadioButtonGroup.addValueChangeListener(valueChange -> {
            numberField.setVisible(true);
        });

        Button okButton = new Button("提交");
        okButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        okButton.addClickListener(clickEvent -> {
            DormitoryDivideApproach approach = approachRadioButtonGroup.getValue();
            Double value = numberField.getValue();
            if (approach != null && value != null) {
                try {
                    List<DormitorySpace> dormitorySpaces;
                    switch (approach) {
                        case Number:
                            dormitorySpaces = dormitorySpaceService.allocateFromParentByExplicitNumberByEqualization(dormitorySpace, value.intValue());
                            dormitorySpaces.forEach(dataProvider::refreshItem);
                            dataProvider.refreshAll();
                            dataCommunicator.reset();
                            break;

                        case Capacity:
                            DormitorySpace newSpace = dormitorySpaceService.allocateFromParentByExplicitCapacity(dormitorySpace, value.intValue());
                            dataProvider.refreshItem(dormitorySpace);
                            dataProvider.refreshItem(newSpace);
                            dataProvider.refreshAll();
                            dataCommunicator.reset();
                            break;

                        case CapacityEqualization:
                            dormitorySpaces = dormitorySpaceService.allocateFromParentByExplicitAllocationByEqualization(dormitorySpace, value.intValue());
                            dormitorySpaces.forEach(dataProvider::refreshItem);
                            dataProvider.refreshAll();
                            dataCommunicator.reset();
                            break;

                        default:
                            break;
                    }
                } catch (DormitoryManageException e) {
                    Notification.show(e.getMessage(), 3000, Notification.Position.MIDDLE);
                }
                dialog.close();
            } else {
                Notification.show("方法或数字未填", 3000, Notification.Position.MIDDLE);
            }
        });
        container.add(okButton);
        dialog.addComponentAsFirst(container);
    }

    @Bean
    @UIScope
    public HorizontalLayout controlPanel(@Autowired @Qualifier("createSpaceButton") Button createSpaceButton) {
        HorizontalLayout controlPanel = new HorizontalLayout();
        controlPanel.add(createSpaceButton);
        return controlPanel;
    }

    @Bean
    @UIScope
    public Button createSpaceButton() {
        Button createSpaceButton = new Button("新建宿舍空间");
        createSpaceButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return createSpaceButton;
    }

    @Bean
    @UIScope
    public H1 headerH1() {
        return new H1(DormitoryView.VIEW_TITLE);
    }

    @Bean
    @UIScope
    public DormitoryEditForm dormitoryEditForm() {
        return new DormitoryEditForm(
                dormitorySpaceService,
                spaceBinder(),
                spaceNameField(),
                capacityField(),
                availableCheckbox(),
                operationalCheckbox(),
                spaceTypeComboBox(),
                upperSpaceComboBox()
        );
    }

    //--------------------------------------------------------------------------------
    //------------                       住户管理编辑对话框                             -----------------
    //--------------------------------------------------------------------------------

    @Bean
    @UIScope
    public DormitoryEditDialog dormitoryEditDialog() {
        return new DormitoryEditDialog(
                dormitoryEditForm(),
                commitButton(),
                giveUpButton()
        );
    }

    @Bean
    @UIScope
    public Button giveUpButton() {
        Button giveUpButton = new Button("放弃并关闭");
        giveUpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        giveUpButton.getStyle().set("color", "red");
        return giveUpButton;
    }

    @Bean
    @UIScope
    public Binder<DormitorySpace> spaceBinder() {
        return new Binder<>();
    }

    @Bean
    @UIScope
    public TextField spaceNameField() {
        TextField spaceNameField = new TextField("名称", "请输入名称或编号");
        spaceNameField.setRequired(true);
        spaceNameField.setRequiredIndicatorVisible(true);
        return spaceNameField;
    }

    @Bean
    @UIScope
    public TextField capacityField() {
        TextField capacityField = new TextField("容积", "最大容纳人数");
        capacityField.setRequiredIndicatorVisible(true);
        return capacityField;
    }

    @Bean
    @UIScope
    public Checkbox availableCheckbox() {
        return new Checkbox("设置为可用（未满）");
    }

    @Bean
    @UIScope
    public Checkbox operationalCheckbox() {
        return new Checkbox("设置为启用");
    }

    @Bean
    @UIScope
    public ComboBox<DormitorySpaceType> spaceTypeComboBox() {
        ComboBox<DormitorySpaceType> spaceTypeComboBox = new ComboBox<>("空间类型");
        spaceTypeComboBox.setItems(DormitorySpaceType.values());
        spaceTypeComboBox.setItemLabelGenerator(DormitorySpaceType::getCn);
        return spaceTypeComboBox;
    }

    @Bean
    @UIScope
    public ComboBox<DormitorySpace> upperSpaceComboBox() {
        ComboBox<DormitorySpace> upperSpaceComboBox = new ComboBox<>("上层空间");
        upperSpaceComboBox.setVisible(false);
        upperSpaceComboBox.setItemLabelGenerator(DormitorySpace::getName);
        return upperSpaceComboBox;
    }

    @Bean
    @UIScope
    public Button commitButton() {
        Button commit = new Button("提交");
        commit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return commit;
    }
}
