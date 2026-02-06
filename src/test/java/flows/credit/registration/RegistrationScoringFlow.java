package flows.credit.registration;

import core.base.UiContext;
import io.qameta.allure.Step;

public class RegistrationScoringFlow {

    private final UiContext ui;

    public RegistrationScoringFlow(UiContext ui) {
        this.ui = ui;
    }

    @Step("Оценка информации заемщика")
    public void fillScoringInfo() {

        ui.lookupComponent
                .setHandBookFieldByValueCheck("Семейное положение","Мучаррад (мард)")
                .setFieldByValueCheck("Количество иждивенцев (строка)","0")
                .setFieldByValueCheck("Общий стаж, мес","60")
                .setHandBookFieldByValueCheck("Тип занятости","Имеет другой источник дохода")
                .setHandBookFieldByValueCheck("Причина отсутствия работы","Получатель Д/П");

        ui.basePage.scrollToTop();

        ui.buttonsComponent
                .clickButtonByContainNameCheck("ОЦЕНКА ИНФОРМАЦИИ");

        ui.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Тип собственности на недвижимость",
                        "Более 2-ух квартир"
                )
                .setHandBookFieldByValueCheck(
                        "Тип владения автомобилем",
                        "Есть автомобиль"
                );
        // Обязательное согласие на обработку данных БКИ
        ui.checkboxComponent
                .ensureCheckboxBKI("IsConsentBKIProcessing");
    }
}
