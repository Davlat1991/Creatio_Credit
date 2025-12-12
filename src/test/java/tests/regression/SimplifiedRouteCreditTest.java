package tests.regression;

import core.base.BaseTest;
import core.base.common.components.*;
import core.data.contacts.ContactData;
import core.data.users.Users;
import core.pages.credit.ConsultationPanelPage;
import core.pages.credit.ContractCreditApplicationPage;
import core.pages.ui.DetailPage;
import org.testng.annotations.Test;
import steps.credit.BorrowerSteps;
import steps.credit.CreditApplicationSteps;
import steps.login.LoginSteps;
import steps.workspace.WorkspaceSteps;

import java.time.LocalDate;

public class SimplifiedRouteCreditTest extends BaseTest {

    @Test(description = "Упрощённый маршрут оформления потребительского кредита")
    public void testSimplifiedCreditFlow() {

        // -------------------------------------------------------------
        // 🔵 0. ТЕСТОВЫЕ ДАННЫЕ
        // -------------------------------------------------------------
        ContactData client = new ContactData(
                "Иван",
                "Иванович",
                "Иванов",
                LocalDate.of(1995, 3, 10),
                "АН1234567",
                "ОВД Душанбе",
                LocalDate.of(2021, 5, 20),
                "+992900001122",
                "ivan@test.com",
                "г. Душанбе, ул. Ленина"
        );

        // -------------------------------------------------------------
        // 🔵 1. ИНИЦИАЛИЗАЦИЯ STEPS
        // -------------------------------------------------------------
        LoginSteps login = new LoginSteps();
        WorkspaceSteps workspace = new WorkspaceSteps();

        CreditApplicationSteps credit = new CreditApplicationSteps(
                new ContractCreditApplicationPage(),
                new FieldComponent(),
                new LookupComponent(),
                new ButtonsComponent(),
                new DashboardComponent(),
                new MiniPageComponent(),
                new FileUploadComponent(),
                new GridComponent(),
                new DetailPage(),
                new ConsultationPanelPage()
        );

        BorrowerSteps borrower = new BorrowerSteps();


        // -------------------------------------------------------------
        // 🔵 2. АВТОРИЗАЦИЯ
        // -------------------------------------------------------------
        login.openLoginPage(BASE_URL)
                .enterUsername(Users.DAVLAT.getUsername())
                .enterPassword(Users.DAVLAT.getPassword())
                .clickLogin()
                .verifyLogin();

        // -------------------------------------------------------------
        // 🔵 3. РАБОЧЕЕ МЕСТО
        // -------------------------------------------------------------
        workspace.openWorkspaceAndSection("Розничный менеджер", "Заявки");

        // -------------------------------------------------------------
        // 🔵 4. КОНСУЛЬТАЦИЯ + ПОДБОР ПРОДУКТА + СОЗДАНИЕ ЗАЯВКИ
        // -------------------------------------------------------------
        credit.startConsultation(client)
                .selectProduct()
                .createApplication()
                .saveApplicationNumber();

        // -------------------------------------------------------------
        // 🔵 5. ЗАПОЛНЕНИЕ АНКЕТЫ ЗАЁМЩИКА
        // -------------------------------------------------------------
        borrower.addBorrower()
                .fillMainInfo()
                .fillPassport()
                .fillRegistrationAddress()
                .fillActualAddress()
                .fillAdditionalInfo()
                .addIncome()
                .addExpense()
                .fillSocialData()
                .fillRiskAssessment();

        // -------------------------------------------------------------
        // 🔵 6. ПРОХОЖДЕНИЕ СТАДИЙ ОБРАБОТКИ
        // -------------------------------------------------------------
        credit.passStage("Проверка клиента")
                .passStage("Заполните данные обеспечения и поручительства");

        // -------------------------------------------------------------
        // 🔵 7. ЗАГРУЗКА ДОКУМЕНТОВ
        // -------------------------------------------------------------
        credit.uploadDocuments()
                .passStage("Вложить документы и отправить на рассмотрение");

        // -------------------------------------------------------------
        // 🔵 8. ПОИСК ЗАЯВКИ ПО НОМЕРУ
        // -------------------------------------------------------------
        workspace.openWorkspace("ИКОК")
                .openSection("Заявки");

        credit.findApplication();

        // -------------------------------------------------------------
        // 🔵 9. СОЗДАНИЕ ДОГОВОРА
        // -------------------------------------------------------------
        credit.createContract();

        // -------------------------------------------------------------
        // 🔵 10. ВЫДАЧА КРЕДИТА
        // -------------------------------------------------------------
        credit.issueCredit("Наличными");
    }
}

