package tests.flows;

import core.base.BaseTest;
import core.data.TestData;
import core.data.TestDataLoader;
import core.data.contacts.ContactData;
import core.data.mappers.ContactDataMapper;
import core.data.mappers.LoginDataMapper;
import core.data.users.LoginData;
import flows.common.AuthorizationFlow;
import flows.credit.*;
import assertions.credit.DocumentsAssertions;
import flows.credit.registration.RegistrationStageFlow;
import org.testng.annotations.Test;

/*public class StandardCreditRouteTest extends BaseTest {

    @Test
    public void creditApplicationHappyPath() {

        // ============================================================
        // 1. TEST DATA
        // ============================================================

        TestData data = TestDataLoader.load();

        LoginData retailManager =
                LoginDataMapper.from(data.user("retailManager"));
        LoginData underwriter =
                LoginDataMapper.from(data.user("underwriter"));


        ContactData contact =
                ContactDataMapper.from(data.defaultContact());

        // ============================================================
        // 2. FLOWS
        // ============================================================

        AuthorizationFlow authFlow = new AuthorizationFlow(ctx);
        ProductSelectionFlow productFlow = new ProductSelectionFlow(ctx);
        ApplicationCreationFlow applicationFlow = new ApplicationCreationFlow(ctx);
        RegistrationStageFlow registrationFlow = new RegistrationStageFlow(ctx);
        PreliminaryCheckStageFlow preliminaryCheckFlow = new PreliminaryCheckStageFlow(ctx);
        DocumentsStageFlow documentsStageFlow = new DocumentsStageFlow(ctx);
        DocumentsAssertions documentsAssertions = new DocumentsAssertions(ctx);
        ReviewStageFlow reviewStageFlow = new ReviewStageFlow(ctx);

        // ============================================================
        // 3. HAPPY PATH
        // ============================================================

        // Login
        authFlow.login(retailManager);

        // Start consultation & client search
        clientFlow.startConsultation(
                "Розничный менеджер",
                contact
        );

        // Product selection
        productFlow.selectProduct(
                "Карзхои гуногунмаксад",
                "Барои эхтиёчоти оилави",
                "50000",
                "36",
                "Сомони Чумхурии Точикистон"
        );

        // Application creation
        applicationFlow.createApplication(
                "3",
                "2",
                "Аннуитетный",
                "36"
        );

        // Registration stage
        registrationFlow.completeRegistrationStage();

        // Preliminary check stage
        preliminaryCheckFlow.completePreliminaryCheckStage();

        // Documents stage
        documentsStageFlow.uploadDocumentsLegacy();

        reviewStageFlow.completeReviewAsRetailManager();

        // ===== Underwriter =====
        reviewStageFlow.approveReviewAsUnderwriter(underwriter);
    }
}*/
