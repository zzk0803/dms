package zzk.project.dms.ui.finance;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import org.springframework.beans.factory.annotation.Qualifier;
import zzk.project.dms.domain.entities.FinancialRecord;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.FinancialRecordService;
import zzk.project.dms.domain.services.common.FormSupportService;

import java.math.BigDecimal;
import java.util.function.Supplier;

public class FinanceRecordEditForm extends VerticalLayout {
    private ComboBox<Tenement> tenementComboBox;
    private DatePicker recordDateDatePicker;
    private TextField checkInField;
    private TextArea descriptionArea;
    private Checkbox markCheckBox;

    private FinancialRecordService financialRecordService;
    private FinancialRecord editEntity;
    private FinancialRecord completedEntity;
    private Binder<FinancialRecord> entityBinder;
    private boolean commitSuccess;

    public FinanceRecordEditForm(
            @Qualifier("financialRecordBinder") Binder<FinancialRecord> financialRecordBinder,
            @Qualifier("tenementComboBox") ComboBox<Tenement> tenementComboBox,
            @Qualifier("recordDateDatePicker") DatePicker recordDateDatePicker,
            @Qualifier("checkInField") TextField checkInField,
            @Qualifier("descriptionArea") TextArea descriptionArea,
            @Qualifier("markCheckBox") Checkbox markCheckBox,
            FinancialRecordService financialRecordService) {
        this.entityBinder = financialRecordBinder;
        this.tenementComboBox = tenementComboBox;
        this.recordDateDatePicker = recordDateDatePicker;
        this.checkInField = checkInField;
        this.descriptionArea = descriptionArea;
        this.markCheckBox = markCheckBox;
        this.financialRecordService = financialRecordService;
        configure();
    }

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

    protected void configureUIEvent() {

    }

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

    public FormSupportService<FinancialRecord, ?> getFormSupportService() {
        return this.financialRecordService;
    }

    public Supplier<FinancialRecord> getBlackEntitySupplier() {
        return FinancialRecord::new;
    }

    public boolean doCommit() {
        try {
            getEntityBinder().writeBean(getEditEntity());
            setCompletedEntity(getEditEntity());
            getFormSupportService().save(getCompletedEntity());
            setCommitSuccess(true);
        } catch (ValidationException e) {
            Notification.show(e.getMessage());
            setCommitSuccess(false);
        }
        return isCommitSuccess();
    }

    public void doAbort() {
        setEditEntity(produceBlackBean());
        getEntityBinder().setBean(getEditEntity());
    }

    private FinancialRecord produceBlackBean() {
        return getBlackEntitySupplier().get();
    }

    protected void configure() {
        configureUI(this);
        configureBinging(getEntityBinder());
        setEditEntity(produceBlackBean());
        getEntityBinder().setBean(getEditEntity());
        configureUIEvent();
    }

    public VerticalLayout getRootLayout() {
        return this;
    }

    public Binder<FinancialRecord> getEntityBinder() {
        return entityBinder;
    }

    public FinancialRecord getEditEntity() {
        return editEntity;
    }

    public void setEditEntity(FinancialRecord editEntity) {
        this.editEntity = editEntity;
    }

    public FinancialRecord getCompletedEntity() {
        return completedEntity;
    }

    public void setCompletedEntity(FinancialRecord completedEntity) {
        this.completedEntity = completedEntity;
    }

    public boolean isCommitSuccess() {
        return commitSuccess;
    }

    public void setCommitSuccess(boolean commitSuccess) {
        this.commitSuccess = commitSuccess;
    }
}
