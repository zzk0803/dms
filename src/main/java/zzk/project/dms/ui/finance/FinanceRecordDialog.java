package zzk.project.dms.ui.finance;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.data.provider.DataCommunicator;
import com.vaadin.flow.data.provider.DataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import zzk.project.dms.domain.entities.FinancialRecord;
import zzk.project.dms.ui.common.AbstractEntityEditDialog;
import zzk.project.dms.ui.common.AbstractEntityEditForm;

public class FinanceRecordDialog extends AbstractEntityEditDialog<FinancialRecord> {
    private FinanceView parentView;

    @Autowired
    public FinanceRecordDialog(
            @Qualifier("financeRecordFormDialogHeader") H4 dialogHeader,
            @Qualifier("financeRecordFormEntityEditForm") FinanceRecordEditForm financeRecordEditForm,
            @Qualifier("financeRecordFormOkButton") Button okButton,
            @Qualifier("financeRecordFormCancelButton") Button cancelButton
    ) {
        super(dialogHeader, financeRecordEditForm, okButton, cancelButton);
        initUI();
    }

    private void initUI() {
    }

    public FinanceView getParentView() {
        return parentView;
    }

    public void setParentView(FinanceView parentView) {
        this.parentView = parentView;
    }

    @Override
    public <T> void onCommit(AbstractEntityEditForm<T> form) {
        if (form.doCommit()) {
            DataProvider<FinancialRecord, ?> recordDataProvider = getParentView().getFinancialRecordGrid().getDataProvider();
            DataCommunicator<FinancialRecord> dataCommunicator = getParentView().getFinancialRecordGrid().getDataCommunicator();
            recordDataProvider.refreshItem((FinancialRecord) form.getCompletedEntity());
            recordDataProvider.refreshAll();
            dataCommunicator.reset();
        }
    }

    @Override
    public <T> void onAbort(AbstractEntityEditForm<T> form) {
        form.doAbort();
    }


}
