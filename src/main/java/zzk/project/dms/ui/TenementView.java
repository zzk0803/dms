package zzk.project.dms.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Qualifier;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.ui.tenement.TenementEditDialog;

@Route(value = TenementView.VIEW_NAME, layout = MainView.class)
public class TenementView extends VerticalLayout {

    public static final String VIEW_NAME = "tenement";
    public static final String VIEW_TITLE = "住户管理";

    private H1 h1header;
    private Button createButton;
    private Grid<Tenement> tenementGrid;
    private TenementEditDialog tenementEditDialog;

    public TenementView(
            @Qualifier("h1header") H1 h1header,
            @Qualifier("createButton") Button createButton,
            @Qualifier("tenementGrid") Grid<Tenement> tenementGrid,
            @Qualifier("tenementEditDialog") TenementEditDialog tenementEditDialog
    ) {
        this.h1header = h1header;
        this.createButton = createButton;
        this.tenementGrid = tenementGrid;
        this.tenementEditDialog = tenementEditDialog;
        initUI();
    }

    private void initUI() {
        setWidth("95%");
        add(h1header);
        add(createButton);
        add(tenementGrid);
        tenementGrid.setWidth("100%");
        setFlexGrow(1, tenementGrid);
        expand(tenementGrid);
        onEvent();
    }

    private void onEvent() {
        createButton.addClickListener(click -> tenementEditDialog.open());
        tenementEditDialog.setTenementGrid(this.tenementGrid);
    }

    public H1 getH1header() {
        return h1header;
    }

    public void setH1header(H1 h1header) {
        this.h1header = h1header;
    }

    public Button getCreateButton() {
        return createButton;
    }

    public void setCreateButton(Button createButton) {
        this.createButton = createButton;
    }

    public Grid<Tenement> getTenementGrid() {
        return tenementGrid;
    }

    public void setTenementGrid(Grid<Tenement> tenementGrid) {
        this.tenementGrid = tenementGrid;
    }

    public TenementEditDialog getTenementEditDialog() {
        return tenementEditDialog;
    }

    public void setTenementEditDialog(TenementEditDialog tenementEditDialog) {
        this.tenementEditDialog = tenementEditDialog;
    }
}
