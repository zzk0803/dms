package zzk.project.dms.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "sensor",layout = MainView.class)
public class SensorView extends VerticalLayout {
    public SensorView() {
        add(new H1("这里是传感器页面"));
    }
}
