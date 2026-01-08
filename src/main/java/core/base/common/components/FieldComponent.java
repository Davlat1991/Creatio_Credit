package core.base.common.components;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;



public class FieldComponent extends Components {

    // ============================================================
    // 1) TEXT INPUT (универсальный)
    // ============================================================

    @Step("Ввести значение '{value}' в поле '{label}'")
    public FieldComponent setValue(String label, String value) {

        // Ищем input или textarea по label
        SelenideElement field = findField(label)
                .shouldBe(visible)
                .scrollIntoView(true);

        retryType(field, value);

        return this;
    }

    private SelenideElement findField(String label) {

        // 1️⃣ Пытаемся найти по label (идеальный случай)
        String byLabel =
                "//label[normalize-space()='" + label + "']/following::*[self::input or self::textarea][1]";
        if ($x(byLabel).exists()) {
            return $x(byLabel);
        }

        // 2️⃣ Fallback: по placeholder (Creatio часто так делает)
        String byPlaceholder =
                "//input[@placeholder='" + label + "'] | //textarea[@placeholder='" + label + "']";
        if ($x(byPlaceholder).exists()) {
            return $x(byPlaceholder);
        }

        // 3️⃣ Жёсткий фейл с понятной ошибкой
        throw new AssertionError("❌ Поле с label/placeholder '" + label + "' не найдено");
    }


    /**
     * Универсальная попытка ввода текста с fallback на JS
     */
    private void retryType(SelenideElement element, String value) {
        retry(() -> {
            try {
                element.clear();
                element.setValue(value);
            } catch (Throwable t) {
                executeJavaScript(
                        "arguments[0].value = arguments[1];" +
                                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                        element, value
                );
            }
            return true;
        }, 3, 300);
    }


    // ============================================================
    // 2) LOOKUP / СПРАВОЧНИК (универсальный)
    // ============================================================

    @Step("Выбрать '{value}' в справочнике '{label}'")
    public FieldComponent select(String label, String value) {

        SelenideElement fieldContainer = findFieldContainer(label)
                .shouldBe(visible)
                .scrollIntoView(true);

        String marker = fieldContainer.getAttribute("data-item-marker");

        // Сначала пробуем input внутри поля
        ElementsCollection inputs = fieldContainer.$$x(".//input[not(@type='hidden')]");
        if (!inputs.isEmpty()) {
            SelenideElement input = inputs.first().shouldBe(visible, enabled);
            input.click();
            input.sendKeys(Keys.CONTROL + "a");
            input.sendKeys(Keys.BACK_SPACE);
            input.sendKeys(value);
        } else {
            // Если lookup без ввода → нажимаем стрелку
            SelenideElement arrow = fieldContainer.$x(".//*[contains(@class,'arrow') or contains(@class,'icon')]")
                    .shouldBe(visible);

            retryClick(arrow, "Открыть lookup '" + label + "'");
        }

        // Ищем правильный listview с таким же marker
        SelenideElement listView = $$x("//div[contains(@class,'listview') and @data-item-marker='" + marker + "']")
                .findBy(visible);

        // Ищем пункт по normalize-space
        SelenideElement item = listView
                .$x(".//li[normalize-space()='" + value + "']")
                .shouldBe(visible);

        jsClick(item);

        // Ждём исчезновения списка
        listView.should(disappear);

        return this;
    }

    protected SelenideElement findFieldContainer(String label) {
        return $x("//label[normalize-space()='" + label + "']/ancestor::div[@data-item-marker]");
    }


    // ============================================================
    // 3) Проверка значения (universal getter)
    // ============================================================

    @Step("Проверить, что поле '{label}' имеет значение '{expected}'")
    public FieldComponent shouldHaveValue(String label, String expected) {
        findField(label).shouldHave(exactValue(expected));
        return this;
    }

    @Step("Получить значение поля '{label}'")
    public String getValue(String label) {
        SelenideElement field = findField(label)
                .shouldBe(visible)
                .scrollIntoView(true);

        // Creatio: input.value or textarea.value
        return field.getValue();
    }

//Работает из старого проекта //19.12.2025

    public FieldComponent setFieldByValue(String name, String value, boolean isPlaceholder, boolean isTextarea) {
        if (isPlaceholder && isTextarea) {
            $x("//textarea[@placeholder='" + name + "']").setValue(value);
        } else if (isPlaceholder) {
            $x("//input[@placeholder='" + name + "']").setValue(value);
        } else if (isTextarea) {
            $x("//textarea[@placeholder='" + name + "']").setValue(value);
        } else {
            $x("//label[.='" + name + "']/../..//input").setValue(value);
        }

        return this;
    }

    public FieldComponent validateFieldValue(String label, String expectedValue) {
        findFieldByLabel(label)
                .shouldHave(Condition.value(expectedValue));
        return this;
    }

    public static SelenideElement findFieldByLabel(String label) {
        String safeLabel = label.replace("'", "\\'");
        String xpath = String.format(
                "//label[normalize-space()='%1$s']/following::input[1] | " +
                        "//label[contains(normalize-space(.),'%1$s')]/following::input[1] | " +
                        "//label[normalize-space()='%1$s']/following::textarea[1] | " +
                        "//label[contains(normalize-space(.),'%1$s')]/following::textarea[1] | " +
                        "//label[normalize-space()='%1$s']/following::select[1] | " +
                        "//label[contains(normalize-space(.),'%1$s')]/following::select[1] | " +
                        "//*[@placeholder='%1$s'] | //*[@aria-label='%1$s'] | //*[@name='%1$s'] | //*[@data-test-id='%1$s']",
                safeLabel
        );

        return com.codeborne.selenide.Selenide.$x(xpath);
    }




}
