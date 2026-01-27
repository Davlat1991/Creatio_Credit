package flows.credit.registration;

import core.base.UiContext;
import io.qameta.allure.Step;

public class RegistrationPrintFlow {

    private final UiContext ctx;

    public RegistrationPrintFlow(UiContext ctx) {
        this.ctx = ctx;
    }

    @Step("Сохранение и печать анкеты-заявки")
    public void printApplication() {

        ctx.basePage
                .clickButtonByNameCheck("Сохранить")
                .clickButtonByNameCheck("Закрыть");

        ctx.buttonsComponent
                .clickButtonByContainName("Участники заявки");

        ctx.basePage
                .clickButtonByNameCheck("Заемщик");

        ctx.detailComponent
                .openDetailMenu("Участники заявки");

        ctx.menuComponent
                .clickButtonByLiName("Печать Анкеты-заявки");
    }
}
