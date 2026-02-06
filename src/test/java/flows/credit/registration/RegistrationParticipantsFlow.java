package flows.credit.registration;

import core.base.UiContext;
import core.base.common.career.CareerField;
import io.qameta.allure.Step;

public class RegistrationParticipantsFlow {

    private final UiContext ui;

    public RegistrationParticipantsFlow(UiContext ui) {
        this.ui = ui;
    }

    @Step("Добавление заемщика")
    public void addBorrower() {

        ui.buttonsComponent
                .clickButtonByContainName("Участники заявки");
                //.doubleclickButtonByName("Заемщик"); //Метод для продолжения заявки
        ui.basePage
                .clickButtonById("BnzVwFinApplicationAllParticipantDetailAddTypedRecordButtonButton-imageEl");

        ui.menuComponent
                .clickButtonByLiName("Заемщик");
    }


    @Step("Заполнение данных карьеры заемщика")
    public void fillCareerDetailsSelfEmployed() {
        ui.buttonsComponent
                .clickButtonByContainNameCheck("ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ");

        ui.detailPage.clickAddRecordInDetail("Карьера");

        ui.careerComponent
                .setLookupByMarker(CareerField.EMPLOYMENT_TYPE, "Предприниматель")
                .setTextByMarker(CareerField.ORGANIZATION_NAME, "Агропром")
                .setLookupByMarker(CareerField.POSITION, "Агроном")
                .setTextByMarker(CareerField.START_DATE, "01.01.2020")
                .setLookupByMarker(CareerField.EMPLOYER, "ЧДММ ММК-Агро");

        ui.contractPage
                .clickButtonByNameCheck("Сохранить");
    }

    @Step("Заполнение данных карьеры заемщика")
    public void fillCareerDetailsEmployee() {
        ui.buttonsComponent
                .clickButtonByContainNameCheck("ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ");

        ui.detailPage.clickAddRecordInDetail("Карьера");

        ui.careerComponent
                .setLookupByMarker(CareerField.EMPLOYMENT_TYPE, "Полная занятость")
                .setTextByMarker(CareerField.ORGANIZATION_NAME, "Агро-холдинг")
                .setLookupByMarker(CareerField.POSITION, "Бухгалтер")
                .setTextByMarker(CareerField.START_DATE, "01.01.2020")
                .setLookupByMarker(CareerField.EMPLOYER, "ЧДММ \"Раисагрохолдинг\"");

        ui.contractPage
                .clickButtonByNameCheck("Сохранить");


    }

}
