package tests.credit.smoke;


/*import core.base.BaseTest;
import core.pages.login.LoginPage;
import core.pages.routes.SimpleRoutePage;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import steps.login.LoginSteps;

@Epic("Creatio Credit")
@Feature("Упрощённый маршрут")
@Story("Smoke: создание заявки")
@Owner("Davlat")
@Severity(SeverityLevel.CRITICAL)
public class SimpleRouteSmokeTest extends BaseTest {

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

        // 2. Переход в рабочее место и раздел
        workspaceSteps.openWorkspaceAndSection("Розничный менеджер", "Заявки");

        // 3. Создание заявки
        dashboardPage.openCreateMenu();

        // 4. Заполнение упрощённой формы
        new SimpleRoutePage()
                .waitOpened()
                .fillRequiredFields("Иванов Иван", "9000000000")
                .save()
                .verifyStatus("Создано");
    }
}*/
