package tests.credit.smoke;

import org.testng.annotations.Test;
import steps.login.LoginSteps;


public class LoginTest {



        private final LoginSteps login = new LoginSteps();

        @Test(description = "Smoke: успешный логин пользователя")
        public void userCanLogin() {

            login.openLoginPage("http://10.10.202.254/")
                    .enterUsername("S_RUSTMOVA_796")
                    .enterPassword("S_RUSTMOVA_796S_RUSTMOVA_796")
                    .clickLogin()
                    .verifyLogin();
        }


}
