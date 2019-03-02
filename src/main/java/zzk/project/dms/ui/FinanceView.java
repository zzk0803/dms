package zzk.project.dms.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "finance",layout = MainView.class)
public class FinanceView extends VerticalLayout {
    public FinanceView() {
        add(new H1("这是财务管理页面"));
    }
}
