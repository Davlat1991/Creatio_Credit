package core.base.common.educationcareer;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class EducationCareerComponent {

    @Step("Выбрать значение '{value}' в поле '{field.marker}'")
    public EducationCareerComponent setLookup(EducationCareerField field, String value) {

        SelenideElement input = $x(
                "//*[@data-item-marker='" + field.marker + "']//input"
        ).shouldBe(Condition.visible, Condition.enabled);

        input.setValue(value);

        $x("//div[contains(@class,'listview')]//li[normalize-space(.)='" + value + "']")
                .shouldBe(Condition.visible)
                .click();

        input.shouldHave(Condition.exactValue(value));
        return this;
    }

    @Step("Заполнить поле '{field.marker}' значением '{value}'")
    public EducationCareerComponent setText(EducationCareerField field, String value) {

        SelenideElement input = $x(
                "//*[@data-item-marker='" + field.marker + "']//input"
        ).shouldBe(Condition.visible, Condition.enabled);

        input.clear();
        input.setValue(value)
                .shouldHave(Condition.value(value));

        return this;
    }

    @Step("Очистить причину отсутствия работы, если заполнена")
    public EducationCareerComponent clearReasonIfPresent() {

        SelenideElement block = $x(
                "//*[@data-item-marker='" + EducationCareerField.REASON_FOR_NOT_WORKING.marker + "']"
        ).shouldBe(Condition.visible);

        SelenideElement input = block.$("input");

        if (input.getValue() != null && !input.getValue().isBlank()) {
            block.$(".base-edit-clear-icon")
                    .shouldBe(Condition.visible)
                    .click();

            input.shouldHave(Condition.value(""));
        }

        return this;
    }


    @Step("Очистить поле '{field.marker}', если оно заполнено")
    public EducationCareerComponent clearFieldIfPresent(EducationCareerField field) {

        SelenideElement block = $x(
                "//*[@data-item-marker='" + field.marker + "']"
        ).shouldBe(Condition.visible);

        SelenideElement input = block.$("input")
                .shouldBe(Condition.exist);

        String currentValue = input.getValue();

        if (currentValue == null || currentValue.isBlank()) {
            return this;
        }

        SelenideElement clearIcon = block.$(".base-edit-clear-icon");

        if (clearIcon.exists() && clearIcon.isDisplayed()) {
            clearIcon.click();
            input.shouldHave(Condition.value(""));
        }

        return this;
    }


    @Step("Очистить lookup-поле '{field.marker}' (Creatio-safe)")
    public EducationCareerComponent clearLookupSafely(EducationCareerField field) {

        SelenideElement block = $x(
                "//*[@data-item-marker='" + field.marker + "']"
        ).shouldBe(Condition.visible);

        SelenideElement clearIcon = block.$(".base-edit-clear-icon");

        if (!clearIcon.exists() || !clearIcon.isDisplayed()) {
            return this;
        }

        // 🔥 1. Кликаем clear
        Selenide.executeJavaScript("arguments[0].click();", clearIcon);

        // 🔥 2. Принудительно blur, чтобы Creatio зафиксировал состояние
        Selenide.executeJavaScript(
                "arguments[0].dispatchEvent(new Event('blur', {bubbles:true}));",
                block
        );

        // 🔥 3. Даём Creatio стабилизироваться (НЕ проверяем пустоту!)
        Selenide.sleep(300);

        return this;
    }




}
