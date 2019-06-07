package zzk.project.dms.ui.dormitory;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import zzk.project.dms.domain.entities.DormitorySpace;

@SpringComponent
@UIScope
public class DormitoryEditDialog extends Dialog {
    private VerticalLayout root = new VerticalLayout();

    private H4 dialogHeader;
    private DormitoryEditForm dormitoryEditForm;
    private TreeGrid<DormitorySpace> spaceTreeGrid;

    private Button commitButton;
    private Button giveUpButton;

    @Autowired
    public DormitoryEditDialog(
           @Qualifier("dialogHeaderForDormitory") H4 dialogHeader,
            DormitoryEditForm dormitoryEditForm,
           @Qualifier("dormitoryDialogCommitButton") Button commitButton,
           @Qualifier("dormitoryDialogGiveUpButton") Button giveUpButton
    ) {
        this.dialogHeader = dialogHeader;
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

        root.add(dialogHeader);
        root.add(dormitoryEditForm);
        root.add(new HorizontalLayout(commitButton, giveUpButton));
        addComponentAsFirst(root);
        onEvent();
    }

    private void onEvent() {
        commitButton.addClickListener(click -> {
            UI.getCurrent().access(() -> {
                if (dormitoryEditForm.doCommit()) {
                    DormitorySpace completedDormitorySpace = dormitoryEditForm.getCompletedDormitorySpace();
                    spaceTreeGrid.getDataProvider().refreshAll();
                    spaceTreeGrid.getDataProvider().refreshItem(completedDormitorySpace);
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
