package flows.credit.registration;

import core.base.UiContext;
import core.base.common.career.CareerField;
import io.qameta.allure.Step;

public class RegistrationParticipantsFlow {

    private final UiContext ctx;

    public RegistrationParticipantsFlow(UiContext ctx) {
        this.ctx = ctx;
    }

    @Step("Добавление заемщика")
    public void addBorrower() {

        ctx.buttonsComponent
                .clickButtonByContainName("Участники заявки");
                //.doubleclickButtonByName("Заемщик"); //Метод для продолжения заявки
        ctx.basePage
                .clickButtonById("BnzVwFinApplicationAllParticipantDetailAddTypedRecordButtonButton-imageEl");

        ctx.menuComponent
                .clickButtonByLiName("Заемщик");
    }

    @Step("Заполнение данных карьеры заемщика")
    public void fillCareerDetailsSelfEmployed() {
        ctx.buttonsComponent
                .clickButtonByContainNameCheck("ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ");

        ctx.detailPage.clickAddRecordInDetail("Карьера");

        ctx.careerComponent
                .setLookupByMarker(CareerField.EMPLOYMENT_TYPE, "Предприниматель")
                .setTextByMarker(CareerField.ORGANIZATION_NAME, "Агропром")
                .setLookupByMarker(CareerField.POSITION, "Агроном")
                .setTextByMarker(CareerField.START_DATE, "01.01.2020")
                .setLookupByMarker(CareerField.EMPLOYER, "ЧДММ ММК-Агро");

        ctx.contractPage
                .clickButtonByNameCheck("Сохранить");
    }

    @Step("Заполнение данных карьеры заемщика")
    public void fillCareerDetailsEmployed() {
        ctx.buttonsComponent
                .clickButtonByContainNameCheck("ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ");

        ctx.detailPage.clickAddRecordInDetail("Карьера");

        ctx.careerComponent
                .setLookupByMarker(CareerField.EMPLOYMENT_TYPE, "Полная занятость")
                .setTextByMarker(CareerField.ORGANIZATION_NAME, "Агро-холдинг")
                .setLookupByMarker(CareerField.POSITION, "Бухгалтер")
                .setTextByMarker(CareerField.START_DATE, "01.01.2020")
                .setLookupByMarker(CareerField.EMPLOYER, "ЧДММ \"Раисагрохолдинг\"");

        ctx.contractPage
                .clickButtonByNameCheck("Сохранить");
    }






}
