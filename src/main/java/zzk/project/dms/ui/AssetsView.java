package zzk.project.dms.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "assets",layout = MainView.class)
public class AssetsView extends VerticalLayout  {
    public AssetsView() {
        add(new H1("这是资产管理页面"));
    }
}
