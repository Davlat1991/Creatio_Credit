package tests.flows;

import core.base.BaseTest;
import core.data.TestData;
import core.data.TestDataLoader;
import core.data.contacts.ContactData;
import core.data.mappers.ContactDataMapper;
import core.data.mappers.LoginDataMapper;
import core.data.registration.EmploymentType;
import core.data.registration.RegistrationIncomeExpensesData;
import core.data.users.LoginData;
import core.enums.Workspace;
import flows.common.AuthorizationFlow;
import flows.common.NavigationFlow;
import flows.common.WorkspaceFlow;
import flows.credit.*;
import flows.credit.registration.RegistrationStageFlow;
import org.testng.annotations.Test;

public class StandartRiouteCreditTest extends BaseTest {

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


        registrationFlow.completeRegistrationStage(
                incomeExpensesData,
                EmploymentType.OTHER_INCOME
        );

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
