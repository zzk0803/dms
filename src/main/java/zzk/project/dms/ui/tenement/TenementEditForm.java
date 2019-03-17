package zzk.project.dms.ui.tenement;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import zzk.project.dms.domain.DormitoryManageException;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.entities.DormitorySpaceType;
import zzk.project.dms.domain.entities.TenementGender;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.TenementService;
import zzk.project.dms.domain.utilies.Dormitories;
import zzk.project.dms.ui.dormitory.DormitoryFlatDataProvider;
import zzk.project.dms.utilies.SpringBeansUtil;

import java.util.Objects;

public class TenementEditForm extends VerticalLayout {
    private Tenement editTenement;
    private Tenement completedTenement;
    private Binder<Tenement> tenementBinder;
    private TenementService tenementService;
    private boolean commitSuccess;

    //--------------------------------------------------------------------------------
    //------------                       个人基本信息                             -----------------
    //--------------------------------------------------------------------------------

    @Id("id")
    private TextField nameField;
    @Id("gender")
    private Select<TenementGender> genderSelect;
    @Id("personIdentityID")
    private TextField personIdentityIDField;
    @Id("contactMethod.houseTelephone")
    private TextField houseTelephoneField;
    @Id("contactMethod.mobilePhone")
    private TextField mobilePhone;
    @Id("contactMethod.primaryEmail")
    private EmailField primaryEmailField;

    //--------------------------------------------------------------------------------
    //------------                       租房信息                             -----------------
    //--------------------------------------------------------------------------------

    private Checkbox distributeCurrentlyCheckbox;
    private VerticalLayout moreInfoGroups;
    private Label selectBerthLabel;
    @Id("spot")
    private ComboBox<DormitorySpace> dormitorySpaceComboBox;
    @Id("startDate")
    private DatePicker tenementDateField;
    @Id("expiredDate")
    private DatePicker expireDateField;
    @Id("valid")
    private Checkbox validCheckbox;
    private DormitoryFlatDataProvider dormitoryFlatDataProvider;

    @Autowired
    public TenementEditForm(
            @Qualifier("tenementBinder") Binder<Tenement> tenementBinder,
            TenementService tenementService,
            @Qualifier("distributeCurrently") Checkbox distributeCurrentlyCheckbox,
            @Qualifier("nameField") TextField nameField,
            @Qualifier("genderSelect") Select<TenementGender> genderSelect,
            @Qualifier("personIdentityIDField") TextField personIdentityIDField,
            @Qualifier("houseTelephoneField") TextField houseTelephoneField,
            @Qualifier("mobilePhone") TextField mobilePhone,
            @Qualifier("primaryEmailField") EmailField primaryEmailField,
            @Qualifier("berthLabel") Label berthLabel,
            @Qualifier("datePicker") DatePicker tenementDateField,
            @Qualifier("datePicker") DatePicker expireDateField,
            @Qualifier("validCheckbox") Checkbox validCheckbox
    ) {

        this.tenementBinder = tenementBinder;
        this.tenementService = tenementService;
        this.distributeCurrentlyCheckbox = distributeCurrentlyCheckbox;
        this.nameField = nameField;
        this.genderSelect = genderSelect;
        this.personIdentityIDField = personIdentityIDField;
        this.houseTelephoneField = houseTelephoneField;
        this.mobilePhone = mobilePhone;
        this.primaryEmailField = primaryEmailField;
        this.selectBerthLabel = berthLabel;
        this.tenementDateField = tenementDateField;
        this.expireDateField = expireDateField;
        this.validCheckbox = validCheckbox;
        init();
    }

    private void init() {
        setWidth("100%");

        HorizontalLayout nameAndGender = new HorizontalLayout();
        nameAndGender.add(nameField, genderSelect, personIdentityIDField);
        add(nameAndGender);

        HorizontalLayout contactFields = new HorizontalLayout();
        contactFields.add(houseTelephoneField, mobilePhone, primaryEmailField);
        add(contactFields);

        add(distributeCurrentlyCheckbox);

        onEvent();
        setEditTenement(new Tenement());
        binding();
    }

    private void binding() {
        tenementBinder.setBean(getEditTenement());
        tenementBinder.forField(nameField)
                .asRequired("姓名不能为空")
                .bind(Tenement::getName, Tenement::setName);

        tenementBinder.bind(genderSelect, Tenement::getGender, Tenement::setGender);

        tenementBinder.forField(personIdentityIDField)
                .asRequired("身份证号码不能为空")
                .bind(Tenement::getPersonIdentityID, Tenement::setPersonIdentityID);

        tenementBinder.forField(houseTelephoneField)
                .bind(tenement -> tenement.getTenementContactMethod().getHouseTelephone(),
                        (tenement, telephone) -> tenement.getTenementContactMethod().setHouseTelephone(telephone)
                );

        tenementBinder.forField(mobilePhone)
                .bind(tenement -> tenement.getTenementContactMethod().getHouseTelephone(),
                        (tenement, mobile) -> tenement.getTenementContactMethod().setMobilePhone(mobile)
                );

        tenementBinder.forField(primaryEmailField)
                .bind(tenement -> tenement.getTenementContactMethod().getHouseTelephone(),
                        (tenement, email) -> tenement.getTenementContactMethod().setPrimaryEmail(email)
                );
    }

