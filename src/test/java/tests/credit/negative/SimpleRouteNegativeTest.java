package tests.credit.negative;


import core.base.BaseTest;
import core.pages.credit.SimpleRoutePage;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import core.config.Environment;

@Epic("Creatio Credit")
@Feature("Упрощённый маршрут")
public class SimpleRouteNegativeTest extends BaseTest {

    @Test(
            description = "Негативный тест: обязательные поля",
            groups = "negative"
    )
    @Story("Negative: пустые обязательные поля")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Davlat")
    @Description("Проверка, что Creatio не даёт сохранить заявку, если обязательные поля не заполнены")
    public void emptyRequiredFieldsTest() {

        loginPage.openLoginPage()
                .loginAs(Environment.USER_DAVLAT);

        workspaceSteps.openWorkspaceAndSection("Розничный менеджер", "Заявки");
        dashboardPage.openCreateMenu();

        new SimpleRoutePage()
                .waitOpened()
                .save()
                .shouldHaveError("Заполните обязательные поля");
    }
}



