package zzk.project.dms.ui.dormitory;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.*;
import com.vaadin.flow.router.internal.RouteUtil;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.ui.MainView;

@Route(value = DormitoryView.VIEW_NAME,
       layout = MainView.class)
public class DormitoryView extends VerticalLayout
//        implements HasUrlParameter<String>, BeforeLeaveObserver, BeforeEnterObserver
{

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
        setWidth("100%");
        setHeight("100%");
        add(headerH1);
        add(controlPanel);
        add(spaceTreeGrid);
        setAlignSelf(Alignment.STRETCH, spaceTreeGrid);
        expand(spaceTreeGrid);
        onEvent();
    }

    private void onEvent() {
        createSpaceButton.addClickListener(click -> editDialog.open());
        editDialog.setSpaceTreeGrid(this.spaceTreeGrid);
    }

    public DormitoryEditDialog getEditDialog() {
        return editDialog;
    }

    public TreeGrid<DormitorySpace> getSpaceTreeGrid() {
        return spaceTreeGrid;
    }

//    @Override
//    public void setParameter(
//            BeforeEvent event, String parameter
//    ) {
//        System.out.println("event = " + event + ", parameter = " + parameter);
//    }

//    @Override
//    public void setParameter(
//            BeforeEvent event,
//            @OptionalParameter String parameter
//            ) {
//        System.out.println("event = " + event + ", parameter = " + parameter);
//    }

//    @Override
//    public void setParameter(
//            BeforeEvent event,
//            @WildcardParameter String parameter
//    ) {
//        System.out.println("event = " + event + ", parameter = " + parameter);
//    }


//    @Override
//    public void beforeLeave(BeforeLeaveEvent event) {
//        final BeforeLeaveEvent.ContinueNavigationAction postponed = event.postpone();
//
//        final Dialog dialog = new Dialog();
//        VerticalLayout layouts = new VerticalLayout();
//        layouts.add(new Text("Sure Leave?"));
//        layouts.add(new HorizontalLayout(new Button("YES", (clickedEvent) -> postponed.proceed()),
//                                         new Button("No", (clickedEvent) -> {
//                                             dialog.close();
//                                         })
//        ));
//
//        dialog.add(layouts);
//        dialog.open();
//    }
//
//    @Override
//    public void beforeEnter(BeforeEnterEvent event) {
//        Notification.show("entered");
//    }

}
