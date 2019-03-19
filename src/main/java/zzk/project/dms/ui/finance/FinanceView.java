package zzk.project.dms.ui.finance;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import zzk.project.dms.domain.entities.FinancialRecord;
import zzk.project.dms.ui.MainView;

@Route(value = FinanceView.VIEW_NAME,layout = MainView.class)
public class FinanceView extends VerticalLayout {
    public static final String VIEW_NAME = "finance";
    public static final String VIEW_TITLE = "财务管理";

    private Button addRecordButton;
    private Grid<FinancialRecord> financialRecordGrid;

    private FinanceRecordDialog financeRecordDialog;

    public FinanceView(
            Button addRecordButton,
            Grid<FinancialRecord> financialRecordGrid,
            FinanceRecordDialog financeRecordDialog
    ) {
        this.addRecordButton = addRecordButton;
        this.financialRecordGrid = financialRecordGrid;
        this.financeRecordDialog = financeRecordDialog;
        this.financeRecordDialog.setRecordGrid(this.financialRecordGrid);

        ui();
        event();
    }

    private void ui() {
        setWidth("97.5%");
        setHeight("100%");
        add(
                new H1(VIEW_TITLE),
                addRecordButton,
                financialRecordGrid,
                financeRecordDialog
        );
        setFlexGrow(1, financeRecordDialog);
        expand(financeRecordDialog);
    }

    private void event() {
        addRecordButton.addClickListener(click -> {
            financeRecordDialog.open();
        });
    }

    public FinanceRecordDialog getEditDialog() {
        return this.financeRecordDialog;
    }
}
