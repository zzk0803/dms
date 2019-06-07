package zzk.project.dms.ui.finance;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Qualifier;
import zzk.project.dms.domain.entities.FinancialRecord;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.FinancialRecordService;

import java.math.BigDecimal;

@SpringComponent
@UIScope
public class FinanceRecordEditForm extends VerticalLayout {
    private Binder<FinancialRecord> binder;
    private FinancialRecord editingRecord;
    private FinancialRecord completedRecord;
    private boolean commitSuccess;

    @PropertyId("recordDate")
    private DatePicker recordDatePicker;
    @PropertyId("tenement")
    private ComboBox<Tenement> tenementComboBox;
    @PropertyId("checkIn")
    private NumberField checkInField;
    @PropertyId("description")
    private TextArea descriptionArea;
    @PropertyId("mark")
    private Checkbox markCheckBox;

    private FinancialRecordService financialRecordService;

    public FinanceRecordEditForm(
            @Qualifier("financialBinder") Binder<FinancialRecord> binder,
            @Qualifier("financialRecordDatePicker") DatePicker recordDatePicker,
            @Qualifier("financialTenementComboBox") ComboBox<Tenement> tenementComboBox,
            @Qualifier("financialCheckInField") NumberField checkInField,
            @Qualifier("financialDescriptionArea") TextArea descriptionArea,
            @Qualifier("financialMarkCheckBox") Checkbox markCheckBox,
            FinancialRecordService financialRecordService
    ) {
        this.financialRecordService = financialRecordService;
        this.binder = binder;
        this.recordDatePicker = recordDatePicker;
        this.tenementComboBox = tenementComboBox;
        this.checkInField = checkInField;
        this.descriptionArea = descriptionArea;
        this.markCheckBox = markCheckBox;
        init();
    }

    public FinancialRecord getEditingRecord() {
        return editingRecord;
    }

    public void setEditingRecord(FinancialRecord editingRecord) {
        this.editingRecord = editingRecord;
    }

    public void setEditingRecordEmergency(FinancialRecord financialRecord) {
        setEditingRecord(financialRecord);
        this.binder.readBean(financialRecord);
    }

    public FinancialRecord getCompletedRecord() {
        return completedRecord;
    }

    public void setCompletedRecord(FinancialRecord completedRecord) {
        this.completedRecord = completedRecord;
    }

    public boolean isCommitSuccess() {
        return commitSuccess;
    }

    public void setCommitSuccess(boolean commitSuccess) {
        this.commitSuccess = commitSuccess;
    }

    private void init() {
        ui();
        binding();
        event();
    }

    private void binding() {
        setEditingRecord(new FinancialRecord());
        binder.forField(recordDatePicker)
                .asRequired("须填写结算日期")
                .bind(FinancialRecord::getRecordDate, FinancialRecord::setRecordDate);
        binder.forField(tenementComboBox)
                .asRequired("须填写住户")
                .bind(FinancialRecord::getTenement, FinancialRecord::setTenement);
        binder.forField(checkInField)
                .asRequired("须填写收费金额")
                .withConverter(new Converter<Double, BigDecimal>() {
                    @Override
                    public Result<BigDecimal> convertToModel(Double value, ValueContext context) {
                        return Result.ok(BigDecimal.valueOf(value));
                    }

                    @Override
                    public Double convertToPresentation(BigDecimal value, ValueContext context) {
                        return value.doubleValue();
                    }
                }).bind(FinancialRecord::getCheckIn, FinancialRecord::setCheckIn);
        binder.forField(descriptionArea)
                .bind(FinancialRecord::getDescription, FinancialRecord::setDescription);
        binder.forField(markCheckBox)
                .bind(FinancialRecord::isMark, FinancialRecord::setMark);
    }

    private void event() {

    }

    private void ui() {
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
        add(new HorizontalLayout(tenementComboBox, checkInField));
        add(markCheckBox);
        add(descriptionArea);
        add(recordDatePicker);
    }

    public boolean commit() {
        FinancialRecord editingRecord = getEditingRecord();
        try {
            binder.writeBean(editingRecord);
            setCompletedRecord(financialRecordService.commit(editingRecord));
            setCommitSuccess(true);
        } catch (ValidationException e) {
            setCommitSuccess(false);
        }
        return isCommitSuccess();
    }

    public void reset() {
        setCompletedRecord(null);
        setEditingRecord(new FinancialRecord());
        binder.setBean(getEditingRecord());
    }
}
