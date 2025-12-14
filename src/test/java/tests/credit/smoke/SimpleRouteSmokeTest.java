package tests.credit.smoke;


import core.base.BaseTest;
import core.pages.credit.SimpleRoutePage;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import core.config.Environment;

@Epic("Creatio Credit")
@Feature("Упрощённый маршрут")
@Story("Smoke: создание заявки")
@Owner("Davlat")
@Severity(SeverityLevel.CRITICAL)
public class SimpleRouteSmokeTest extends BaseTest {

    @Test(
            groups = {"smoke"},
            description = "Smoke: создание заявки через упрощённый маршрут. Проверка статуса 'Создано'"
    )
    public void simpleRouteSmokeTest() {

        // 1. Авторизация
        loginPage.openLoginPage()
                .loginAs(Environment.USER_DAVLAT);

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
}
