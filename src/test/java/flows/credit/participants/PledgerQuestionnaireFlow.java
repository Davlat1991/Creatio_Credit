package flows.credit.participants;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import core.base.UiContext;
import core.utils.AllureAttachments;
import core.utils.LogStep;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class PledgerQuestionnaireFlow {

    private final UiContext ui;

    public PledgerQuestionnaireFlow(UiContext ui) {
        this.ui = ui;
    }

    public void completePledgerQuestionnaire(boolean pzlRelation) {

        fillPledgerQuestionnaire();
        setPzlRelation(pzlRelation);
    }

    @Step("Заполнение анкеты залогодателя")
    public void fillPledgerQuestionnaire() {

        ui.lookupComponent
                .setHandBookFieldByValueCheck("Вид связи", "Родственник");

        ui.lookupComponent
                .selectDropdownValueWithCheckNew("BnzAffiliation", "Мобильный");

        // Регистрация
        ui.buttonsComponent.doubleclickButtonByName("Регистрация");
        ui.contactAddressPage.waitForAddressPageLoaded();

        ui.dateFieldComponent
                .setDateFieldByMarker("BnzRegistrationDate", "01.01.2020");

        ui.contractPage.clickButtonByNameCheck("Сохранить");
        ui.basePage.waitForPage();

        // Фактический
        ui.buttonsComponent.doubleclickButtonByName("Фактический");
        ui.contactAddressPage.waitForAddressPageLoaded();

        ui.dateFieldComponent
                .setDateFieldByMarker("BnzRegistrationDate", "01.01.2020");

        ui.contractPage.clickButtonByNameCheck("Сохранить");
        ui.basePage.waitForPage();
    }

    // ТВОЙ setPzlRelation — 그대로
    public void setPzlRelation(boolean expectedValue) {

        String value = expectedValue ? "Да" : "Нет";

        LogStep.info("⏳ Установка значения '" + value + "' в поле 'Связь с ПЗЛ'");

        SelenideElement pzlBlock = $("[data-item-marker='ПЗЛ']")
                .shouldBe(Condition.visible);

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

        boolean isYesSelectedBefore =
                yesRadio.has(Condition.cssClass("t-radio-checked"));

        if (expectedValue && !isYesSelectedBefore) yesRadio.click();
        if (!expectedValue && isYesSelectedBefore) noRadio.click();

        if (expectedValue)
            yesRadio.shouldHave(Condition.cssClass("t-radio-checked"));
        else
            noRadio.shouldHave(Condition.cssClass("t-radio-checked"));


        ui.lookupComponent
                .setHandBookFieldByValueCheck("Национальность", "Таджик / таджичка");

        ui.buttonsComponent
                .clickButtonByContainNameCheck("ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ");

        ui.lookupComponent
                .setHandBookFieldByValueCheck("Семейное положение","Оиладор (зан)");

        ui.contractPage.clickButtonByNameCheck("Сохранить");
        ui.contractPage.clickButtonByNameCheck("Закрыть");
    }
}