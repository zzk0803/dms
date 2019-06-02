package zzk.project.dms;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import zzk.project.dms.domain.authentication.AccessControl;
import zzk.project.dms.domain.utilies.SpringBeansUtil;
import zzk.project.dms.ui.LoginView;

public class DormitoryManageSystemInitListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        AccessControl accessControl = (AccessControl) SpringBeansUtil.getBean(AccessControl.class);
        serviceInitEvent.getSource().addUIInitListener(uiInitEvent -> {
            uiInitEvent.getUI().addBeforeEnterListener(beforeEnterEvent -> {
                if (!accessControl.isUserSignedIn() && !LoginView.class.equals(beforeEnterEvent.getNavigationTarget())) {
                    beforeEnterEvent.rerouteTo(LoginView.class);
                }
            });
        });
    }
}
