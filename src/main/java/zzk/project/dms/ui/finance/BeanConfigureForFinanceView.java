package zzk.project.dms.ui.finance;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zzk.project.dms.domain.entities.FinancialRecord;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.FinancialRecordService;
import zzk.project.dms.domain.services.TenementService;
import zzk.project.dms.domain.utilies.Dormitories;
import zzk.project.dms.ui.tenement.TenementNameFilterAndPageableDataProvider;

@Configuration
public class BeanConfigureForFinanceView {

    @Autowired
    private FinanceRecordDataProvider financeRecordDataProvider;

    @Autowired
    private TenementService tenementService;

    @Autowired
    private FinancialRecordService financialRecordService;

    //--------------------------------------------------------------------------------
    //------------                       财务管理页面                             -----------------
    //--------------------------------------------------------------------------------

    @Bean
    @UIScope
    public Button addRecordButton() {
        Button addRecordButton = new Button("新增一条记录");
        addRecordButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return addRecordButton;
    }

    @Bean
    @UIScope
    public Grid<FinancialRecord> financialRecordGrid() {
        Grid<FinancialRecord> financialRecordGrid = new Grid<>();
        financialRecordGrid.addColumn(financialRecord -> financialRecord.getTenement().getName())
                .setFlexGrow(1)
                .setResizable(true)
                .setWidth("2em").setHeader("住户");
        financialRecordGrid.addColumn(financialRecord -> Dormitories.getFullName(financialRecord.getTenement().getDormitorySpace()))
                .setFlexGrow(1)
                .setWidth("4em")
                .setResizable(true)
                .setHeader("入住位置");
        financialRecordGrid.addColumn(FinancialRecord::getRecordDate)
                .setFlexGrow(1)
                .setResizable(true)
                .setHeader("结算日期");
        financialRecordGrid.addColumn(FinancialRecord::getDescription)
                .setFlexGrow(1)
                .setResizable(true)
                .setHeader("备忘录");
        financialRecordGrid.addColumn(FinancialRecord::getCheckIn)
                .setFlexGrow(1)
                .setResizable(true)
                .setHeader("应收金额");
        financialRecordGrid.addColumn(record -> record.isMark() ? "已缴费" : "未缴费")
                .setFlexGrow(1)
                .setResizable(true)
                .setHeader("是否已缴费");
        financialRecordGrid.addComponentColumn(financialRecord -> {
            //编辑
            Button edit = new Button(VaadinIcon.EDIT.create(), click -> {
                financialRecordGrid.getParent().ifPresent(component -> {
                    if (component instanceof FinanceView) {
                        FinanceView financeView = (FinanceView) component;
                        financeView.getEditDialog().setEditingRecord(financialRecord);
                        financeView.getEditDialog().open();
                    }
                });
            });
            edit.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SMALL);

            //删除
            Button delete = new Button(VaadinIcon.CLOSE_CIRCLE.create(), click -> {
                financialRecordGrid.getParent().ifPresent(component -> {
                    if (component instanceof FinanceView) {
                        FinancialRecord deletedRecord = financialRecordService.delete(financialRecord);
                        FinanceView financeView = (FinanceView) component;
                        financeView.getFinancialRecordGrid().getDataProvider().refreshItem(deletedRecord);
                        financeView.getFinancialRecordGrid().getDataProvider().refreshAll();
                        financeView.getFinancialRecordGrid().getDataCommunicator().reset();
                    }
                });
            });
            delete.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
            return new HorizontalLayout(edit, delete);
        }).setHeader("可用操作").setFlexGrow(1).setResizable(true);
        financialRecordGrid.setDataProvider(financeRecordDataProvider);
        return financialRecordGrid;
    }

    //--------------------------------------------------------------------------------
    //------------                       财务表单页面                             -----------------
    //--------------------------------------------------------------------------------

    @Bean
    @UIScope
    public Binder<FinancialRecord> financialBinder() {
        return new Binder<FinancialRecord>();
    }

    @Bean
    @UIScope
    public ComboBox<Tenement> financialTenementComboBox() {
        ComboBox<Tenement> tenementComboBox = new ComboBox<>("住户");
        tenementComboBox.setDataProvider(tenementDataProviderForComboBox());
        tenementComboBox.setItemLabelGenerator(Tenement::getName);
        return tenementComboBox;
    }

    @Bean
    @UIScope
    public TenementNameFilterAndPageableDataProvider tenementDataProviderForComboBox() {
        return new TenementNameFilterAndPageableDataProvider(tenementService);
    }

    @Bean
    @UIScope
    public DatePicker financialRecordDatePicker() {
        DatePicker recordDateDatePicker = new DatePicker();
        recordDateDatePicker.setLabel("结算日期");
        return recordDateDatePicker;
    }

    @Bean
    @UIScope
    public NumberField financialCheckInField() {
        NumberField numberField = new NumberField("应收金额");
        numberField.setHasControls(true);
        numberField.setSuffixComponent(VaadinIcon.DOLLAR.create());
        return numberField;
    }

    @Bean
    @UIScope
    public TextArea financialDescriptionArea() {
        TextArea descriptionArea = new TextArea("备忘录");
        descriptionArea.setHeight("20em");
        return descriptionArea;
    }

    @Bean
    @UIScope
    public Checkbox financialMarkCheckBox() {
        return new Checkbox("是否已收款");
    }

    //--------------------------------------------------------------------------------
    //------------                       财务表单对话框                          -----------------
    //--------------------------------------------------------------------------------

    @Bean
    @UIScope
    public H4 financeRecordFormDialogHeader() {
        return new H4("新建&编辑收费记录");
    }

    @Bean
    @UIScope
    public Button financeRecordFormOkButton() {
        Button button = new Button("提交");
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }

    @Bean
    @UIScope
    public Button financeRecordFormCancelButton() {
        Button button = new Button("放弃并返回");
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        button.getStyle().set("color", "red");
        return button;
    }

}
