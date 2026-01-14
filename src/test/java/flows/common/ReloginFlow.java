package flows.common;

import core.base.TestContext;
import core.data.users.LoginData;
import io.qameta.allure.Step;

/**
 * Infrastructure flow:
 * отвечает за безопасную смену пользователя в рамках одного теста
 */
public class ReloginFlow {

    private final AuthorizationFlow authFlow;

    public ReloginFlow(TestContext ctx) {
        this.authFlow = new AuthorizationFlow(ctx);
    }

    @Step("Перелогиниться под пользователем {user}")
    public void relogin(String baseUrl, LoginData user) {
        authFlow.logout();
        authFlow.login(baseUrl, user);
    }
}
