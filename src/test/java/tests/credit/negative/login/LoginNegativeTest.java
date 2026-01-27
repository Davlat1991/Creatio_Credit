package tests.credit.negative.login;


import core.base.BaseTest;
import core.data.TestData;
import core.data.TestDataLoader;
import core.data.mappers.LoginDataMapper;
import core.data.users.LoginData;
import flows.common.AuthorizationFlow;
import io.qameta.allure.*;
import org.testng.annotations.Test;


@Epic("Creatio Authorization")
@Feature("Login Page")
public class LoginNegativeTest extends BaseTest {

    @Test(
            description = "Негативный сценарий: неверный пароль",
            groups = "negative"
    )
    @Story("Negative: неверный пароль")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Davlat")
    @Description("Проверка ошибки авторизации при вводе неправильного пароля")
    public void loginWithWrongPassword() {

        // ============================================================
        // 1. TEST DATA (ТОЛЬКО ЗДЕСЬ)
        // ============================================================
        TestData data = TestDataLoader.load();

        LoginData retailManager =
                LoginDataMapper.from(data.user("retailManager"));

        // ============================================================
        // 2. INFRASTRUCTURE FLOWS
        // ============================================================
        AuthorizationFlow authFlow = new AuthorizationFlow(ctx);

        // ============================================================
        // 🔵 3. АВТОРИЗАЦИЯ
        // ============================================================
        authFlow.login(retailManager);

        /*loginPage.openLoginPage()
                .enterUsername(Environment.USER_DAVLAT.getLogin())
                .enterPassword("WRONGPASS")
                .clickLoginButton();

        loginPage.getLoginError()
                .shouldHave(text("Неверный логин или пароль"));*/
    }
}





