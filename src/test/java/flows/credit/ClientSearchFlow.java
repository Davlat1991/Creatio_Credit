package flows.credit;

import core.base.UiContext;
import io.qameta.allure.Step;

public class ClientSearchFlow {

    private final UiContext ui;

    public ClientSearchFlow(UiContext ui) {
        this.ui = ui;
    }

    @Step("Поиск клиента по ФИО")
    public void searchClient(
            String lastName,
            String firstName,
            String middleName
    ) {
        ui.fieldPage
                .setFieldByValue("Фамилия", lastName, true, false)
                .setFieldByValue("Имя", firstName, true, false)
                .setFieldByValue("Отчество", middleName, true, false);

        ui.contractPage.clickButtonByNameCheck("Поиск");
    }
}
