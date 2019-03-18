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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import zzk.project.dms.domain.entities.FinancialRecord;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.FinancialRecordService;

import java.math.BigDecimal;

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

    @Autowired
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

        ui();
        event();
        this.editEntity = new FinancialRecord();
        binding();
    }

    private void event() {
        addAttachListener(attachEvent -> {
            entityBinder.readBean(this.editEntity);
        });
    }

    private void binding() {
        entityBinder.setBean(editEntity);
        entityBinder.forField(this.tenementComboBox)
                .asRequired("必须填写住户")
                .bind(FinancialRecord::getTenement, FinancialRecord::setTenement);
        entityBinder.forField(this.checkInField)
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
        entityBinder.forField(this.recordDateDatePicker)
                .asRequired("必须填写结算日期")
                .bind(FinancialRecord::getRecordDate, FinancialRecord::setRecordDate);
        entityBinder.forField(this.descriptionArea)
                .bind(FinancialRecord::getDescription, FinancialRecord::setDescription);
        entityBinder.forField(this.markCheckBox)
                .bind(FinancialRecord::isMark, FinancialRecord::setMark);
    }

    private void ui() {
        add(
                new HorizontalLayout(
                        this.tenementComboBox,
                        this.checkInField),
                this.recordDateDatePicker,
                this.descriptionArea,
                this.markCheckBox
        );
        this.descriptionArea.setWidth("100%");
        expand(this.descriptionArea);
        this.recordDateDatePicker.setWidth("100%");
        expand(this.recordDateDatePicker);
    }

    public boolean doCommit() {
        try {
            entityBinder.writeBean(editEntity);
            setCompletedEntity(editEntity);
            financialRecordService.save(getCompletedEntity());
            setCommitSuccess(true);
        } catch (ValidationException e) {
            setCommitSuccess(false);
            Notification.show(e.getMessage());
        }
        return isCommitSuccess();
    }

    public void reset() {
        setEditEntity(new FinancialRecord());
        entityBinder.readBean(editEntity);
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
