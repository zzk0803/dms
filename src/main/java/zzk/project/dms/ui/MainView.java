package zzk.project.dms.ui;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import zzk.project.dms.ui.dormitory.DormitoryView;
import zzk.project.dms.ui.finance.FinanceView;
import zzk.project.dms.ui.sensor.SensorView;
import zzk.project.dms.ui.summary.SummaryView;
import zzk.project.dms.ui.system.SystemView;
import zzk.project.dms.ui.tenement.TenementView;

public class MainView extends AbstractAppRouterLayout {

    @Override
    protected void configure(
            AppLayout appLayout,
            AppLayoutMenu appLayoutMenu
    ) {
        Span branding = new Span("宿舍管理系统");
        appLayout.setBranding(branding);
        appLayoutMenu.addMenuItems(
                new AppLayoutMenuItem(
                        VaadinIcon.CHART_GRID.create(),
                        SummaryView.VIEW_TITLE,
                        SummaryView.VIEW_NAME
                ),
                new AppLayoutMenuItem(VaadinIcon.BUILDING.create(),
                                      DormitoryView.VIEW_TITLE,
                                      DormitoryView.VIEW_NAME
                ),
                new AppLayoutMenuItem(VaadinIcon.USER.create(),
                                      TenementView.VIEW_TITLE,
                                      TenementView.VIEW_NAME
                ),
                new AppLayoutMenuItem(VaadinIcon.DOLLAR.create(),
                                      FinanceView.VIEW_TITLE,
                                      FinanceView.VIEW_NAME
                ),
                new AppLayoutMenuItem(VaadinIcon.CLUSTER.create(),
                                      SensorView.VIEW_TITLE,
                                      SensorView.VIEW_NAME
                ),
                new AppLayoutMenuItem(VaadinIcon.COGS.create(),
                                      SystemView.VIEW_TITLE,
                                      SystemView.VIEW_NAME
                )
        );
    }

}
