package flows.credit.registration;

import core.base.TestContext;
import io.qameta.allure.Step;

public class RegistrationScoringFlow {

    private final TestContext ctx;

    public RegistrationScoringFlow(TestContext ctx) {
        this.ctx = ctx;
    }

    @Step("Оценка информации заемщика")
    public void fillScoringInfo() {

        ctx.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Семейное положение",
                        "Мучаррад (мард)"
                )
                .setFieldByValueCheck(
                        "Количество иждивенцев (строка)",
                        "0"
                )
                .setFieldByValueCheck(
                        "Общий стаж, мес",
                        "60"
                )
                .setHandBookFieldByValueCheck(
                        "Тип занятости",
                        "Имеет другой источник дохода"
                )
                .setHandBookFieldByValueCheck(
                        "Причина отсутствия работы",
                        "Получатель Д/П"
                );

        ctx.basePage.scrollToTop();

        ctx.buttonsComponent
                .clickButtonByContainNameCheck("ОЦЕНКА ИНФОРМАЦИИ");

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
