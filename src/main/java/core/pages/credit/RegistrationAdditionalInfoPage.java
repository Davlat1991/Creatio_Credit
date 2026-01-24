package core.pages.credit;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class RegistrationAdditionalInfoPage {

    @Step("Очистить поле 'Причина отсутствия работы', если оно заполнено")
    public void clearReasonOfNoWorkIfPresent() {

        SelenideElement reasonBlock =
                $("[data-item-marker='BnzReasonForNotWorking']")
                        .shouldBe(visible);

        SelenideElement input =
                reasonBlock.$("input.base-edit-input")
                        .shouldBe(visible);

        String currentValue = input.getValue();

        if (currentValue == null || currentValue.trim().isEmpty()) {
            return;
        }

        reasonBlock.$(".base-edit-clear-icon")
                .shouldBe(visible)
                .click();

        input.shouldHave(attribute("value", ""));
    }
}
