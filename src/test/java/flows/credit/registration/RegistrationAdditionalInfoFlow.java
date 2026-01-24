package flows.credit.registration;

import core.base.TestContext;
import core.data.registration.EmploymentType;
import io.qameta.allure.Step;

/**
 * Flow отвечает ТОЛЬКО за заполнение скоринговых данных.
 * Не содержит навигации, кнопок подтверждения и переходов этапов.
 */
public class RegistrationAdditionalInfoFlow {

    private final TestContext ctx;

    public RegistrationAdditionalInfoFlow(TestContext ctx) {
        this.ctx = ctx;

    }


    @Step("Заполнение базовых скоринговых данных заемщика")
    public void fillBaseScoringData() {



        ctx.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Семейное положение",
                        "Мучаррад (мард)"
                );

        ctx.lookupComponent
                .setFieldByValueCheck(
                        "Количество иждивенцев (строка)",
                        "0"
                )
                .setFieldByValueCheck(
                        "Общий стаж",
                        "36"
                )
                .setFieldByValueCheck(
                        "Общий стаж, лет",
                        "3"
                )
                .setFieldByValueCheck(
                        "Общий стаж, мес",
                        "36"
                )
                .setFieldByValueCheck(
                        "Стаж на последнем месте, лет",
                        "2"
                )
                .setFieldByValueCheck(
                        "Стаж на последнем месте, мес",
                        "24"
                );


    }

    @Step("Подготовка поля 'Причина отсутствия работы'")
    public void prepareReasonOfNoWork() {
        ctx.additionalInfoPage.clearReasonOfNoWorkIfPresent();
    }

    @Step("Заполнение данных самозанятого")
    public void fillSelfEmployedData() {

        prepareReasonOfNoWork();

        ctx.lookupComponent
                .setHandBookFieldByValueCheck("Должность", "Учитель")
                .setFieldByValueCheck("Наименование организации", "ВУЗ")
                .setHandBookFieldByValueCheck("Роль", "Учитель")
                .setHandBookFieldByValueCheck("Социальный статус",
                        "Рабочий в частной фирме/организации")
                .setHandBookFieldByValueCheck("Образование", "2 и более высших")
                .setFieldByValueCheck("Полное название должности", "Учитель")
                .setHandBookFieldByValueCheck("Работодатель", "Общий отдел")
                .setHandBookFieldByValueCheck("Организационно-правовая форма", "ВУЗ")
                .setHandBookFieldByValueCheck("Деловая репутация",
                        "Управленческая должность");
    }


    @Step("Выбор типа занятости: {employmentType}")
    public void selectEmploymentType(EmploymentType employmentType) {

        ctx.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Тип занятости",
                        employmentType.getUiName()
                );

        prepareReasonOfNoWork();

        ctx.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Должность",
                        "Агроном"
                )
                .setFieldByValueCheck(
                        "Наименование организации",
                        "Агропром")
                .setHandBookFieldByValueCheck(
                        "Роль",
                        "Руководитель")
                .setHandBookFieldByValueCheck(
                        "Образование",
                        "2 и более высших")
                .setFieldByValueCheck(
                        "Полное название должности",
                        "Агроном")
                .setHandBookFieldByValueCheck(
                        "Организационно-правовая форма",
                        "Дехканские (фермерские) хозяйства")
                .setHandBookFieldByValueCheck(
                        "Деловая репутация",
                        "Управленческая должность");


    }



    @Step("Заполнение данных работающего в организации")
    public void fillEmployedData() {
        prepareReasonOfNoWork();

        ctx.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Должность",
                        "Агроном"
                )
                .setFieldByValueCheck(
                        "Наименование организации",
                        "Агропром")
                .setHandBookFieldByValueCheck(
                        "Роль",
                        "Руководитель")
                .setHandBookFieldByValueCheck(
                        "Образование",
                        "2 и более высших")
                .setFieldByValueCheck(
                        "Полное название должности",
                        "Агроном")
                .setHandBookFieldByValueCheck(
                        "Организационно-правовая форма",
                        "Дехканские (фермерские) хозяйства")
                .setHandBookFieldByValueCheck(
                        "Деловая репутация",
                        "Управленческая должность");

    }


    @Step("Заполнение данных клиента с иным источником дохода")
    public void fillOtherIncomeData() {
        setReasonOfNoWork("Получатель Д/П");
    }



    private void setReasonOfNoWork(String reason) {
        ctx.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Причина отсутствия работы",
                        reason
                );


    }

}
