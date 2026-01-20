/*package tests.flows;

import core.base.BaseTest;
import core.config.Environment;
import core.data.TestData;
import core.data.TestDataLoader;
import core.data.mappers.LoginDataMapper;
import core.data.users.LoginData;
import flows.common.AuthorizationFlow;
import flows.common.NavigationFlow;
import flows.credit.ClientNotificationStageFlow;
import flows.credit.ReviewStageFlow;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

public class TestFlows extends BaseTest {

    @Test
    @Epic("Credit process")
    @Feature("Review stage")
    @Story("Retail Manager -> Underwriter approval")
    public void reviewStageFlowTest() {

        // ===== Test data =====
        TestData data = TestDataLoader.load();

        LoginData retailManager =
                LoginDataMapper.from(data.user("retailManager"));

        LoginData underwriter =
                LoginDataMapper.from(data.user("underwriter"));

        // ===== Flows =====
        AuthorizationFlow authFlow = new AuthorizationFlow(ctx);
        NavigationFlow navigationFlow = new NavigationFlow(ctx);
        ReviewStageFlow reviewStageFlow = new ReviewStageFlow(ctx);
        ClientNotificationStageFlow notificationFlow = new ClientNotificationStageFlow(ctx);

        // ===== Retail Manager =====
        authFlow.login(retailManager);

        navigationFlow.open(
                Environment.BASE_URL +
                        "0/Nui/ViewModule.aspx#CardModuleV2/FinApplicationPage/edit/4dd5eb9c-3602-42e8-b29f-e63242af1c57"
        );

        reviewStageFlow.completeReviewAsRetailManager();

        // ===== Underwriter =====
        reviewStageFlow.approveReviewAsUnderwriter(underwriter);

        authFlow.login(retailManager);

        notificationFlow.notifyClientAndConfirm("Рустамова Саодатчон Валиевна");

        authFlow.logout();
    }


}*/
