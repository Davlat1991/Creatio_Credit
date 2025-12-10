package core.pages.login;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import core.data.users.Credentials;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    // Локаторы — адаптированы под Creatio
    private final SelenideElement usernameField = $("[name='Username']");
    private final SelenideElement passwordField = $("[name='Password']");
    private final SelenideElement loginButton =
            $x("//button[contains(@class,'login-button') or contains(text(),'Войти')]");

    // -----------------------------
    // Открыть страницу логина
    // -----------------------------
    @Step("Открыть страницу логина: {url}")
    public LoginPage openLoginPage(String url) {
        open(url);
        return this;
    }

    // -----------------------------
    // Ввести логин
    // -----------------------------
    @Step("Ввести логин: {username}")
    public LoginPage enterUsername(String username) {
        usernameField.shouldBe(visible).clear();
        usernameField.setValue(username);
        return this;
    }

    // -----------------------------
    // Ввести пароль
    // -----------------------------
    @Step("Ввести пароль")
    public LoginPage enterPassword(String password) {
        passwordField.shouldBe(visible).clear();
        passwordField.setValue(password);
        return this;
    }

    // -----------------------------
    // Нажать кнопку Войти
    // -----------------------------
    @Step("Нажать кнопку 'Войти'")
    public LoginPage clickLoginButton() {
        loginButton.shouldBe(visible, enabled).click();
        return this;
    }

    // -------------------------------------------------------
    // 🔥 Новый метод: авторизация пользователя через Credentials
    // -------------------------------------------------------
    @Step("Авторизация пользователя: {credentials.username}")
    public LoginPage login(Credentials credentials) {
        enterUsername(credentials.getUsername());
        enterPassword(credentials.getPassword());
        clickLoginButton();
        return this;
    }

    // -------------------------------------------------------
    // 🔥 Универсальный login через username + password
    // -------------------------------------------------------
    @Step("Авторизация пользователя (строки): {username}")
    public LoginPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        return this;
    }

    // -------------------------------------------------------
    // Проверка, что пользователь успешно вошёл
    // -------------------------------------------------------
    @Step("Проверка авторизации пользователя")
    public boolean isUserLoggedIn() {
        return $x("//span[contains(@class,'user-name') or contains(@class,'profile-indicator')]")
                .shouldBe(visible)
                .exists();
    }

    public SelenideElement getLoginError() {
        return $x("//*[contains(@class,'login-error-message') or contains(@class,'login-page-error') or contains(text(),'Неверный')]")
                .shouldBe(Condition.visible);
    }
}

