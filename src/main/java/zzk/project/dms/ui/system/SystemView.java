package zzk.project.dms.ui.system;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import zzk.project.dms.ui.MainView;

import java.awt.*;

@Route(value = SystemView.VIEW_NAME, layout = MainView.class)
public class SystemView extends VerticalLayout {
    public static final String VIEW_NAME = "system";
    public static final String VIEW_TITLE = "系统设置";

    public SystemView() {
    }
}
