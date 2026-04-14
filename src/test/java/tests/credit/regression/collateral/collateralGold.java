package tests.credit.regression.collateral;

import core.base.BaseTest;
import core.config.Environment;
import core.data.TestData;
import core.data.TestDataLoader;
import core.data.collateral.CollateralData;
import core.data.contacts.ContactData;
import core.data.contacts.ContactDataFactory;
import core.data.mappers.LoginDataMapper;
import core.data.registration.RegistrationIncomeExpensesData;
import core.data.users.LoginData;
import core.enums.CurrencyType;
import core.enums.Workspace;
import flows.common.AuthorizationFlow;
import flows.common.NavigationFlow;
import flows.common.WorkspaceFlow;
import flows.credit.*;
        import flows.credit.ReviewStageRetailFlow;
import flows.credit.ReviewStageUnderwriterFlow;
import flows.credit.participants.ParticipantsStageFlow;
import flows.credit.registration.*;
        import flows.credit.registration.client.BaseClientFlow;
import flows.credit.registration.client.EmployeeClientFlow;
import flows.credit.registration.client.OtherIncomeClientFlow;
import flows.credit.registration.client.SelfEmployedClientFlow;

import org.testng.annotations.Test;
import flows.credit.collateral.CollateralStageFlow;
import core.data.contacts.ContactDataFactory;

import java.util.List;

import static core.data.factory.CollateralTestDataFactory.*;



public class collateralGold extends BaseTest {

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
        LoginData ikokgo =
                LoginDataMapper.from(data.user("ikokgo"));
        LoginData cashier1 =
                LoginDataMapper.from(data.user("cashier1"));

        ContactData contact =
                ContactDataFactory.defaultContact();

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
        ParticipantsStageFlow participantsStageFlow = new ParticipantsStageFlow(ui);
        AttachDocumentsFlow attachDocumentsFlow = new AttachDocumentsFlow (ui);




        // 🔹 ВАЖНО: выбор типа клиента ТОЛЬКО ЗДЕСЬ
        //BaseClientFlow clientFlow = new SelfEmployedClientFlow(ui); //Тип клиента самозанятый
        //BaseClientFlow clientFlow = new EmployeeClientFlow(ui);      //Тип клиента работает в организации
        BaseClientFlow clientFlow = new OtherIncomeClientFlow(ui);    //Тип клиента имеет другой источник дохода


        // ============================================================
        // 4. RETAIL MANAGER
        // ============================================================

        /*authFlow.login(retailManager1);
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
                "20000",
                "24",
                "Сомони Чумхурии Точикистон"
        );

        applicationFlow.createApplication(
                "3",
                "2",
                "Аннуитетный",
                "24"
        );

        registrationFlow.completeRegistrationStage(
                incomeExpensesData,
                clientFlow);

        preliminaryCheckFlow.completePreliminaryCheckStage();

        // ============================================================
        //                      УЧАСТНИКИ
        // ============================================================

        List<CollateralData> collaterals = List.of(

                gold(CurrencyType.TJS)

        );

        collateralStageFlow.completeCollateralStage(collaterals);

        documentsStageFlow.uploadDocumentsLegacy();


        // ============================================================
        // 6. CLIENT NOTIFICATION — RETAIL MANAGER
        // ============================================================

        clientNotificationFlow.completeClientNotification(
                "Назарова Азиза Акбаровна"
        );

        authFlow.logout();

        // ============================================================
        // 7. LOAN ISSUANCE
        // ============================================================

        authFlow.login(ikokgo);
        workspaceFlow.select(Workspace.IKOK_GO);

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
        // 🔵 15. ПРИКРЕПЛЕНИЕ ДОКУМЕНТА ДЛЯ ЗАВЕРШЕНИЯ ЗАЯВКИ
        // ============================================================
        authFlow.login(retailManager1);
        workspaceFlow.select(Workspace.RETAIL_MANAGER);

        attachDocumentsFlow.attachDocument();

        authFlow.logout();*/

        // ============================================================
        // 🔵 16. ЗАВЕРШЕНИЕ ЗАЯВКИ
        // ============================================================
        authFlow.login(ikokgo);
        workspaceFlow.select(Workspace.IKOK_GO);
        navigationFlow.open(
                Environment.BASE_URL +
                        "0/Nui/ViewModule.aspx#CardModuleV2/BnzContractCreditPage/edit/ebede958-3160-4e90-bab7-678fc74d9678");

        applicationFinishFlow.completeApplicationFinish();


        authFlow.logout();


    }
}

