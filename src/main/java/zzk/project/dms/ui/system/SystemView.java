package zzk.project.dms.ui.system;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import zzk.project.dms.ui.MainView;

@Route(value = SystemView.VIEW_NAME, layout = MainView.class)
public class SystemView extends VerticalLayout {
    public static final String VIEW_NAME = "system";
    public static final String VIEW_TITLE = "系统设置";

    public SystemView() {
        add(new H1(VIEW_TITLE));
    }

}