package zzk.project.dms.ui.sensor;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import zzk.project.dms.ui.MainView;

@Route(value = SensorView.VIEW_NAME, layout = MainView.class)
public class SensorView extends VerticalLayout {
    public static final String VIEW_TITLE = "传感器";
    public static final String VIEW_NAME = "sensor";

    public SensorView() {

    }
}
