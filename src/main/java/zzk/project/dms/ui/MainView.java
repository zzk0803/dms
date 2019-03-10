package zzk.project.dms.ui;

import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;

@Push
public class MainView extends AbstractAppRouterLayout {
    @Override
    protected void configure(AppLayout appLayout, AppLayoutMenu appLayoutMenu) {
        appLayout.setBranding(new H3("宿舍管理系统"));
        appLayoutMenu.addMenuItems(
                new AppLayoutMenuItem(VaadinIcon.CHART_GRID.create(),Overview.VIEW_TITLE, Overview.VIEW_NAME),
                new AppLayoutMenuItem(VaadinIcon.BUILDING.create(),DormitoryView.VIEW_TITLE, DormitoryView.VIEW_NAME),
                new AppLayoutMenuItem(VaadinIcon.USER.create(),TenementView.VIEW_TITLE, TenementView.VIEW_NAME),
                new AppLayoutMenuItem(VaadinIcon.STORAGE.create(),AssetsView.VIEW_TITLE, AssetsView.VIEW_NAME),
                new AppLayoutMenuItem(VaadinIcon.DOLLAR.create(),FinanceView.VIEW_TITLE, FinanceView.VIEW_NAME),
                new AppLayoutMenuItem(VaadinIcon.CLUSTER.create(),SensorView.VIEW_TITLE, SensorView.VIEW_NAME),
                new AppLayoutMenuItem(VaadinIcon.COGS.create(),SystemView.VIEW_TITLE, SystemView.VIEW_NAME)
        );
    }
}
