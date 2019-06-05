package zzk.project.dms.ui;

import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.appbar.AppBarBuilder;
import com.github.appreciated.app.layout.component.menu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.component.menu.left.items.LeftNavigationItem;
import com.github.appreciated.app.layout.entity.DefaultBadgeHolder;
import com.github.appreciated.app.layout.notification.DefaultNotificationHolder;
import com.github.appreciated.app.layout.notification.component.AppBarNotificationButton;
import com.github.appreciated.app.layout.router.AppLayoutRouterLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.Push;
import zzk.project.dms.ui.dormitory.DormitoryView;
import zzk.project.dms.ui.finance.FinanceView;
import zzk.project.dms.ui.sensor.SensorView;
import zzk.project.dms.ui.summary.SummaryView;
import zzk.project.dms.ui.system.SystemView;
import zzk.project.dms.ui.tenement.TenementView;

@Push
public class MainView extends AppLayoutRouterLayout {

    public MainView() {
        init(AppLayoutBuilder
                .get(Behaviour.LEFT_RESPONSIVE)
                .withTitle("宿舍管理系统")
                .withAppBar(AppBarBuilder
                        .get()
                        .add(new AppBarNotificationButton<>(VaadinIcon.BELL, new DefaultNotificationHolder()))
                        .build())
                .withAppMenu(LeftAppMenuBuilder
                        .get()
                        .add(new LeftNavigationItem(SummaryView.VIEW_TITLE, VaadinIcon.CHART_GRID.create(), SummaryView.class))
                        .add(new LeftNavigationItem(DormitoryView.VIEW_TITLE, VaadinIcon.BUILDING.create(), DormitoryView.class))
                        .add(new LeftNavigationItem(TenementView.VIEW_TITLE, VaadinIcon.USER.create(), TenementView.class))
                        .add(new LeftNavigationItem(FinanceView.VIEW_TITLE, VaadinIcon.DOLLAR.create(), FinanceView.class))
                        .add(new LeftNavigationItem(SensorView.VIEW_TITLE, VaadinIcon.CLUSTER.create(), SensorView.class))
                        .add(new LeftNavigationItem(SystemView.VIEW_TITLE, VaadinIcon.COGS.create(), SystemView.class))
                        .build())
                .build());
    }
}
