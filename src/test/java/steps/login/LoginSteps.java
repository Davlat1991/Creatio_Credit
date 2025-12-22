package steps.login;

import com.codeborne.selenide.Condition;
import io.qameta.allure.*;
import core.pages.login.LoginPage;

@Epic("Авторизация")
@Feature("Логин пользователя")


public class LoginSteps {

    private final LoginPage page = new LoginPage();


    // ---------------------------------------------------------
    // ОТКРЫТИЕ СТРАНИЦЫ
    // ---------------------------------------------------------

    @Step("Открыть страницу логина: {url}")
    public LoginSteps openLoginPage(String baseURL) {
        page.openLoginPage();
        return this;
    }

    // ---------------------------------------------------------
    // ВВОД ДАННЫХ
    // ---------------------------------------------------------

    @Step("Ввести логин: {username}")
    public LoginSteps enterUsername(String username) {
        page.enterUsername(username);
        return this;
    }

    @Step("Ввести пароль")
    public LoginSteps enterPassword(String password) {
        page.enterPassword(password);
        return this;
    }

    @Step("Нажать кнопку 'Войти'")
    public LoginSteps clickLogin() {
        page.clickLoginButton();
        return this;
    }


    // ---------------------------------------------------------
    // ГЛАВНЫЕ БИЗНЕС-ШАГИ
    // ---------------------------------------------------------

    @Step("Авторизоваться пользователем {username}")
    public LoginSteps login(String username, String password) {
        page.enterUsername(username);
        page.enterPassword(password);
        page.clickLoginButton();
        return this;
    }

    @Step("Авторизоваться как пользователь {username}")
    public LoginSteps loginAs(String username, String password) {
        page.enterUsername(username);
        page.enterPassword(password);
        page.clickLoginButton();
        return this;
    }


    // ---------------------------------------------------------
    // ПРОВЕРКИ
    // ---------------------------------------------------------

    @Step("Проверить успешный вход")
    public LoginSteps verifyLogin() {
        if (!page.isUserLoggedIn()) {
            throw new AssertionError("❌ Пользователь НЕ авторизован!");
        }
        return this;
    }
    @Step("Проверить ошибку логина: '{expectedMessage}'")
    public LoginSteps verifyLoginError(String expectedMessage) {
        page.getLoginError().shouldHave(Condition.text(expectedMessage));
        return this;
    }


    @Step("Проверить, что вход НЕ выполнен")
    public LoginSteps verifyLoginFailed() {
        if (page.isUserLoggedIn()) {
            throw new AssertionError("❌ Логин выполнен, а должен был провалиться!");
        }
        return this;
    }
}
