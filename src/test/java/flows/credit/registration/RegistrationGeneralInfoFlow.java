package flows.credit.registration;

import core.base.UiContext;
import io.qameta.allure.Step;

public class RegistrationGeneralInfoFlow {

    private final UiContext ui;

    public RegistrationGeneralInfoFlow(UiContext ui) {
        this.ui = ui;
    }

    @Step("Заполнение общей информации регистрации")
    public void fillGeneralInfo() {

        ui.lookupComponent
                .setHandBookFieldByValueCheck("Регион использования кредита", "ш. Хучанд")
                .setHandBookFieldByValueCheck("Источник информации", "Интернет/Сайт");
    }
}
