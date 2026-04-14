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
public class SimpleRouteBoundaryTest extends BaseTest {

    @Test(description = "Boundary: слишком короткое ФИО", groups = "boundary")
    @Story("Boundary: ФИО длиной 1 символ — недопустимо")
    @Severity(SeverityLevel.MINOR)
    @Owner("Davlat")
    @Description("Проверка ошибки при вводе в поле ФИО слишком короткого значения")
    public void fioTooShortTest() {

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

        new SimpleRoutePage()
                .waitOpened()
                .fillRequiredFields("А", "9000000000")
                .save()
                .shouldHaveError("Введите корректное ФИО");
    }


    @Test(description = "Boundary: минимальная допустимая длина ФИО", groups = "boundary")
    @Story("Boundary: минимально допустимое ФИО")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Davlat")
    @Description("Проверка успешного сохранения заявки при вводе минимально допустимого значения ФИО")
    public void fioValidMinLengthTest() {

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

        new SimpleRoutePage()
                .waitOpened()
                .fillRequiredFields("Ан", "9000000000")
                .save()
                .verifyStatus("Создано");
    }
}


