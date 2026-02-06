package flows.credit.registration;

import core.base.UiContext;
import io.qameta.allure.Step;

public class RegistrationPrintFlow {

    private final UiContext ui;

    public RegistrationPrintFlow(UiContext ui) {
        this.ui = ui;
    }

    @Step("Сохранение и печать анкеты-заявки")
    public void printApplication() {

        ui.basePage
                .clickButtonByNameCheck("Сохранить")
                .clickButtonByNameCheck("Закрыть");

        ui.buttonsComponent
                .clickButtonByContainName("Участники заявки");

        ui.basePage
                .clickButtonByNameCheck("Заемщик");

        ui.detailComponent
                .openDetailMenu("Участники заявки");

        ui.menuComponent
                .clickButtonByLiName("Печать Анкеты-заявки");
    }
}
