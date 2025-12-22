package tests.credit.smoke;

import core.base.BaseTest;
import core.pages.login.LoginPage;
import org.testng.annotations.Test;
import steps.login.LoginSteps;

import static core.config.Environment.BASE_URL;


public class LoginTest extends BaseTest {



    private final LoginSteps login = new LoginSteps();
    private final LoginPage openUrl = new LoginPage();

        @Test(description = "Smoke: успешный логин пользователя")
        public void userCanLogin() {

            openUrl
                    .openUrl(BASE_ULR_1);//.openUrl("http://10.10.202.254/0/Nui/ViewModule.aspx#CardModuleV2/FinApplicationPage/edit/666a922f-d170-4ee8-9d76-3a3a6f1708ca");
            login
                    .enterUsername(retailManager.getLogin())
                    .enterPassword(retailManager.getPassword())
                    .clickLogin()
                    .verifyLogin();

        }


}
