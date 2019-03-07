package zzk.project.dms.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = TenementView.VIEW_NAME,layout = MainView.class)
public class TenementView extends VerticalLayout  {

    public static final String VIEW_NAME = "tenement";
    public static final String VIEW_TITLE = "住户管理";

    public TenementView() {
        add(new H1(VIEW_TITLE));
    }


}
