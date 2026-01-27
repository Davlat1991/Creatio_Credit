package tests.credit.negative.standard;


import core.base.BaseTest;

import core.data.TestData;
import core.data.TestDataLoader;
import core.data.contacts.ContactData;
import core.data.mappers.ContactDataMapper;
import core.data.mappers.LoginDataMapper;
import core.data.users.LoginData;
import core.enums.Workspace;
import core.pages.routes.ClientDataPage;
import core.pages.routes.SimpleRoutePage;
import flows.common.AuthorizationFlow;
import flows.common.WorkspaceFlow;
import flows.credit.*;
import io.qameta.allure.*;
import org.testng.annotations.Test;

                                        //НУЖНО ДОРОБОТАТЬ ТЕСТ !!!


@Epic("Creatio Credit")
@Feature("Стандартный маршрут")
public class StandardRouteNegativeTest extends BaseTest {

    @Test(description = "Негатив: отказ клиента")
    @Story("Отказ по стандартному маршруту")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Davlat")
    public void standardRouteDeclined() {
        // ============================================================
        // 1. TEST DATA (ТОЛЬКО ЗДЕСЬ)
        // ============================================================
        TestData data = TestDataLoader.load();

        LoginData retailManager =
                LoginDataMapper.from(data.user("retailManager"));

        ContactData contact =
                ContactDataMapper.from(data.defaultContact());

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


        new ClientDataPage()
                .fillClientData("Ошибочный клиент", "000000")
                .next()
                .fillBasicFields("50000", "12")
                .save()
                .sendToReview()
                .decline()
                .verifyStatus("Отказано");
    }
}
