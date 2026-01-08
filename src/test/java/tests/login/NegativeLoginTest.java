package tests.login;



import core.base.BaseTest;
import core.config.Environment;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;

/*@Epic("Creatio Authorization")
@Feature("Login Page")
public class NegativeLoginTest extends BaseTest {

    @Test(
            description = "Негативный сценарий: неверный пароль",
            groups = "negative"
    )
    @Story("Negative: неверный пароль")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Davlat")
    @Description("Проверка ошибки авторизации при вводе неправильного пароля")
    public void loginWithWrongPassword() {

        loginPage.openLoginPage()
                .enterUsername(Environment.USER_DAVLAT.getLogin())
                .enterPassword("WRONGPASS")
                .clickLoginButton();

        loginPage.getLoginError()
                .shouldHave(text("Неверный логин или пароль"));
    }
}*/





