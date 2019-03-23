package zzk.project.dms.authentication;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import zzk.project.dms.domain.entities.Account;
import zzk.project.dms.domain.services.AccountService;

@Component
@Scope(value = "prototype")
public class AccessControlImpl implements AccessControl {

    @Autowired
    private AccountService accountService;

    @Override
    public Account signIn(String username, String password) {
        Account account = accountService.login(username, password);
        if (account != null) {
            CurrentUser.set(account.getUsername());
        }
        return account;
    }

    @Override
    public boolean isUserSignedIn() {
        return !CurrentUser.get().isEmpty();
    }

    @Override
    public String getPrincipalName() {
        return CurrentUser.get();
    }

    @Override
    public void signOut() {
        VaadinSession.getCurrent().getSession().invalidate();
        UI.getCurrent().getPage().reload();
    }
}
