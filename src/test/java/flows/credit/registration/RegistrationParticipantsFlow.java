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
                .setLookupByMarker(CareerField.EMPLOYMENT_TYPE, "Другой учитель")
                .setTextByMarker(CareerField.ORGANIZATION_NAME, "МТГ \"Мактаби П.Пулотов\"")
                .setLookupByMarker(CareerField.POSITION, "Ведущий специалист")
                .setTextByMarker(CareerField.START_DATE, "15.01.2024")
                .setLookupByMarker(CareerField.EMPLOYER, "МТГ \"Мактаби П.Пулотов\"")
                .setTextByMarker(CareerField.POSITION_FULL_NAME, "Омузгори забони англиси")
                .setTextByMarker(CareerField.DUE_DATE, "15.01.2034");

        ui.contractPage
                .clickButtonByNameCheck("Сохранить");


    }

}
