
package flows.credit.participants;

import core.base.UiContext;
import core.data.participants.ParticipantData;
import io.qameta.allure.Step;

public class ParticipantsAddFlow {

    private final UiContext ui;

    public ParticipantsAddFlow(UiContext ui) {
        this.ui = ui;
    }

    @Step("Добавление участника {participant.role}")
    public void addParticipant(ParticipantData participant) {

        ui.basePage
                .clickButtonById("BnzVwFinApplicationAllParticipantDetailAddTypedRecordButtonButton-imageEl");

        ui.menuComponent.clickButtonByLiName("Физ. лицо");
        ui.menuComponent.clickButtonByLiName(participant.getRole().getUiValue());

        ui.lookupComponent
                .setModalSearchField("Surname", participant.getLastName())
                .setModalSearchField("GivenName", participant.getFirstName())
                .setModalSearchField("MiddleName", participant.getMiddleName());

        ui.basePage.clickButtonByName("Поиск");
        ui.basePage.clickButtonByName("Выбрать");

        ui.basePage.waitForPage();
    }
}













/*package flows.credit.participants;
import core.base.UiContext;
import core.data.participants.ParticipantData;
import io.qameta.allure.Step;

public class ParticipantsAddFlow {

    private final UiContext ui;

    public ParticipantsAddFlow(UiContext ui) {
        this.ui = ui;
    }

    @Step("Добавление участника {participant.role}")
    public void addParticipant(ParticipantData participant) {

        ui.basePage
                .clickButtonById("BnzVwFinApplicationAllParticipantDetailAddTypedRecordButtonButton-imageEl");

        ui.menuComponent.clickButtonByLiName("Физ. лицо");
        ui.menuComponent.clickButtonByLiName(participant.getRole().getUiValue());

        ui.lookupComponent
                .setModalSearchField("Surname"," ")
                .setModalSearchField("GivenName","Фарида")
                .setModalSearchField("MiddleName","Махкамбоевна");

        ui.basePage
                .clickButtonByName("Поиск");
        ui.basePage
                .clickButtonByName("Выбрать");

        ui.basePage.waitForPage();
    }
}







public class ParticipantsAddFlow {

    private final UiContext ui;

    public ParticipantsAddFlow(UiContext ui) {
        this.ui = ui;
    }

    @Step("Добавление участника {participant.role}")
    public void addParticipant(ParticipantData participant) {

        ui.basePage
                .clickButtonById("BnzVwFinApplicationAllParticipantDetailAddTypedRecordButtonButton-imageEl");

        ui.menuComponent.clickButtonByLiName("Физ. лицо");
        ui.menuComponent.clickButtonByLiName(participant.getRole().getUiValue());

        ui.lookupComponent
                .setModalSearchField("Surname","Чаборова")
                .setModalSearchField("GivenName","Дилафруз")
                .setModalSearchField("MiddleName","Кобилчоновна");

        ui.basePage
                .clickButtonByName("Поиск");
        ui.basePage
                .clickButtonByName("Выбрать");

        @Step("Заполнение анкеты залогодателя")
        public void fillPledgerQuestionnaire() {

            // Вид связи
            ui.lookupComponent
                    .setHandBookFieldByValueCheck("Вид связи", "Родственник");

            //Средства связи
            ui.lookupComponent
                    .selectDropdownValueWithCheckNew("BnzAffiliation", "Мобильный");

            // Регистрация

            ui.buttonsComponent
                    .doubleclickButtonByName("Регистрация");
            ui.contactAddressPage
                    .waitForAddressPageLoaded();

            ui.dateFieldComponent
                    .setDateFieldByMarker("BnzRegistrationDate", "01.01.2020");
            ui.contractPage
                    .clickButtonByNameCheck("Сохранить");

            ui.basePage
                    .waitForPage();

            // Фактический

            ui.buttonsComponent
                    .doubleclickButtonByName("Фактический");
            ui.contactAddressPage
                    .waitForAddressPageLoaded();
            ui.dateFieldComponent
                    .setDateFieldByMarker("BnzRegistrationDate", "01.01.2020");
            ui.contractPage
                    .clickButtonByNameCheck("Сохранить");
            ui.basePage
                    .waitForPage();

        }
    }

        // Связь с ПЗЛ
        // (true) выбрать "Да"
        // (false); выбрать "Нет"
        public void setPzlRelation(boolean expectedValue) {

            String value = expectedValue ? "Да" : "Нет";

            LogStep.info("⏳ Установка значения '" + value + "' в поле 'Связь с ПЗЛ'");

            SelenideElement pzlBlock = $("[data-item-marker='ПЗЛ']")
                    .shouldBe(Condition.visible);

            // 📎 Прикладываем DOM блока ПЗЛ
            AllureAttachments.attachHtml(
                    "🧩 ПЗЛ DOM (до)",
                    pzlBlock.getAttribute("outerHTML")
            );

            SelenideElement yesRadio = pzlBlock
                    .find("[data-item-marker='BnzPZLTrue']")
                    .closest(".radio-button-container")
                    .find(".t-radio-wrap");

            SelenideElement noRadio = pzlBlock
                    .find("[data-item-marker='BnzPZLFalse']")
                    .closest(".radio-button-container")
                    .find(".t-radio-wrap");

            boolean isYesSelectedBefore = yesRadio.has(Condition.cssClass("t-radio-checked"));

            if (expectedValue && !isYesSelectedBefore) {
                yesRadio.click();
            }

            if (!expectedValue && isYesSelectedBefore) {
                noRadio.click();
            }

            if (expectedValue) {
                yesRadio.shouldHave(Condition.cssClass("t-radio-checked"));
            } else {
                noRadio.shouldHave(Condition.cssClass("t-radio-checked"));
            }

            // 📎 DOM после
            AllureAttachments.attachHtml(
                    "🧩 ПЗЛ DOM (до)",
                    pzlBlock.getAttribute("outerHTML")
            );

            LogStep.info("✔ Поле 'Связь с ПЗЛ' успешно установлено в значение '" + value + "'");


            ui.lookupComponent
                    .setHandBookFieldByValueCheck("Национальность", "Таджик / таджичка");

            ui.buttonsComponent
                    .clickButtonByContainNameCheck("ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ");

            ui.lookupComponent
                    .setHandBookFieldByValueCheck(
                            "Семейное положение","Оиладор (зан)");

            ui.contractPage
                    .clickButtonByNameCheck("Сохранить");

            ui.contractPage
                    .clickButtonByNameCheck("Закрыть");
        }*/

