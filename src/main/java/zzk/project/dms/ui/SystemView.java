package zzk.project.dms.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "system",layout = MainView.class)
public class SystemView extends VerticalLayout {
    public SystemView() {
        add(new H1("这里是系统设置页面"));
    }
}
