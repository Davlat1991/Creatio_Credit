package flows.credit.participants;

import core.base.UiContext;
import core.data.participants.ParticipantData;
import core.data.registration.RegistrationIncomeExpensesData;
import io.qameta.allure.Step;

public class PledgerQuestionnaireFlow {

    private final UiContext ui;

    public PledgerQuestionnaireFlow(UiContext ui) {
        this.ui = ui;
    }
    @Step("Заполнение анкеты залогодателя")
    public void fill(ParticipantData participant, RegistrationIncomeExpensesData incomeExpensesData) {

        ui.lookupComponent
                .selectDropdownValueWithCheckNew("BnzAffiliation", "Мобильный");


        // Регистрация
        ui.buttonsComponent.doubleclickButtonByName("Регистрация");
        ui.contactAddressPage.waitForAddressPageLoaded();

        ui.dateFieldComponent
                .setDateFieldByMarker("BnzRegistrationDate", "01.01.2020");

        ui.contractPage.clickButtonByNameCheck("Сохранить");
        ui.basePage.waitForPage();

        ui.lookupComponent
                .selectDropdownValue("Вид связи", "Партнер");

        // Фактический
       /* ui.buttonsComponent.doubleclickButtonByName("Фактический");
        ui.contactAddressPage.waitForAddressPageLoaded();

        ui.dateFieldComponent
                .setDateFieldByMarker("BnzRegistrationDate", "01.01.2020");

        ui.contractPage.clickButtonByNameCheck("Сохранить");
        ui.basePage.waitForPage();*/

        ui.lookupComponent
                .setHandBookFieldByValueCheck("Национальность", "Таджик / таджичка");

        ui.buttonsComponent
                .clickButtonByContainNameCheck("ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ");

        ui.lookupComponent
                .setHandBookFieldByValueCheck("Семейное положение", "Оиладор (зан)");

        ui.lookupComponent
                .setHandBookFieldByValueCheck("Уровень риска", "Низкий");

        ui.contractPage.clickButtonByNameCheck("Сохранить");
        ui.contractPage.clickButtonByNameCheck("Закрыть");
    }
}