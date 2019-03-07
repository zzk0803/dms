package zzk.project.dms.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = SensorView.VIEW_NAME, layout = MainView.class)
public class SensorView extends VerticalLayout {

    public static final String VIEW_NAME = "sensor";
    public static final String VIEW_TITLE = "传感器";

    public SensorView() {
        add(new H1(VIEW_TITLE));
    }


}
