package flows.common;

import core.base.UiContext;
import core.data.users.LoginData;
import io.qameta.allure.Step;

/**
 * Infrastructure flow:
 * отвечает только за авторизацию / выход / смену пользователя
 */
public class AuthorizationFlow {

    private final UiContext ui;

    public AuthorizationFlow(UiContext ctx) {
        this.ui = ctx;
    }

    @Step("Войти под пользователем {user.login}")
    public void login(LoginData user) {
        ui.loginSteps.loginAs(user);
    }

    @Step("Выйти из системы")
    public void logout() {
        ui.headerPage.logout();
    }

    @Step("Перелогиниться под пользователем {user.login}")
    public void relogin(LoginData user) {
        logout();
        login(user);
    }
}
