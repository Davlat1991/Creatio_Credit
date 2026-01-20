package flows.credit;

import core.base.TestContext;
import io.qameta.allure.Step;

public class ClientSearchFlow {

    private final TestContext ctx;

    public ClientSearchFlow(TestContext ctx) {
        this.ctx = ctx;
    }

    @Step("Поиск клиента по ФИО")
    public void searchClient(
            String lastName,
            String firstName,
            String middleName
    ) {
        ctx.fieldPage
                .setFieldByValue("Фамилия", lastName, true, false)
                .setFieldByValue("Имя", firstName, true, false)
                .setFieldByValue("Отчество", middleName, true, false);

        ctx.contractPage.clickButtonByNameCheck("Поиск");
    }
}
