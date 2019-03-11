package zzk.project.dms.ui.assets;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import zzk.project.dms.ui.MainView;

@Route(value = AssetsView.VIEW_NAME,layout = MainView.class)
public class AssetsView extends VerticalLayout  {
    public static final String VIEW_NAME = "assets";
    public static final String VIEW_TITLE = "财产管理";

    private Tabs subviewTabs;
    private FlexLayout subview;
    private FlexLayout root;

    public AssetsView() {
        add(new H1(VIEW_TITLE));
    }


}
