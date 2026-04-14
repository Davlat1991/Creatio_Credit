package tests.credit.negative.simple;


import core.base.BaseTest;
import core.data.TestData;
import core.data.TestDataLoader;
import core.data.contacts.ContactData;
import core.data.contacts.ContactDataFactory;
import core.data.mappers.LoginDataMapper;
import core.data.users.LoginData;
import core.enums.Workspace;
import core.pages.routes.SimpleRoutePage;
import flows.common.AuthorizationFlow;
import flows.common.WorkspaceFlow;
import flows.credit.*;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import core.data.contacts.ContactDataFactory;



                                //НУЖНО ДОРОБОТАТЬ ТЕСТ !!!

@Epic("Creatio Credit")
@Feature("Упрощённый маршрут")
public class SimpleRouteNegativeTest extends BaseTest {

    @Test(
            description = "Негативный тест: обязательные поля",
            groups = "negative"
    )
    @Story("Negative: пустые обязательные поля")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Davlat")
    @Description("Проверка, что Creatio не даёт сохранить заявку, если обязательные поля не заполнены")
    public void emptyRequiredFieldsTest() {

        // ============================================================
        // 1. TEST DATA (ТОЛЬКО ЗДЕСЬ)
        // ============================================================
        TestData data = TestDataLoader.load();

        LoginData retailManager =
                LoginDataMapper.from(data.user("retailManager"));

        ContactData contact =
                ContactDataFactory.defaultContact();

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

        // ============================================================
        // 🔵 4. АВТОРИЗАЦИЯ
        // ============================================================
        authFlow.login(retailManager);

        // ============================================================
        // 🔵 5. РАБОЧЕЕ МЕСТО
        // ============================================================
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
                "Сомони Чумхурии Точикистон");

        new SimpleRoutePage()
                .shouldHaveError("Заполните обязательные поля");
    }
}



