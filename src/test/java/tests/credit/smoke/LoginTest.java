package tests.credit.smoke;

import org.testng.annotations.Test;
import steps.login.LoginSteps;

import static core.config.Environment.BASE_URL;


public class LoginTest {



        private final LoginSteps login = new LoginSteps();

        @Test(description = "Smoke: успешный логин пользователя")
        public void userCanLogin() {

            login.openLoginPage()
                    .enterUsername("S_RUSTMOVA_796")
                    .enterPassword("S_RUSTMOVA_796S_RUSTMOVA_796")
                    .clickLogin()
                    .verifyLogin();
        }


}
