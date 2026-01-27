package core.pages.login;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import core.config.Environment;
import core.data.users.LoginData;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {

    // ===============================
    // Elements
    // ===============================

    private final SelenideElement usernameInput = $("#loginEdit-el");
    private final SelenideElement passwordInput = $("#passwordEdit-el");
    private final SelenideElement loginButton   = $("[data-item-marker='btnLogin']");
    private final SelenideElement header        = $("#left-header-container");
    private final SelenideElement loginError    = $(".base-edit-validation");
    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);
    // ===============================
    // Navigation
    // ===============================

    @Step("Открыть приложение (base URL)")
    public LoginPage openBase() {
        open(Environment.BASE_URL);
        usernameInput.shouldBe(visible, Duration.ofSeconds(15));
        return this;
    }

    // ===============================
    // Actions
    // ===============================

    @Step("Ввести логин: {login}")
    public LoginPage enterUsername(String login) {
        usernameInput.shouldBe(visible).setValue(login);
        return this;
    }

    @Step("Ввести пароль")
    public LoginPage enterPassword(String password) {
        passwordInput.shouldBe(visible).setValue(password);
        return this;
    }

    @Step("Нажать кнопку Войти")
    public LoginPage clickLogin() {
        loginButton.shouldBe(visible).click();
        return this;
    }

    // ===============================
    // Business methods
    // ===============================

    @Step("Авторизация под пользователем: {user.login}")
    public LoginPage loginAs(LoginData user) {
        return openBase()
                .enterUsername(user.getLogin())
                .enterPassword(user.getPassword())
                .clickLogin()
                .verifyLoginSuccess();
    }




    @Step("Главная страница успешно открыта")
    public LoginPage verifyLoginSuccess() {
        header.shouldBe(visible, Duration.ofSeconds(20));

        log.info("Успешная авторизация: главная страница открыта");

        attachScreenshot();
        return this;
    }

    @Attachment(value = "Главная страница", type = "image/png")
    public byte[] attachScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver())
                .getScreenshotAs(OutputType.BYTES);
    }

    // ===============================
    // Getters
    // ===============================

    public SelenideElement getLoginError() {
        return loginError;
    }
}
