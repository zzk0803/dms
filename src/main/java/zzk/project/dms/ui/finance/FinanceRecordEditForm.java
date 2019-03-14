package zzk.project.dms.ui.finance;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.function.ValueProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import zzk.project.dms.domain.entities.FinancialRecord;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.FinancialRecordService;
import zzk.project.dms.domain.services.FormSupportService;
import zzk.project.dms.ui.common.AbstractEntityEditForm;

import java.math.BigDecimal;
import java.util.function.Supplier;

public class FinanceRecordEditForm extends AbstractEntityEditForm<FinancialRecord> {
    private ComboBox<Tenement> tenementComboBox;
    private DatePicker recordDateDatePicker;
    private TextField checkInField;
    private TextArea descriptionArea;
    private Checkbox markCheckBox;

    private FinancialRecordService financialRecordService;

    @Autowired
    public FinanceRecordEditForm(
            @Qualifier("financialRecordBinder") Binder<FinancialRecord> financialRecordBinder,
            @Qualifier("tenementComboBox") ComboBox<Tenement> tenementComboBox,
            @Qualifier("recordDateDatePicker") DatePicker recordDateDatePicker,
            @Qualifier("checkInField") TextField checkInField,
            @Qualifier("descriptionArea") TextArea descriptionArea,
            @Qualifier("markCheckBox") Checkbox markCheckBox,
            FinancialRecordService financialRecordService) {
        super(financialRecordBinder);
        this.tenementComboBox = tenementComboBox;
        this.recordDateDatePicker = recordDateDatePicker;
        this.checkInField = checkInField;
        this.descriptionArea = descriptionArea;
        this.markCheckBox = markCheckBox;
        this.financialRecordService = financialRecordService;
        configure();
    }

    @Override
    protected void configureUI(VerticalLayout rootLayout) {
        rootLayout.add(
                new HorizontalLayout(
                        tenementComboBox,
                        checkInField),
                recordDateDatePicker,
                descriptionArea,
                markCheckBox
        );
        descriptionArea.setWidth("100%");
        expand(descriptionArea);
        recordDateDatePicker.setWidth("100%");
        expand(recordDateDatePicker);
    }

    @Override
    protected void configureUIEvent() {

    }

    @Override
    protected void configureBinging(Binder<FinancialRecord> entityBinder) {
        entityBinder.forField(tenementComboBox)
                .asRequired("必须填写住户")
                .bind(FinancialRecord::getTenement, FinancialRecord::setTenement);
        entityBinder.forField(checkInField)
                .asRequired("必须填写收取金额")
                .withConverter(new Converter<String, BigDecimal>() {
                    @Override
                    public Result<BigDecimal> convertToModel(String input, ValueContext valueContext) {
                        try {
                            return Result.ok(BigDecimal.valueOf(Double.valueOf(input)));
                        } catch (NumberFormatException e) {
                            return Result.error("请输入数字");
                        }
                    }

                    @Override
                    public String convertToPresentation(BigDecimal bigDecimal, ValueContext valueContext) {
                        return String.valueOf(bigDecimal);
                    }
                })
                .bind(FinancialRecord::getCheckIn, FinancialRecord::setCheckIn);
        entityBinder.forField(recordDateDatePicker)
                .asRequired("必须填写结算日期")
                .bind(FinancialRecord::getRecordDate, FinancialRecord::setRecordDate);
        entityBinder.forField(descriptionArea)
                .bind(FinancialRecord::getDescription, FinancialRecord::setDescription);
        entityBinder.forField(markCheckBox)
                .bind(FinancialRecord::isMark, FinancialRecord::setMark);
    }

    @Override
    public FormSupportService<FinancialRecord, ?> getFormSupportService() {
        return this.financialRecordService;
    }

    @Override
    public Supplier<FinancialRecord> getBlackEntitySupplier() {
        return FinancialRecord::new;
    }
}
