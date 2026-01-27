package flows.credit;

import core.base.UiContext;
import io.qameta.allure.Step;

public class ClientSearchFlow {

    private final UiContext ctx;

    public ClientSearchFlow(UiContext ctx) {
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
