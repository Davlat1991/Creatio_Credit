package flows.credit.registration;

import core.base.TestContext;
import io.qameta.allure.Step;

public class RegistrationParticipantsFlow {

    private final TestContext ctx;

    public RegistrationParticipantsFlow(TestContext ctx) {
        this.ctx = ctx;
    }

    @Step("Добавление заемщика")
    public void addBorrower() {

        ctx.buttonsComponent
                .clickButtonByContainName("Участники заявки")
                .doubleclickButtonByName("Заемщик"); //Метод для продолжения заявки

        /*ctx.basePage
                .clickButtonById("BnzVwFinApplicationAllParticipantDetailAddTypedRecordButtonButton-imageEl");

        ctx.menuComponent
                .clickButtonByLiName("Заемщик");*/
    }

    @Step("Заполнение данных карьеры заемщика")
    public void fillCareerDetails() {
        ctx.buttonsComponent
                .clickButtonByContainNameCheck("ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ");

        ctx.detailPage.clickAddRecordInDetail("Карьера");

        ctx.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Тип занятости",
                        "Предприниматель"
                );

        ctx.lookupComponent
                .setFieldByValueCheck(
                        "Наименование организации",
                        "Агропром"
                )
                .setHandBookFieldByValueCheck(
                        "Должность",
                        "Агроном"
                );

        //Дата начало
        ctx.dateFieldComponent
                .setDateFieldByMarker("StartDate", "01.01.2020");


        //LLC "Agroprom Hujand"
        ctx.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Работодатель",
                        "ЧДММ \"АГРОПРОМ ХУЧАНД\""
                );

        ctx.lookupComponent
                .setFieldByValueCheck(
                        "Полное название должности",
                        "Агроном"
                );

        ctx.contractPage
                .clickButtonByNameCheck("Сохранить");
    }



}
