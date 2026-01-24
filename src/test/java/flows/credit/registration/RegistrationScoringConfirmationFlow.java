package flows.credit.registration;

import core.base.TestContext;
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

    private final TestContext ctx;

    public RegistrationScoringConfirmationFlow(TestContext ctx) {
        this.ctx = ctx;
    }

    @Step("Подтверждение этапа оценки информации заемщика")
    public void confirmScoring() {

        // Возврат в начало страницы перед подтверждением этапа
        ctx.basePage.scrollToTop();

        // Подтверждение этапа скоринга
        ctx.buttonsComponent
                .clickButtonByContainNameCheck("ОЦЕНКА ИНФОРМАЦИИ");

        // Дополнительные параметры после подтверждения
        ctx.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Тип собственности на недвижимость",
                        "Более 2-ух квартир"
                )
                .setHandBookFieldByValueCheck(
                        "Тип владения автомобилем",
                        "Есть автомобиль"
                );

        // Обязательное согласие на обработку данных БКИ
        ctx.checkboxComponent
                .ensureCheckboxBKI("IsConsentBKIProcessing");
    }
}
