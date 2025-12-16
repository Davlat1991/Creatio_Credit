package tests.credit.negative;



import core.base.BaseTest;
import core.pages.routes.SimpleRoutePage;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import core.config.Environment;

@Epic("Creatio Credit")
@Feature("Упрощённый маршрут")
public class SimpleRouteBoundaryTest extends BaseTest {

    @Test(description = "Boundary: слишком короткое ФИО", groups = "boundary")
    @Story("Boundary: ФИО длиной 1 символ — недопустимо")
    @Severity(SeverityLevel.MINOR)
    @Owner("Davlat")
    @Description("Проверка ошибки при вводе в поле ФИО слишком короткого значения")
    public void fioTooShortTest() {

        loginPage.openLoginPage()
                .loginAs(Environment.USER_DAVLAT);

        workspaceSteps.openWorkspaceAndSection("Розничный менеджер", "Заявки");
        dashboardPage.openCreateMenu();

        new SimpleRoutePage()
                .waitOpened()
                .fillRequiredFields("А", "9000000000")
                .save()
                .shouldHaveError("Введите корректное ФИО");
    }


    @Test(description = "Boundary: минимальная допустимая длина ФИО", groups = "boundary")
    @Story("Boundary: минимально допустимое ФИО")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Davlat")
    @Description("Проверка успешного сохранения заявки при вводе минимально допустимого значения ФИО")
    public void fioValidMinLengthTest() {

        loginPage.openLoginPage()
                .loginAs(Environment.USER_DAVLAT);

        workspaceSteps.openWorkspaceAndSection("Розничный менеджер", "Заявки");
        dashboardPage.openCreateMenu();

        new SimpleRoutePage()
                .waitOpened()
                .fillRequiredFields("Ан", "9000000000")
                .save()
                .verifyStatus("Создано");
    }
}


