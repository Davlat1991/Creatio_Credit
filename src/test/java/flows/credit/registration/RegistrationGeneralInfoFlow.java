package flows.credit.registration;

import core.base.TestContext;
import io.qameta.allure.Step;

public class RegistrationGeneralInfoFlow {

    private final TestContext ctx;

    public RegistrationGeneralInfoFlow(TestContext ctx) {
        this.ctx = ctx;
    }

    @Step("Заполнение общей информации регистрации")
    public void fillGeneralInfo() {

        ctx.lookupComponent
                .setHandBookFieldByValueCheck("Регион использования кредита", "ш. Хучанд")
                .setHandBookFieldByValueCheck("Источник информации", "Интернет/Сайт");
    }
}
