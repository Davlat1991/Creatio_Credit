package tests.credit.smoke;


import core.base.BaseTest;
import core.data.TestData;
import core.data.TestDataLoader;
import core.data.mappers.LoginDataMapper;
import core.data.users.LoginData;
import flows.common.AuthorizationFlow;
import org.testng.annotations.Test;


public class LoginSmokeTest extends BaseTest {

 @Test
    public void LoginSmokeTest() {

        // ============================================================
        // 1. TEST DATA (ТОЛЬКО ЗДЕСЬ)
        // ============================================================

        TestData data = TestDataLoader.load();

        LoginData retailManager =
                LoginDataMapper.from(data.user("retailManager"));


        // ============================================================
        // 2. AUTHORIZATION
        // ============================================================

        AuthorizationFlow authFlow = new AuthorizationFlow(ui);

        // ============================================================

        authFlow.login(retailManager);

        }

}
