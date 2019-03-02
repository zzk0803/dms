package zzk.project.dms.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "lodging", layout = MainView.class)
public class LodgingView extends HorizontalLayout {

    public LodgingView() {
        add(new H1("这里是住宅管理页面"));
    }
}
