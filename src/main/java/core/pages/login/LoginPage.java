package core.pages.login;

import com.codeborne.selenide.SelenideElement;
import core.data.users.LoginData;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class LoginPage {

    // Поле логина
    private final SelenideElement usernameInput = $("#loginEdit-el");

    // Поле пароля
    private final SelenideElement passwordInput = $("#passwordEdit-el");

    // Кнопка Войти
    private final SelenideElement loginButton = $("[data-item-marker='btnLogin']");

    // Признак успешного входа — верхнее меню
    private final SelenideElement headerContainer = $("#left-header-container");

    // Ошибка логина
    private final SelenideElement loginError = $(".base-edit-validation");


    @Step("Открыть страницу логина")
    public LoginPage openLoginPage() {
        open("/");
        usernameInput.shouldBe(visible);
        return this;
    }

    public LoginPage enterUsername(String username) {
        usernameInput.shouldBe(visible).setValue(username);
        return this;
    }



    public LoginPage enterPassword(String password) {
        passwordInput.shouldBe(visible).setValue(password);
        return this;
    }

    public void clickLoginButton() {
        loginButton.shouldBe(visible).click();
    }

    public boolean isUserLoggedIn() {
        return headerContainer.shouldBe(visible, Duration.ofSeconds(15)).exists();
    }

    public SelenideElement getLoginError() {
        return loginError;
    }


    // ================================
    // 🔥 Новые методы (вставь эти 3!)
    // ================================

    @Step("Авторизация: логин = {login}")
    public LoginPage login(String login, String password) {
        enterUsername(login);
        enterPassword(password);
        clickLoginButton();

        headerContainer.shouldBe(visible, Duration.ofSeconds(15));
        return this;
    }

    @Step("Авторизация под пользователем: {user.login}")
    public LoginPage loginAs(LoginData user) {
        return login(user.getLogin(), user.getPassword());
    }



}