    private void advancedBinding() {
        tenementBinder.forField(dormitorySpaceComboBox)
                .withValidator((space, valueContext) -> {
                    if (space != null) {
                        if (space.getType().equals(DormitorySpaceType.BERTH)) {
                            setLabelTextAndColor("green", Dormitories.getFullName(space));
                            return ValidationResult.ok();
                        } else {
                            setLabelTextAndColor("#E26A17", "系统将自动寻找可用的床位");
                            return ValidationResult.ok();
                        }
                    } else {
                        setLabelTextAndColor("red", "还没有选择床位");
                        return ValidationResult.ok();
                    }
                })
                .bind(Tenement::getDormitorySpace, Tenement::setDormitorySpace);

        tenementBinder.forField(tenementDateField)
                .bind(Tenement::getStartDate, Tenement::setStartDate);

        tenementBinder.forField(expireDateField)
                .bind(Tenement::getExpiredDate, Tenement::setExpiredDate);

        tenementBinder.forField(validCheckbox)
                .bind(Tenement::isValid, Tenement::setValid);
    }

    private void onEvent() {
        distributeCurrentlyCheckbox.addValueChangeListener(valueChange -> {
            Boolean value = valueChange.getValue();
            if (Objects.nonNull(value) && value) {
                showTenementsFields();
            } else {
                hideTenementsFields();
            }
        });

        addAttachListener(attachEvent -> {
            tenementBinder.readBean(this.getEditTenement());
            DormitorySpace spot = getEditTenement().getDormitorySpace();
            boolean hasSpot = spot != null;
            distributeCurrentlyCheckbox.setValue(hasSpot);
            if (hasSpot) {
                setLabelTextAndColor("green", Dormitories.getFullName(spot));
            } else {
                setLabelTextAndColor("#E26A17", "还没有选择床位");
            }
        });

        addDetachListener(detachEvent -> {
            reset();
        });
    }

    private void setLabelTextAndColor(String color, String message) {
        selectBerthLabel.getStyle().set("color", color);
        selectBerthLabel.setText(message);
    }

    private void hideTenementsFields() {
        this.remove(moreInfoGroups);
        this.dormitorySpaceComboBox = null;
    }

    private void showTenementsFields() {
        this.moreInfoGroups = new VerticalLayout();
        moreInfoGroups.setWidth("100%");
        setFlexGrow(1, moreInfoGroups);
        expand(moreInfoGroups);

        moreInfoGroups.add(selectBerthLabel);

        dormitoryFlatDataProvider = (DormitoryFlatDataProvider) SpringBeansUtil.getBean(DormitoryFlatDataProvider.class);
        dormitorySpaceComboBox = new ComboBox<>(5);
        dormitorySpaceComboBox.setDataProvider(dormitoryFlatDataProvider);
        dormitorySpaceComboBox.setItemLabelGenerator(Dormitories::getFullName);
        dormitorySpaceComboBox.setWidth("100%");
        moreInfoGroups.add(dormitorySpaceComboBox, validCheckbox);
        moreInfoGroups.setFlexGrow(1, dormitorySpaceComboBox);
        moreInfoGroups.expand(dormitorySpaceComboBox);

        tenementDateField.setLabel("入住时间");
        expireDateField.setLabel("到期时间");
        HorizontalLayout dateFields = new HorizontalLayout(tenementDateField, expireDateField);
        moreInfoGroups.add(dateFields);
        moreInfoGroups.setFlexGrow(1, dateFields);
        moreInfoGroups.expand(dateFields);

        add(moreInfoGroups);
        advancedBinding();
    }

    public boolean commit() {
        try {
            tenementBinder.writeBean(getEditTenement());
            setCompletedTenement(getEditTenement());
            tenementService.distributeBerthForTenement(getCompletedTenement());
            setCommitSuccess(true);
        } catch (ValidationException e) {
            setCommitSuccess(false);
        } catch (DormitoryManageException dormitoryManageException) {
            setCommitSuccess(false);
            Notification.show(dormitoryManageException.getMessage(), 3000, Notification.Position.MIDDLE);
        }
        return isCommitSuccess();
    }

    public void reset() {
        setEditTenement(new Tenement());
        this.tenementBinder.readBean(getEditTenement());
    }

    public Tenement getEditTenement() {
        return this.editTenement;
    }

    public void setEditTenement(Tenement editTenement) {
        this.editTenement = editTenement;
    }

    public Tenement getCompletedTenement() {
        return completedTenement;
    }

    public void setCompletedTenement(Tenement completedTenement) {
        this.completedTenement = completedTenement;
    }

    public boolean isCommitSuccess() {
        return commitSuccess;
    }

    public void setCommitSuccess(boolean commitSuccess) {
        this.commitSuccess = commitSuccess;
    }
}
