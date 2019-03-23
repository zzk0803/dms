package zzk.project.dms.authentication;

import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.Route;

@Route(value = "login")
public class LoginView extends LoginOverlay {
    public LoginView(
            AccessControl accessControl
    ) {
        setI18n(createCNI18n());
        setForgotPasswordButtonVisible(false);
        addLoginListener(loginEvent -> {
            String username = loginEvent.getUsername();
            String password = loginEvent.getPassword();
            if (accessControl.signIn(username, password) != null) {
                getUI().ifPresent(ui -> ui.navigate(""));
            } else {
                setError(true);
            }
        });
        setOpened(true);
    }

    private LoginI18n createCNI18n() {
        LoginI18n loginI18n = LoginI18n.createDefault();

        loginI18n.setHeader(new LoginI18n.Header());
        LoginI18n.Header i18nHeader = loginI18n.getHeader();
        i18nHeader.setTitle("宿舍管理系统");
        i18nHeader.setDescription("请登录");

        loginI18n.setForm(new LoginI18n.Form());
        LoginI18n.Form i18nForm = loginI18n.getForm();
        i18nForm.setUsername("用户名");
        i18nForm.setPassword("密码");
        i18nForm.setSubmit("登录");
        i18nForm.setForgotPassword("忘记密码了？");

        loginI18n.setErrorMessage(new LoginI18n.ErrorMessage());
        LoginI18n.ErrorMessage errorMessage = loginI18n.getErrorMessage();
        errorMessage.setTitle("登录失败");
        errorMessage.setMessage("用户名或密码不一致");
        return loginI18n;
    }
}
