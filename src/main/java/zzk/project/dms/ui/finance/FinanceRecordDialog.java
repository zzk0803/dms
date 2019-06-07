package zzk.project.dms.ui.finance;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import zzk.project.dms.domain.entities.FinancialRecord;

@SpringComponent
@UIScope
public class FinanceRecordDialog extends Dialog {

    private H4 dialogHeader;
    private FinanceRecordEditForm financeRecordEditForm;
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
        this.financeRecordEditForm = financeRecordEditForm;
        this.okButton = okButton;
        this.cancelButton = cancelButton;
        ui();
        onEvent();
    }

    private void ui() {
        this.setCloseOnOutsideClick(false);
        this.setCloseOnEsc(false);

        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.add(dialogHeader);
        rootLayout.add(financeRecordEditForm);
        HorizontalLayout buttonGroup = new HorizontalLayout(okButton, cancelButton);
        buttonGroup.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        rootLayout.add(buttonGroup);
        rootLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, buttonGroup);
        addComponentAsFirst(rootLayout);
    }

    private void onEvent() {
        okButton.addClickListener(click -> {
            UI.getCurrent().access(() -> {
                if (financeRecordEditForm.commit()) {
                    recordGrid.getDataProvider().refreshItem(financeRecordEditForm.getCompletedRecord());
                    recordGrid.getDataProvider().refreshAll();
                    recordGrid.getDataCommunicator().reset();
                    close();
                    financeRecordEditForm.reset();
                }
            });
        });

        cancelButton.addClickListener(click -> {
            financeRecordEditForm.reset();
            close();
        });
    }

    public void setRecordGrid(Grid<FinancialRecord> recordGrid) {
        this.recordGrid = recordGrid;
    }

    public void setEditingRecord(FinancialRecord financialRecord) {
        this.financeRecordEditForm.setEditingRecordEmergency(financialRecord);
    }
}
