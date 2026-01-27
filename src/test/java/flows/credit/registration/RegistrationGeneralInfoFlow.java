package flows.credit.registration;

import core.base.UiContext;
import io.qameta.allure.Step;

public class RegistrationGeneralInfoFlow {

    private final UiContext ctx;

    public RegistrationGeneralInfoFlow(UiContext ctx) {
        this.ctx = ctx;
    }

    @Step("Заполнение общей информации регистрации")
    public void fillGeneralInfo() {

        ctx.lookupComponent
                .setHandBookFieldByValueCheck("Регион использования кредита", "ш. Хучанд")
                .setHandBookFieldByValueCheck("Источник информации", "Интернет/Сайт");
    }
}
