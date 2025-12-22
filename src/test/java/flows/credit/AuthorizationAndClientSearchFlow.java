package flows.credit;



import core.base.BaseTest;
import core.pages.login.LoginPage;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import steps.login.LoginSteps;

@Epic("Creatio Credit")
@Feature("Упрощённый маршрут")
@Story("Smoke: создание заявки")
@Owner("Davlat")
@Severity(SeverityLevel.CRITICAL)


public class AuthorizationAndClientSearchFlow extends BaseTest {

    private final LoginSteps login = new LoginSteps();
    private final LoginPage openUrl = new LoginPage();

    @Test(
            groups = {"smoke"},
            description = "Smoke: создание заявки через упрощённый маршрут. Проверка статуса 'Создано'"
    )
    public void simpleRouteSmokeTest() {

        // 1. Авторизация
        openUrl
                .openUrl(BASE_ULR_1).openUrl("http://10.10.202.254/0/Nui/ViewModule.aspx#CardModuleV2/FinApplicationPage/edit/666a922f-d170-4ee8-9d76-3a3a6f1708ca");
        login
                .enterUsername(retailManager.getLogin())
                .enterPassword(retailManager.getPassword())
                .clickLogin()
                .verifyLogin();

        workspaceSteps
                .selectWorkAccess("Розничный менеджер");
        contractPage
                .clickButtonById("view-button-OBSW-imageEl");



    }
}



