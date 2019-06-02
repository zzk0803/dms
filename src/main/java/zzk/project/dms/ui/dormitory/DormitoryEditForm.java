package zzk.project.dms.ui.dormitory;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import zzk.project.dms.domain.DormitoryManageException;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.entities.DormitorySpaceType;
import zzk.project.dms.domain.services.DormitorySpaceService;

import java.util.Objects;

public class DormitoryEditForm extends VerticalLayout {
    private Binder<DormitorySpace> dormitorySpaceBinder;

    private TextField spaceNameField;
    private TextField capacityField;
    private Checkbox availableCheckbox;
    private Checkbox operationalCheckbox;
    private ComboBox<DormitorySpaceType> spaceTypeComboBox;
    private ComboBox<DormitorySpace> upperSpaceComboBox;

    private DormitorySpace editingObject = new DormitorySpace();
    private DormitorySpace completedDormitorySpace;
    private DormitorySpaceService dormitorySpaceService;

    private boolean commitSuccess;

    @Autowired
    public DormitoryEditForm(
            DormitorySpaceService dormitorySpaceService,
            Binder<DormitorySpace> dormitorySpaceBinder,
            @Qualifier("spaceNameField") TextField spaceNameField,
            @Qualifier("capacityField") TextField capacityField,
            @Qualifier("availableCheckbox") Checkbox availableCheckbox,
            Checkbox operationalCheckbox,
            ComboBox<DormitorySpaceType> spaceTypeComboBox,
            ComboBox<DormitorySpace> upperSpaceComboBox
    ) {
        this.dormitorySpaceService = dormitorySpaceService;
        this.dormitorySpaceBinder = dormitorySpaceBinder;
        this.spaceNameField = spaceNameField;
        this.capacityField = capacityField;
        this.availableCheckbox = availableCheckbox;
        this.operationalCheckbox = operationalCheckbox;
        this.spaceTypeComboBox = spaceTypeComboBox;
        this.upperSpaceComboBox = upperSpaceComboBox;

        initUI();
        onEvent();
        binding();
    }

    public DormitorySpace getEditingObject() {
        return editingObject;
    }


    public void setEditingObject(DormitorySpace editingObject) {
        this.editingObject = editingObject;
    }

    public DormitorySpace getCompletedDormitorySpace() {
        return completedDormitorySpace;
    }

    public void setCompletedDormitorySpace(DormitorySpace completedDormitorySpace) {
        this.completedDormitorySpace = completedDormitorySpace;
    }

    public void reset() {
        setEditingObject(new DormitorySpace());
        dormitorySpaceBinder.readBean(getEditingObject());
    }

    public boolean doCommit() {
        try {
            dormitorySpaceBinder.writeBean(getEditingObject());
            setCompletedDormitorySpace(getEditingObject());
            dormitorySpaceService.save(getEditingObject());
            setCommitSuccess(true);
        } catch (ValidationException e) {
            setCommitSuccess(false);
        } catch (DormitoryManageException e) {
            Notification.show(e.getMessage(), 2000, Notification.Position.BOTTOM_END);
        }
        return isCommitSuccess();
    }

    private void binding() {
        dormitorySpaceBinder.readBean(getEditingObject());
        dormitorySpaceBinder.forField(spaceNameField)
                .asRequired("空间名称&编号不能为空")
                .bind(DormitorySpace::getName, DormitorySpace::setName);

        dormitorySpaceBinder.forField(capacityField)
                .withConverter(new StringToIntegerConverter("必须输入数字"))
                .withValidator((value, context) -> {
                    if (value > 0) {
                        return ValidationResult.ok();
                    } else {
                        return ValidationResult.error("必须输入不小于0的数");
                    }
                })
                .bind(DormitorySpace::getCapacity, DormitorySpace::setCapacity);

        dormitorySpaceBinder.forField(availableCheckbox)
                .bind(DormitorySpace::isAvailable, DormitorySpace::setAvailable);

        dormitorySpaceBinder.forField(operationalCheckbox)
                .bind(DormitorySpace::isOperational, DormitorySpace::setOperational);

        dormitorySpaceBinder.forField(spaceTypeComboBox)
                .asRequired("空间类型不能为空")
                .bind(DormitorySpace::getType, DormitorySpace::setType);

        dormitorySpaceBinder.forField(upperSpaceComboBox)
                .bind(DormitorySpace::getParent, DormitorySpace::setParent);
    }

    private void onEvent() {
        spaceTypeComboBox.addValueChangeListener(changed -> {
            DormitorySpaceType spaceType = changed.getValue();
            if (Objects.nonNull(spaceType) && spaceType.hasBigger()) {
                DormitorySpaceType nextType = spaceType.bigger();
                upperSpaceComboBox.setLabel("选择" + nextType.getCn());
                upperSpaceComboBox.setDataProvider(new ListDataProvider<DormitorySpace>(dormitorySpaceService.listSpaceByType(nextType)));
                upperSpaceComboBox.setVisible(true);
            } else {
                upperSpaceComboBox.setVisible(false);
            }
        });

        addAttachListener(attachEvent -> {
            dormitorySpaceBinder.readBean(getEditingObject());
        });
    }

    private void initUI() {
        setWidth("100%");
        add(
                spaceNameField,
                capacityField,
                availableCheckbox,
                operationalCheckbox,
                spaceTypeComboBox,
                upperSpaceComboBox
        );
    }

    public boolean isCommitSuccess() {
        return commitSuccess;
    }

    public void setCommitSuccess(boolean commitSuccess) {
        this.commitSuccess = commitSuccess;
    }
}
