package zzk.project.dms.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = AssetsView.VIEW_NAME,layout = MainView.class)
public class AssetsView extends VerticalLayout  {
    public static final String VIEW_NAME = "assets";
    public static final String VIEW_TITLE = "财产管理";

    public AssetsView() {
        add(new H1(VIEW_TITLE));
    }
}
