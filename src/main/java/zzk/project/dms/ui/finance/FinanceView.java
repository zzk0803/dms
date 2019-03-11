package zzk.project.dms.ui.finance;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import zzk.project.dms.ui.MainView;

@Route(value = FinanceView.VIEW_NAME,layout = MainView.class)
public class FinanceView extends VerticalLayout {
    public static final String VIEW_NAME = "finance";
    public static final String VIEW_TITLE = "财务管理";

    public FinanceView() {
        add(new H1(VIEW_TITLE));
    }
}
