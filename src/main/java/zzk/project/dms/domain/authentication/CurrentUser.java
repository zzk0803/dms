package zzk.project.dms.domain.authentication;

import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.WrappedSession;

import java.util.Objects;

public final class CurrentUser {
    public static final String KEY = CurrentUser.class.getCanonicalName();

    private CurrentUser() {
    }

    public static String get() {
        WrappedSession session = getSession();
        String currentUser = (String) session.getAttribute(KEY);
        return currentUser == null ? "" : currentUser;
    }

    public static void set(String username) {
        if (username == null) {
            getSession().removeAttribute(KEY);
        } else {
            getSession().setAttribute(KEY, username);
        }
    }

    private static WrappedSession getSession() {
        VaadinRequest currentRequest = getCurrentRequest();
        return currentRequest.getWrappedSession();
    }

    private static VaadinRequest getCurrentRequest() {
        VaadinRequest currentRequest = VaadinService.getCurrentRequest();
        if (Objects.isNull(currentRequest)) {
            throw new IllegalStateException("No request bound to current thread.");
        }
        return currentRequest;
    }
}
