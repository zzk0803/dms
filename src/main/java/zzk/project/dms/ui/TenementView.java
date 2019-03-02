package zzk.project.dms.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "tenement",layout = MainView.class)
public class TenementView extends VerticalLayout  {
    public TenementView() {
        add(new H1("这里是住户管理页面"));
    }
}
