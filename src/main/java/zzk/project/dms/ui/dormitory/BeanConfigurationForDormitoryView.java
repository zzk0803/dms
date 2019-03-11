package zzk.project.dms.ui.dormitory;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.entities.DormitorySpaceType;
import zzk.project.dms.domain.services.DormitorySpaceService;

@Configuration
public class BeanConfigurationForDormitoryView {

    @Autowired
    DormitorySpaceService dormitorySpaceService;

    @Autowired
    DormitoryHierarchicalDataProvider dormitoryHierarchicalDataProvider;

    @Bean
    @UIScope
    public TreeGrid<DormitorySpace> spaceTreeGrid() {
        TreeGrid<DormitorySpace> spaceTreeGrid = new TreeGrid<>();
        spaceTreeGrid.setDataProvider(dormitoryHierarchicalDataProvider);
        spaceTreeGrid.addHierarchyColumn(DormitorySpace::getName).setHeader("名称&编号");
        spaceTreeGrid.addColumn(space -> space.getType().getCn()).setHeader("层级").setFlexGrow(0);
        spaceTreeGrid.addColumn(space -> space.isOperational() ? "已启用" : "已停用").setHeader("是否启用").setFlexGrow(0);
        spaceTreeGrid.addColumn(space -> space.isAvailable() ? "可用" : "已占用").setHeader("是否占用").setFlexGrow(0);
        spaceTreeGrid.addColumn(DormitorySpace::getCapacity).setHeader("容积");
        spaceTreeGrid.addColumn(DormitorySpace::getHasDivided).setHeader("已分派");
        spaceTreeGrid.addColumn(DormitorySpace::getHasOccupy).setHeader("已被住户占有");
        spaceTreeGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        return spaceTreeGrid;
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
