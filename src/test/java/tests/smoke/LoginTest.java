package tests.smoke;

import org.testng.annotations.Test;
import steps.login.LoginSteps;


public class LoginTest {



        private final LoginSteps login = new LoginSteps();

        @Test(description = "Smoke: успешный логин пользователя")
        public void userCanLogin() {

            login.openLoginPage("http://10.10.202.254")
                    .enterUsername("S_RUSTAMOVA_796")
                    .enterPassword("S_RUSTAMOVA_796!@#aaa")
                    .clickLogin()
                    .verifyLogin();
        }


}
