package zzk.project.dms.ui.tenement;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Qualifier;
import zzk.project.dms.domain.entities.Tenement;

public class TenementEditDialog extends Dialog {
    private VerticalLayout root = new VerticalLayout();

    private H4 dialogHeader;
    private TenementEditForm tenementEditForm;
    private Button okButton;
    private Button cancelButton;

    private Grid<Tenement> tenementGrid;

    public TenementEditDialog(
            @Qualifier("dialogHeader") H4 dialogHeader,
            @Qualifier("tenementEditForm") TenementEditForm tenementEditForm,
            @Qualifier("okButton") Button okButton,
            @Qualifier("cancelButton") Button cancelButton
    ) {
        this.dialogHeader = dialogHeader;
        this.tenementEditForm = tenementEditForm;
        this.okButton = okButton;
        this.cancelButton = cancelButton;
        init();
    }

    public void warp(Tenement clickItem) {
        tenementEditForm.setEditTenement(clickItem);
    }

    public Grid<Tenement> getTenementGrid() {
        return tenementGrid;
    }

    public void setTenementGrid(Grid<Tenement> tenementGrid) {
        this.tenementGrid = tenementGrid;
    }

    private void init() {
        root.add(dialogHeader);
        root.add(tenementEditForm);
        HorizontalLayout buttons = new HorizontalLayout(okButton, cancelButton);
        buttons.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        root.add(buttons);
        root.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, buttons);
        add(root);
        onEvent();
    }

    private void onEvent() {
        okButton.addClickListener(click -> {
            if (tenementEditForm.commit()) {
                getTenementGrid().getDataProvider().refreshItem(tenementEditForm.getCompletedTenement());
                getTenementGrid().getDataProvider().refreshAll();
                getTenementGrid().getDataCommunicator().reset();
                close();
                tenementEditForm.reset();
            }
        });

        cancelButton.addClickListener(click -> {
            tenementEditForm.setEditTenement(new Tenement());
            close();
            tenementEditForm.reset();
        });
    }
}
