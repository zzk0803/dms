package zzk.project.dms.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = Overview.VIEW_NAME, layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
public class Overview extends VerticalLayout {
    public static final String VIEW_NAME = "overfiew";
    public static final String VIEW_TITLE = "总览";

    public Overview() {
        add(new H1(VIEW_TITLE));
    }

}
