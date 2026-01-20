package flows.common;

import core.base.TestContext;
import core.data.users.LoginData;
import io.qameta.allure.Step;

/**
 * Infrastructure flow:
 * отвечает только за авторизацию / выход / смену пользователя
 */
public class AuthorizationFlow {

    private final TestContext ctx;

    public AuthorizationFlow(TestContext ctx) {
        this.ctx = ctx;
    }

    @Step("Войти под пользователем {user.login}")
    public void login(LoginData user) {
        ctx.loginSteps.loginAs(user);
    }

    @Step("Выйти из системы")
    public void logout() {
        ctx.headerPage.logout();
    }

    @Step("Перелогиниться под пользователем {user.login}")
    public void relogin(LoginData user) {
        logout();
        login(user);
    }
}
