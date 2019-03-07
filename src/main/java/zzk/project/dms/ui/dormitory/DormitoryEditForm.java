package zzk.project.dms.ui.dormitory;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.services.DormitoryManagementService;

import java.util.Objects;

public class DormitoryEditForm extends VerticalLayout {
    private DormitoryManagementService dormitoryManagementService;
    private Binder<DormitorySpace> spaceBinder;

    private TextField spaceNameField;
    private TextField capacityField;
    private Checkbox availableCheckbox;
    private Checkbox operationalCheckbox;
    private ComboBox<DormitorySpace.SpaceType> spaceTypeComboBox;
    private ComboBox<DormitorySpace> upperSpaceComboBox;

    private DormitorySpace editingObject = new DormitorySpace();

    @Autowired
    public DormitoryEditForm(
            DormitoryManagementService dormitoryManagementService,
            Binder<DormitorySpace> spaceBinder,
            TextField spaceNameField,
            TextField capacityField,
            Checkbox availableCheckbox,
            Checkbox operationalCheckbox,
            ComboBox<DormitorySpace.SpaceType> spaceTypeComboBox,
            ComboBox<DormitorySpace> upperSpaceComboBox
    ) {
        this.dormitoryManagementService = dormitoryManagementService;
        this.spaceBinder = spaceBinder;
        this.spaceNameField = spaceNameField;
        this.capacityField = capacityField;
        this.availableCheckbox = availableCheckbox;
        this.operationalCheckbox = operationalCheckbox;
        this.spaceTypeComboBox = spaceTypeComboBox;
        this.upperSpaceComboBox = upperSpaceComboBox;

        initUI();
        onEvent();
        configureBinding();
    }

    public DormitorySpace getEditingObject() {
        return editingObject;
    }


    public void setEditingObject(DormitorySpace editingObject) {
        this.editingObject = editingObject;
    }

    public void doPersistence() {
        DormitorySpace editingObject = this.getEditingObject();
        boolean valid = spaceBinder.writeBeanIfValid(editingObject);
        if (valid) {
            dormitoryManagementService.createOrUpdate(getEditingObject());
        }
    }

    private void configureBinding() {
        binding();
        spaceBinder.forField(spaceNameField)
                .asRequired("空间名称&编号不能为空")
                .bind(DormitorySpace::getName, DormitorySpace::setName);

        spaceBinder.forField(capacityField)
                .withConverter(new StringToIntegerConverter("必须输入数字"))
                .withValidator((value, context) -> {
                    if (value > 0) {
                        return ValidationResult.ok();
                    } else {
                        return ValidationResult.error("必须输入不小于0的数");
                    }
                })
                .bind(DormitorySpace::getCapacity, DormitorySpace::setCapacity);

        spaceBinder.forField(availableCheckbox)
                .bind(DormitorySpace::isAvailable, DormitorySpace::setAvailable);

        spaceBinder.forField(operationalCheckbox)
                .bind(DormitorySpace::isOperational, DormitorySpace::setOperational);

        spaceBinder.forField(spaceTypeComboBox)
                .bind(DormitorySpace::getType, DormitorySpace::setType);

        spaceBinder.forField(upperSpaceComboBox)
                .bind(DormitorySpace::getUpperSpace, DormitorySpace::setUpperSpace);
    }

    public void reset() {
        DormitorySpace dormitorySpace = new DormitorySpace();
        setEditingObject(dormitorySpace);
        binding();
    }

    private void binding() {
        spaceBinder.setBean(getEditingObject());
    }

    private void onEvent() {
        spaceTypeComboBox.addValueChangeListener(changed -> {
            DormitorySpace.SpaceType spaceType = changed.getValue();
            if (Objects.nonNull(spaceType) && spaceType.hasNext()) {
                ListDataProvider<DormitorySpace> spaceListDataProvider;

                DormitorySpace.SpaceType nextType = spaceType.next();
                spaceListDataProvider = new ListDataProvider<>(dormitoryManagementService.listSpaceByType(nextType));
                upperSpaceComboBox.setLabel("选择" + nextType.getCn());
                upperSpaceComboBox.setDataProvider(spaceListDataProvider);
                upperSpaceComboBox.setVisible(true);
            } else {
                upperSpaceComboBox.setVisible(false);
            }
        });
    }

    private void initUI() {
        setMargin(false);
        add(
                spaceNameField,
                capacityField,
                availableCheckbox,
                operationalCheckbox,
                spaceTypeComboBox,
                upperSpaceComboBox
        );
    }
}
