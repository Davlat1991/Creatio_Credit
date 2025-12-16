package tests.routes;


import core.base.BaseTest;
import core.config.Environment;
import core.pages.login.LoginPage;
import core.pages.routes.ClientDataPage;
import core.pages.routes.StandardRoutePage;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("Creatio Credit")
@Feature("Стандартный маршрут")
public class StandardRoutePositiveTest extends BaseTest {

    @Test(description = "Позитивный сценарий: заявка одобрена")
    @Story("Позитивный сценарий стандартного маршрута")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Davlat")
    public void standardRouteApproved() {

        loginPage.openLoginPage()
                .loginAs(Environment.USER_DAVLAT);

        new ClientDataPage()
                .fillClientData("Иванов Иван", "12345678")
                .next()
                .fillBasicFields("50000", "12")
                .save()
                .sendToReview()
                .approve()
                .verifyStatus("Одобрено");
    }
}

