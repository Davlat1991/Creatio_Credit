package tests.credit;


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
import flows.credit.registration.RegistrationStageFlow;
import flows.credit.registration.client.BaseClientFlow;
import flows.credit.registration.client.OtherIncomeClientFlow;
import mocks.core.BaseMockServer;
import mocks.scenarios.CreditHappyPathMock;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import core.base.BaseTest;

public class CreditUiWithMocksTest extends BaseTest {

    @BeforeClass
    public void startMockServer() {
        BaseMockServer.start();
    }

    @BeforeMethod
    public void setupMocks() {
        BaseMockServer.reset();
        CreditHappyPathMock.setup();
    }

    @AfterClass
    public void stopMockServer() {
        BaseMockServer.stop();
    }

    @Test
    public void StandardRouteCredit() {

        // ===== ДАННЫЕ =====
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

        // ===== FLOWS =====
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

        // 🔹 ВАЖНО: выбор типа клиента
        //BaseClientFlow clientFlow = new SelfEmployedClientFlow(ui); //Тип клиента самозанятый
        //BaseClientFlow clientFlow = new EmployedClientFlow(ui);     //Тип клиента работает в организации
        BaseClientFlow clientFlow = new OtherIncomeClientFlow(ui);    //Тип клиента имеет другой источник дохода

        // ===== АВТОРИЗАЦИЯ =====
        authFlow.login(retailManager1);

        // ===== РАБОЧЕЕ МЕСТО =====
        workspaceFlow.select(Workspace.RETAIL_MANAGER);

        // ============================================================
        // 🔵 6. КОНСУЛЬТАЦИЯ + ПОДБОР ПРОДУКТА + СОЗДАНИЕ ЗАЯВКИ
        // ============================================================
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

        // ============================================================
        // 🔵 7. ЗАПОЛНЕНИЕ АНКЕТЫ ЗАЁМЩИКА
        // ============================================================
        registrationFlow.completeRegistrationStage(
                incomeExpensesData,
                clientFlow);

        // ============================================================
        // 🔵 8. ПРОХОЖДЕНИЕ СТАДИЙ ОБРАБОТКИ
        // ============================================================
        preliminaryCheckFlow.completePreliminaryCheckStage();

        // ============================================================
        // 🔵 9. ЗАГРУЗКА ДОКУМЕНТОВ
        // ============================================================
        documentsStageFlow.uploadDocumentsLegacy();

        // ============================================================
        // 🔵 10. РАССМОТРЕНИЕ
        // ============================================================
        reviewRetailFlow.completeReview();
        authFlow.logout();

        // ============================================================
        // 🔵 11. ПРОЕКТ РЕШЕНИЯ
        // ============================================================
        authFlow.login(underwriter1);
        workspaceFlow.select(Workspace.UNDERWRITER);

        reviewUnderwriterFlow.approveReview("КК4 по заявке"
        );

        authFlow.logout();

        // ============================================================
        // 🔵 12. ИНФОРМИРОВАНИЕ КЛИЕНТА
        // ============================================================
        authFlow.login(retailManager1);
        workspaceFlow.select(Workspace.RETAIL_MANAGER);

        clientNotificationFlow.completeClientNotification(
                "Рустамова Саодатчон Валиевна"
        );

        authFlow.logout();

        // ============================================================
        // 🔵 12. ПОДПИСАНИЕ + ВЫДАЧА + СОЗДАНИЕ ОРДЕРОВ
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

        authFlow.logout();

        // ============================================================
        // 🔵 14. ЗАВЕРШЕНИЕ ЗАЯВКИ
        // ============================================================
        authFlow.login(ikok1);
        workspaceFlow.select(Workspace.IKOK);

        authFlow.logout();



    }
}




