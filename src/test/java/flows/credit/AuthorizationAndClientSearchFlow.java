package flows.credit;

import core.base.TestContext;
import core.data.contacts.ContactData;
import io.qameta.allure.Step;

/**
 * Business flow:
 * старт консультации и поиск клиента
 * (БЕЗ логина и пароля)
 */
public class AuthorizationAndClientSearchFlow {

    private final TestContext ctx;

    public AuthorizationAndClientSearchFlow(TestContext ctx) {
        this.ctx = ctx;
    }

    @Step("Выбор рабочего места, ввод ФИО и запуск консультации")
    public void startConsultation(
            String workspace,
            ContactData contact
    ) {
        selectWorkspace(workspace);
        fillClientFio(contact);
        startConsultation();
    }

    // ======================
    // INTERNAL STEPS
    // ======================

    private void selectWorkspace(String workspace) {
        ctx.workspaceSteps.selectWorkAccess(workspace);
        ctx.basePage.clickButtonById("view-button-OBSW-imageEl");
    }

    private void fillClientFio(ContactData contact) {
        ctx.fieldPage
                .setFieldByValue("Фамилия", contact.getLastName(), true, false)
                .setFieldByValue("Имя", contact.getFirstName(), true, false)
                .setFieldByValue("Отчество", contact.getMiddleName(), true, false);
    }

    private void startConsultation() {
        ctx.contractPage.clickButtonByNameCheck("Поиск");
        ctx.basePage.clickButtonByDataItemMaker("Начать консультацию");
        ctx.detailPage.openDetailByName("Оформить заявку");
        ctx.consultationPanel.registerProductByDIM(
                "consultation-theme-7a0f11cc-756d-474a-885f-1dd64eeca5b3"
        );
    }
}
