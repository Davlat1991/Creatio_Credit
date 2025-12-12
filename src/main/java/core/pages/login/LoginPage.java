package core.pages.login;

import com.codeborne.selenide.SelenideElement;

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

    public void openLoginPage(String url) {
        open(url);
    }

    public void enterUsername(String username) {
        usernameInput.shouldBe(visible).setValue(username);
    }

    public void enterPassword(String password) {
        passwordInput.shouldBe(visible).setValue(password);
    }

    public void clickLoginButton() {
        loginButton.shouldBe(visible).click();
    }

    // Успешный вход — появилось меню
    public boolean isUserLoggedIn() {
        return $("#left-header-container")
                .shouldBe(visible, Duration.ofSeconds(15))
                .exists();
    }


    public SelenideElement getLoginError() {
        return loginError;
    }
}
