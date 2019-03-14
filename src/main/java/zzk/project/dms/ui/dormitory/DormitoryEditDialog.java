package zzk.project.dms.ui.dormitory;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import zzk.project.dms.domain.entities.DormitorySpace;

public class DormitoryEditDialog extends Dialog {
    private VerticalLayout root = new VerticalLayout();

    private DormitoryEditForm dormitoryEditForm;
    private TreeGrid<DormitorySpace> spaceTreeGrid;

    private Button commitButton;
    private Button giveUpButton;

    public DormitoryEditDialog(
            DormitoryEditForm dormitoryEditForm,
            Button commitButton,
            Button giveUpButton
    ) {
        this.dormitoryEditForm = dormitoryEditForm;
        this.commitButton = commitButton;
        this.giveUpButton = giveUpButton;
        init();
    }

    public TreeGrid<DormitorySpace> getSpaceTreeGrid() {
        return spaceTreeGrid;
    }

    public void setSpaceTreeGrid(TreeGrid<DormitorySpace> spaceTreeGrid) {
        this.spaceTreeGrid = spaceTreeGrid;
    }

    private void init() {
        this.setCloseOnOutsideClick(false);
        this.setCloseOnEsc(false);

        H4 headerH4 = new H4("新建&编辑宿舍空间");
        root.add(headerH4);
        root.add(dormitoryEditForm);
        root.add(new HorizontalLayout(commitButton, giveUpButton));
        addComponentAsFirst(root);
        onEvent();
    }

    private void onEvent() {
        commitButton.addClickListener(click -> {
            UI.getCurrent().access(() -> {
                if (dormitoryEditForm.doCommit()) {
                    spaceTreeGrid.getDataProvider().refreshAll();
                    spaceTreeGrid.getDataProvider().refreshItem(dormitoryEditForm.getCompletedDormitorySpace());
                    spaceTreeGrid.getDataCommunicator().reset();
                    close();
                    dormitoryEditForm.reset();
                }
            });
        });
        giveUpButton.addClickListener(close -> {
            dormitoryEditForm.reset();
            close();
        });
    }

    public void warp(DormitorySpace dormitorySpace) {
        dormitoryEditForm.setEditingObject(dormitorySpace);
    }
}
