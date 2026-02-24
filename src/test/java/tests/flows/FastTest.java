package tests.flows;

import core.base.BaseTest;
import core.config.Environment;
import core.data.TestData;
import core.data.TestDataLoader;
import core.data.contacts.ContactData;
import core.data.mappers.ContactDataMapper;
import core.data.mappers.LoginDataMapper;
import core.data.registration.RegistrationIncomeExpensesData;
import core.data.scoring.CreditDecision;
import core.data.users.LoginData;
import core.enums.CollateralType;
import core.enums.Workspace;
import flows.common.AuthorizationFlow;
import flows.common.NavigationFlow;
import flows.common.WorkspaceFlow;
import flows.credit.*;
import flows.credit.ReviewStageRetailFlow;
import flows.credit.ReviewStageUnderwriterFlow;
import flows.credit.registration.*;
import flows.credit.registration.client.BaseClientFlow;
import flows.credit.registration.client.EmployeeClientFlow;
import flows.credit.registration.client.OtherIncomeClientFlow;
import flows.credit.registration.client.SelfEmployedClientFlow;
import org.testng.annotations.Test;
import core.data.registration.EmploymentType;
import flows.credit.collateral.CollateralStageFlow;
import core.enums.CollateralType;

import static core.enums.CollateralType.GOODS;

public class FastTest extends BaseTest {

    @Test
    public void creditApplicationHappyPath() {

        // ============================================================
        // 1. TEST DATA (ТОЛЬКО ЗДЕСЬ)
        // ============================================================

        TestData data = TestDataLoader.load();

        LoginData retailManager1 =
                LoginDataMapper.from(data.user("retailManager1"));
        LoginData underwriter1 =
                LoginDataMapper.from(data.user("underwriter1"));
        LoginData ikok1 =
                LoginDataMapper.from(data.user("ikok1"));
        LoginData cashier1 =
                LoginDataMapper.from(data.user("cashier1"));

        ContactData contact =
                ContactDataMapper.from(data.defaultContact());

        RegistrationIncomeExpensesData incomeExpensesData =
                data.registrationIncomeExpenses();



        // ============================================================
        // 2. INFRASTRUCTURE FLOWS
        // ============================================================

        AuthorizationFlow authFlow = new AuthorizationFlow(ui);
        WorkspaceFlow workspaceFlow = new WorkspaceFlow(ui);

        // ============================================================
        // 3. BUSINESS FLOWS
        // ============================================================

        ClientSearchFlow clientSearchFlow = new ClientSearchFlow(ui);
        ConsultationStartFlow consultationStartFlow = new ConsultationStartFlow(ui);
        ProductSelectionFlow productFlow = new ProductSelectionFlow(ui);
        ApplicationCreationFlow applicationFlow = new ApplicationCreationFlow(ui);
        RegistrationStageFlow registrationFlow = new RegistrationStageFlow(ui);
        PreliminaryCheckStageFlow preliminaryCheckFlow = new PreliminaryCheckStageFlow(ui);
        DocumentsStageFlow documentsStageFlow = new DocumentsStageFlow(ui);
        ReviewStageRetailFlow reviewRetailFlow = new ReviewStageRetailFlow(ui);
        ReviewStageUnderwriterFlow reviewUnderwriterFlow = new ReviewStageUnderwriterFlow(ui);
        ClientNotificationStageFlow clientNotificationFlow = new ClientNotificationStageFlow(ui);
        NavigationFlow navigationFlow = new NavigationFlow(ui);
        LoanIssuanceFlow loanIssuanceFlow = new LoanIssuanceFlow(ui);
        SigningStageFlow signingStageFlow = new SigningStageFlow(ui);
        ApplicationFinishFlow applicationFinishFlow = new ApplicationFinishFlow(ui);
        CollateralStageFlow collateralStageFlow = new CollateralStageFlow(ui);


        // 🔹 ВАЖНО: выбор типа клиента ТОЛЬКО ЗДЕСЬ
      // BaseClientFlow clientFlow = new SelfEmployedClientFlow(ui); //Тип клиента самозанятый
      //BaseClientFlow clientFlow = new EmployeeClientFlow(ui);     //Тип клиента работает в организации
       BaseClientFlow clientFlow = new OtherIncomeClientFlow(ui);  //Тип клиента имеет другой источник дохода


        // ============================================================
        // 4. RETAIL MANAGER
        // ============================================================

        authFlow.login(retailManager1);
        workspaceFlow.select(Workspace.RETAIL_MANAGER);


        /*clientSearchFlow.searchClient(
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
                clientFlow);

        preliminaryCheckFlow.completePreliminaryCheckStage();*/

         navigationFlow.open(
                Environment.BASE_URL +
                        "0/Nui/ViewModule.aspx#CardModuleV2/FinApplicationPage/edit/53b2903d-a606-4365-bb57-d8ef90d5bdaa");
        collateralStageFlow.completeCollateralStage(CollateralType.EQUIPMENT);

        /*documentsStageFlow.uploadDocumentsLegacy();

        reviewRetailFlow.completeReview();

        authFlow.logout();

        // ============================================================
        // 5. UNDERWRITER
        // ============================================================

        authFlow.login(underwriter1);
        workspaceFlow.select(Workspace.UNDERWRITER);

        reviewUnderwriterFlow.approveReview("КК4 по заявке"
        );

        authFlow.logout();

        // ============================================================
        // 6. CLIENT NOTIFICATION — RETAIL MANAGER
        // ============================================================

        authFlow.login(retailManager1);
        workspaceFlow.select(Workspace.RETAIL_MANAGER);


        clientNotificationFlow.completeClientNotification(
                "Рустамова Саодатчон Валиевна"
        );

        authFlow.logout();

        // ============================================================
        // 7. LOAN ISSUANCE
        // ============================================================

        authFlow.login(ikok1);
        workspaceFlow.select(Workspace.IKOK);

        loanIssuanceFlow.issueLoan();

        authFlow.logout();

        // ============================================================
        // 🔵 13. ВЫДАЧА КРЕДИТА
        // ============================================================
        authFlow.login(cashier1);
        workspaceFlow.select(Workspace.CASHIER);

        signingStageFlow.completeSigningStage();

        authFlow.logout();

        // ============================================================
        // 🔵 14. ЗАВЕРШЕНИЕ ЗАЯВКИ
        // ============================================================
        authFlow.login(ikok1);
        workspaceFlow.select(Workspace.IKOK);

        applicationFinishFlow.completeApplicationFinish();


        authFlow.logout();*/


    }
}

