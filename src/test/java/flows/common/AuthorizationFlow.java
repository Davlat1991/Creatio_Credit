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

    @Step("Открыть приложение и войти под пользователем {1}")
    public void login(String baseUrl, LoginData user) {
        ctx.loginPage.openUrl(baseUrl);
        ctx.loginSteps
                .enterUsername(user.getLogin())
                .enterPassword(user.getPassword())
                .clickLogin()
                .verifyLogin();
    }

    @Step("Выйти из системы")
    public void logout() {
        ctx.headerPage.logout();
    }

    @Step("Перелогиниться под пользователем {1}")
    public void relogin(String baseUrl, LoginData user) {
        logout();
        login(baseUrl, user);
    }
}
