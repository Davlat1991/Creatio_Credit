package steps.login;

import io.qameta.allure.*;
import core.pages.login.LoginPage;
import core.data.users.LoginData;

@Epic("Авторизация")
@Feature("Логин пользователя")
public class LoginSteps {

    private final LoginPage page = new LoginPage();

    // ---------------------------------------------------------
    // ОСНОВНЫЕ БИЗНЕС-ШАГИ
    // ---------------------------------------------------------

    @Step("Открыть приложение")
    public LoginSteps openApplication() {
        page.openBase();
        return this;
    }

    @Step("Авторизоваться под пользователем {user.login}")
    public LoginSteps loginAs(LoginData user) {
        page.loginAs(user);
        return this;
    }

    @Step("Авторизоваться (логин = {login})")
    public LoginSteps login(String login, String password) {
        page.openBase()
                .enterUsername(login)
                .enterPassword(password)
                .clickLogin()
                .verifyLoginSuccess();
        return this;
    }

    // ---------------------------------------------------------
    // ПРОВЕРКИ
    // ---------------------------------------------------------

    @Step("Проверить успешный вход")
    public LoginSteps verifyLogin() {
        page.verifyLoginSuccess();
        return this;
    }
}
