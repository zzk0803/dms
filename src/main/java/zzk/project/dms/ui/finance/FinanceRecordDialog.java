package zzk.project.dms.ui.finance;

import com.vaadin.flow.component.UI;
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

    private Grid<FinancialRecord> recordGrid;

    @Autowired
    public FinanceRecordDialog(
            @Qualifier("financeRecordFormDialogHeader") H4 dialogHeader,
            FinanceRecordEditForm financeRecordEditForm,
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
        addComponentAsFirst(rootLayout);
        onEvent();
    }

    private void onEvent() {
        okButton.addClickListener(click -> {
            UI.getCurrent().access(() -> {
                if (entityEditForm.commit()) {
                    recordGrid.getDataProvider().refreshAll();
                    recordGrid.getDataProvider().refreshItem(entityEditForm.getCompletedEntity());
                    recordGrid.getDataCommunicator().reset();
                    close();
                    entityEditForm.reset();
                }
            });
        });

        cancelButton.addClickListener(click -> {
            entityEditForm.reset();
            close();
        });
    }

    public void setRecordGrid(Grid<FinancialRecord> recordGrid) {
        this.recordGrid = recordGrid;
    }

    public void warp(FinancialRecord financialRecord) {
        this.entityEditForm.setEditEntity(financialRecord);
    }
}
