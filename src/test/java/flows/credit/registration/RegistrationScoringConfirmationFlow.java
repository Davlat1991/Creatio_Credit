package flows.credit.registration;

import core.base.UiContext;
import io.qameta.allure.Step;

/**
 * Flow отвечает за подтверждение этапа скоринга:
 * - прокрутка страницы;
 * - нажатие кнопки этапа;
 * - заполнение дополнительных параметров после подтверждения;
 * - установка обязательных чекбоксов.
 *
 * НЕ содержит логики заполнения анкетных данных.
 */
public class RegistrationScoringConfirmationFlow {

    private final UiContext ui;

    public RegistrationScoringConfirmationFlow(UiContext ui) {
        this.ui = ui;
    }

    @Step("Подтверждение этапа оценки информации заемщика")
    public void confirmScoring() {

        // Возврат в начало страницы перед подтверждением этапа
        ui.basePage.scrollToTop();

        // Подтверждение этапа скоринга
        ui.buttonsComponent
                .clickButtonByContainNameCheck("ОЦЕНКА ИНФОРМАЦИИ");

        // Дополнительные параметры после подтверждения
        ui.lookupComponent
                .setHandBookFieldByValueCheck("Тип собственности на недвижимость","Более 2-ух квартир")
                .setHandBookFieldByValueCheck("Тип владения автомобилем","Есть автомобиль");

        // Обязательное согласие на обработку данных БКИ
        ui.checkboxComponent
                .ensureCheckboxBKI("IsConsentBKIProcessing");
    }
}
