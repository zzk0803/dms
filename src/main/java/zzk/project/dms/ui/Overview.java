package zzk.project.dms.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "overview", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
public class Overview extends VerticalLayout {
    public Overview() {
        add(new H1("这里是总览页面"));
    }
}
