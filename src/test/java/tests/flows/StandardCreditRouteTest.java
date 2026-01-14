package tests.flows;

import core.base.BaseTest;
import core.data.TestData;
import core.data.TestDataLoader;
import core.data.contacts.ContactData;
import core.data.mappers.ContactDataMapper;
import core.data.mappers.LoginDataMapper;
import core.data.users.LoginData;
import flows.common.AuthorizationFlow;
import flows.common.NavigationFlow;
import flows.credit.*;
import assertions.credit.DocumentsAssertions;
import org.testng.annotations.Test;

public class StandardCreditRouteTest extends BaseTest {

    /*@Test
    public void creditApplicationHappyPath() {

        // ============================================================
        // 1. TEST DATA
        // ============================================================

        TestData data = TestDataLoader.load();

        LoginData retailManager =
                LoginDataMapper.from(data.user("retailManager"));

        ContactData contact =
                ContactDataMapper.from(data.defaultContact());

        // ============================================================
        // 2. FLOWS
        // ============================================================

        AuthorizationFlow authFlow = new AuthorizationFlow(ctx);
        AuthorizationAndClientSearchFlow clientFlow = new AuthorizationAndClientSearchFlow(ctx);
        ProductSelectionFlow productFlow = new ProductSelectionFlow(ctx);
        ApplicationCreationFlow applicationFlow = new ApplicationCreationFlow(ctx);
        RegistrationStageFlow registrationFlow = new RegistrationStageFlow(ctx);
        PreliminaryCheckStageFlow preliminaryCheckFlow = new PreliminaryCheckStageFlow(ctx);
        DocumentsStageFlow documentsStageFlow = new DocumentsStageFlow(ctx);

        DocumentsAssertions documentsAssertions =
                new DocumentsAssertions(ctx);

        // ============================================================
        // 3. HAPPY PATH
        // ============================================================

        authFlow.login(BASE_URL_1, retailManager);

        clientFlow.startConsultation(
                "Розничный менеджер",
                contact
        );

        productFlow.selectProduct(
                "Карзхои гуногунмаксад",
                "Барои эхтиёчоти оилави",
                "50000",
                "36",
                "Сомони Чумхурии Точикистон"
        );

        applicationFlow.createApplication(
                "3",
                "2",
                "Аннуитетный",
                "36"
        );

        registrationFlow.completeRegistrationStage();
        preliminaryCheckFlow.completePreliminaryCheckStage();

        documentsStageFlow.uploadClientDocuments(
                data.documents()
        );

        // ============================================================
        // 4. ASSERTS (ЯВНО!)
        // ============================================================

        documentsAssertions.documentsShouldBeUploaded(
                "Финансовое досье",
                data.documents().getFinancialDossier().size()
        );

        documentsAssertions.documentsShouldBeUploaded(
                "Досье клиента",
                data.documents().getClientDossier().size()
        );
    }*/


    @Test
    public void debugDocumentsStageOnly() {

        TestData data = TestDataLoader.load();
        LoginData user = LoginDataMapper.from(data.user("retailManager"));

        AuthorizationFlow authFlow = new AuthorizationFlow(ctx);
        NavigationFlow navigationFlow = new NavigationFlow(ctx);
        DocumentsStageFlow documentsFlow = new DocumentsStageFlow(ctx);
        DocumentsAssertions documentsAssertions = new DocumentsAssertions(ctx);

        authFlow.login(BASE_URL_1, user);

        navigationFlow.open(
                "http://10.10.202.254/0/Nui/ViewModule.aspx#CardModuleV2/FinApplicationPage/edit/e7939016-d4cf-4c66-8a65-594764e4f141"
        );

        documentsFlow.uploadFileToDetail(
                "Финансовое досье",
                "Registration (Example).xlsx"
        );

        documentsAssertions.shouldHaveUploadedFiles(
                "Финансовое досье",
                1
        );
    }


}
