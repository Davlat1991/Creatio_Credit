package tests.flows;

import core.base.BaseTest;
import core.config.Environment;
import core.data.TestData;
import core.data.TestDataLoader;
import core.data.contacts.ContactData;
import core.data.mappers.ContactDataMapper;
import core.data.mappers.LoginDataMapper;
import core.data.registration.RegistrationIncomeExpensesData;
import core.data.users.LoginData;
import core.enums.Workspace;
import flows.common.AuthorizationFlow;
import flows.common.NavigationFlow;
import flows.common.WorkspaceFlow;
import flows.credit.*;
import flows.credit.ReviewStageRetailFlow;
import flows.credit.ReviewStageUnderwriterFlow;
import flows.credit.registration.*;
import flows.credit.registration.client.BaseClientFlow;
import flows.credit.registration.client.EmployedClientFlow;
import flows.credit.registration.client.OtherIncomeClientFlow;
import flows.credit.registration.client.SelfEmployedClientFlow;
import org.testng.annotations.Test;
import core.data.registration.EmploymentType;

public class FastTest extends BaseTest {

    @Test
    public void creditApplicationHappyPath() {

        // ============================================================
        // 1. TEST DATA (ТОЛЬКО ЗДЕСЬ)
        // ============================================================

        TestData data = TestDataLoader.load();

        LoginData retailManager =
                LoginDataMapper.from(data.user("retailManager"));
        LoginData underwriter =
                LoginDataMapper.from(data.user("underwriter"));
        LoginData ikok =
                LoginDataMapper.from(data.user("ikok"));
        LoginData cashier =
                LoginDataMapper.from(data.user("cashier"));

        ContactData contact =
                ContactDataMapper.from(data.defaultContact());

        RegistrationIncomeExpensesData incomeExpensesData =
                data.registrationIncomeExpenses();

        // ============================================================
        // 2. INFRASTRUCTURE FLOWS
        // ============================================================

        AuthorizationFlow authFlow = new AuthorizationFlow(ctx);
        WorkspaceFlow workspaceFlow = new WorkspaceFlow(ctx);

        // ============================================================
        // 3. BUSINESS FLOWS
        // ============================================================

        ClientSearchFlow clientSearchFlow = new ClientSearchFlow(ctx);
        ConsultationStartFlow consultationStartFlow = new ConsultationStartFlow(ctx);
        ProductSelectionFlow productFlow = new ProductSelectionFlow(ctx);
        ApplicationCreationFlow applicationFlow = new ApplicationCreationFlow(ctx);
        RegistrationStageFlow registrationFlow = new RegistrationStageFlow(ctx);
        PreliminaryCheckStageFlow preliminaryCheckFlow = new PreliminaryCheckStageFlow(ctx);
        DocumentsStageFlow documentsStageFlow = new DocumentsStageFlow(ctx);
        ReviewStageRetailFlow reviewRetailFlow = new ReviewStageRetailFlow(ctx);
        ReviewStageUnderwriterFlow reviewUnderwriterFlow = new ReviewStageUnderwriterFlow(ctx);
        ClientNotificationStageFlow clientNotificationFlow = new ClientNotificationStageFlow(ctx);
        NavigationFlow navigationFlow = new NavigationFlow(ctx);
        LoanIssuanceFlow loanIssuanceFlow = new LoanIssuanceFlow(ctx);

        // 🔹 ВАЖНО: выбор типа клиента ТОЛЬКО ЗДЕСЬ
        BaseClientFlow clientFlow = new SelfEmployedClientFlow(ctx); //Тип клиента самозанятый
      //BaseClientFlow clientFlow = new EmployedClientFlow(ctx);     //Тип клиента работает в организации
      //BaseClientFlow clientFlow = new OtherIncomeClientFlow(ctx);  //Тип клиента имеет другой источник дохода


        // ============================================================
        // 4. RETAIL MANAGER
        // ============================================================

        authFlow.login(retailManager);
        workspaceFlow.select(Workspace.RETAIL_MANAGER);

        clientSearchFlow.searchClient(
                contact.getLastName(),
                contact.getFirstName(),
                contact.getMiddleName()
        );

        consultationStartFlow.startConsultation(
                "consultation-theme-7a0f11cc-756d-474a-885f-1dd64eeca5b3"
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

        /*authFlow.login(retailManager);

        navigationFlow.open(
                Environment.BASE_URL +
                        "0/Nui/ViewModule.aspx#CardModuleV2/FinApplicationPage/edit/27f1fa9e-4ec5-42a4-a24b-f3c070afce04");*/


        registrationFlow.completeRegistrationStage(
                incomeExpensesData,
                clientFlow);


        preliminaryCheckFlow.completePreliminaryCheckStage();
        documentsStageFlow.uploadDocumentsLegacy();

        reviewRetailFlow.completeReview();

        authFlow.logout();

        // ============================================================
        // 5. UNDERWRITER
        // ============================================================

        authFlow.login(underwriter);
        workspaceFlow.select(Workspace.UNDERWRITER);

        reviewUnderwriterFlow.approveReview("КК4 по заявке"
        );

        authFlow.logout();

        // ============================================================
        // 6. CLIENT NOTIFICATION — RETAIL MANAGER
        // ============================================================

        authFlow.login(retailManager);
        workspaceFlow.select(Workspace.RETAIL_MANAGER);


        clientNotificationFlow.completeClientNotification(
                "Рустамова Саодатчон Валиевна"
        );

        authFlow.logout();

        // ============================================================
        // 7. LOAN ISSUANCE
        // ============================================================

        authFlow.login(ikok);
        workspaceFlow.select(Workspace.IKOK);

        loanIssuanceFlow.issueLoan();

        authFlow.logout();


    }
}

