package zzk.project.dms.ui.finance;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import zzk.project.dms.domain.entities.FinancialRecord;

public class FinanceRecordDialog extends Dialog {
    private VerticalLayout rootLayout = new VerticalLayout();

    private H4 dialogHeader;
    private FinanceRecordEditForm entityEditForm;
    private Button okButton;
    private Button cancelButton;

    private Grid<FinancialRecord> parentViewGrid;

    @Autowired
    public FinanceRecordDialog(
            @Qualifier("financeRecordFormDialogHeader") H4 dialogHeader,
            @Qualifier("financeRecordFormEntityEditForm") FinanceRecordEditForm financeRecordEditForm,
            @Qualifier("financeRecordFormOkButton") Button okButton,
            @Qualifier("financeRecordFormCancelButton") Button cancelButton
    ) {
        this.dialogHeader = dialogHeader;
        this.entityEditForm = financeRecordEditForm;
        this.okButton = okButton;
        this.cancelButton = cancelButton;
        init();
    }

    private void init() {
        this.setCloseOnOutsideClick(false);
        this.setCloseOnEsc(false);

        rootLayout.add(dialogHeader);
        rootLayout.add(entityEditForm);
        HorizontalLayout buttonGroup = new HorizontalLayout(okButton, cancelButton);
        buttonGroup.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        rootLayout.add(buttonGroup);
        rootLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, buttonGroup);
        add(rootLayout);
        onEvent();
    }

    private void onEvent() {
        okButton.addClickListener(click -> {
            if (entityEditForm.doCommit()) {
                getParentViewGrid().getDataProvider().refreshItem(entityEditForm.getCompletedEntity());
                getParentViewGrid().getDataProvider().refreshAll();
                getParentViewGrid().getDataCommunicator().reset();
                close();
                entityEditForm.reset();
            }
        });

        cancelButton.addClickListener(click -> {
            close();
            entityEditForm.reset();
        });
    }

    public Grid<FinancialRecord> getParentViewGrid() {
        return parentViewGrid;
    }

    public void setParentViewGrid(Grid<FinancialRecord> parentViewGrid) {
        this.parentViewGrid = parentViewGrid;
    }

    public void warp(FinancialRecord financialRecord) {
        this.entityEditForm.setEditEntity(financialRecord);
    }
}
