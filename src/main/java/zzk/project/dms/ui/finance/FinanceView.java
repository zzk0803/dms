package zzk.project.dms.ui.finance;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Qualifier;
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
            @Qualifier("addRecordButton") Button addRecordButton,
            @Qualifier("financialRecordGrid") Grid<FinancialRecord> financialRecordGrid,
            @Qualifier("financeRecordDialog") FinanceRecordDialog financeRecordDialog) {
        this.addRecordButton = addRecordButton;
        this.financialRecordGrid = financialRecordGrid;
        this.financeRecordDialog = financeRecordDialog;

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

        this.financeRecordDialog.setParentViewGrid(this.financialRecordGrid);
        addRecordButton.addClickListener(click -> {
            financeRecordDialog.open();
        });
    }
}
