package zzk.project.dms.ui.dormitory;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.ui.MainView;

@Route(value = DormitoryView.VIEW_NAME, layout = MainView.class)
public class DormitoryView extends VerticalLayout {
    public static final String VIEW_NAME = "dormitory";
    public static final String VIEW_TITLE = "住宿管理";

    private H1 headerH1;
    private HorizontalLayout controlPanel;
    private Button createSpaceButton;
    private TreeGrid<DormitorySpace> spaceTreeGrid;

    private DormitoryEditDialog editDialog;

    public DormitoryView(
            H1 headerH1,
            HorizontalLayout controlPanel,
            Button createSpaceButton,
            TreeGrid<DormitorySpace> spaceTreeGrid,
            DormitoryEditDialog editDialog
    ) {
        this.headerH1 = headerH1;
        this.controlPanel = controlPanel;
        this.createSpaceButton = createSpaceButton;
        this.spaceTreeGrid = spaceTreeGrid;
        this.editDialog = editDialog;
        initUI();
    }

    private void initUI() {
        setWidth("95%");
        add(headerH1);
        add(controlPanel);
        add(spaceTreeGrid);
        setFlexGrow(1, spaceTreeGrid);
        expand(spaceTreeGrid);
        onEvent();
    }

    private void onEvent() {
        createSpaceButton.addClickListener(click -> editDialog.open());
        editDialog.setSpaceTreeGrid(this.spaceTreeGrid);
    }

}
