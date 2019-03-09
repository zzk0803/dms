package zzk.project.dms.ui;

import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;

@Push
public class MainView extends AbstractAppRouterLayout {
    @Override
    protected void configure(AppLayout appLayout, AppLayoutMenu appLayoutMenu) {
        VerticalLayout header = new VerticalLayout(new H3("宿舍管理系统"));
        header.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setMargin(false);
        header.setSpacing(false);
        header.setPadding(false);
        appLayout.setBranding(header);
        appLayoutMenu.addMenuItems(
                new AppLayoutMenuItem(Overview.VIEW_TITLE, Overview.VIEW_NAME),
                new AppLayoutMenuItem(DormitoryView.VIEW_TITLE, DormitoryView.VIEW_NAME),
                new AppLayoutMenuItem(TenementView.VIEW_TITLE, TenementView.VIEW_NAME),
                new AppLayoutMenuItem(AssetsView.VIEW_TITLE, AssetsView.VIEW_NAME),
                new AppLayoutMenuItem(FinanceView.VIEW_TITLE, FinanceView.VIEW_NAME),
                new AppLayoutMenuItem(SensorView.VIEW_TITLE, SensorView.VIEW_NAME),
                new AppLayoutMenuItem(SystemView.VIEW_TITLE, SystemView.VIEW_NAME)
        );
    }
}
