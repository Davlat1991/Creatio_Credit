package flows.credit.participants;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import core.base.UiContext;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class PzlFlow {

    private final UiContext ui;

    public PzlFlow(UiContext ui) {
        this.ui = ui;
    }

    @Step("Установка значения ПЗЛ = {expectedValue}")
    public void setPzl(boolean expectedValue) {

        SelenideElement pzlBlock = $("[data-item-marker='ПЗЛ']")
                .shouldBe(Condition.visible);

        SelenideElement yesRadio = pzlBlock
                .find("[data-item-marker='BnzPZLTrue']")
                .closest(".radio-button-container")
                .find(".t-radio-wrap");

        SelenideElement noRadio = pzlBlock
                .find("[data-item-marker='BnzPZLFalse']")
                .closest(".radio-button-container")
                .find(".t-radio-wrap");

        boolean isYesSelected =
                yesRadio.has(Condition.cssClass("t-radio-checked"));

        if (expectedValue && !isYesSelected) {
            com.codeborne.selenide.Selenide.executeJavaScript("arguments[0].click();", yesRadio);
        }

        if (!expectedValue && isYesSelected) {
            com.codeborne.selenide.Selenide.executeJavaScript("arguments[0].click();", noRadio);
        }

        if (expectedValue)
            yesRadio.shouldHave(Condition.cssClass("t-radio-checked"));
        else
            noRadio.shouldHave(Condition.cssClass("t-radio-checked"));
    }
}