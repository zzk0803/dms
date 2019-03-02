package zzk.project.dms.ui;

import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

public class MainView extends AbstractAppRouterLayout {
    private final VerticalLayout root = new VerticalLayout();

    public MainView() {
        root.setSpacing(true);
        root.setMargin(true);
        root.add(new H1("这里是主页面"));
    }

    @Override
    protected void configure(AppLayout appLayout, AppLayoutMenu appLayoutMenu) {
        appLayout.setBranding(new H3("宿舍管理系统"));
        appLayoutMenu.addMenuItems(
                new AppLayoutMenuItem("总览", "overview"),
                new AppLayoutMenuItem("住宿管理", "lodging"),
                new AppLayoutMenuItem("住户管理", "tenement"),
                new AppLayoutMenuItem("资产管理", "assets"),
                new AppLayoutMenuItem("财务管理", "finance"),
                new AppLayoutMenuItem("传感器", "sensor"),
                new AppLayoutMenuItem("系统设置", "system")
        );
    }
}
