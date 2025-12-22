package core.base.common.components;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.timeout;
import static com.codeborne.selenide.Selenide.*;
import static com.google.common.net.HostSpecifier.isValid;


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

    private SelenideElement findFieldContainer(String label) {
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

    private SelenideElement findFieldByLabel(String label) {
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


    public FieldComponent setHandBookFieldByValueCheck(String nameField, String value) {

        setFieldByValueCheck(nameField, value);

        SelenideElement item = $x("//div[contains(@class,'listview')]//li[normalize-space(.)='" + value + "']")
                .shouldBe(Condition.visible);

        item.click();

        $x("//label[normalize-space(.)='" + nameField + "']/../..//input")
                .shouldHave(Condition.exactValue(value));

        return this;
    }

    public FieldComponent setFieldByValueCheck(String nameField, String value) {

        SelenideElement input = $x("//label[.='" + nameField + "']/../..//input");

        input.setValue(value)
                .shouldHave(Condition.value(value));

        return this;
    }


    @Step("Получить непустое значение поля '{fieldName}'")
    public String getNonEmptyValue(String fieldName) {
        SelenideElement field = findFieldByLabel(fieldName)
                .shouldBe(Condition.visible);

        long end = System.currentTimeMillis() + timeout;
        String current = null;

        while (System.currentTimeMillis() < end) {
            try {
                current = field.getAttribute("value");
                if (isValid(current)) return current;

                try {
                    current = field.val();
                    if (isValid(current)) return current;
                } catch (Throwable ignored) {}

                current = field.getAttribute("data-value");
                if (isValid(current)) return current;

                current = field.getText();
                if (isValid(current)) return current;

                SelenideElement inner = field.closest("div")
                        .$x(".//input[not(@type='hidden')]");
                if (inner.exists()) {
                    current = inner.getAttribute("value");
                    if (isValid(current)) return current;

                    try {
                        current = inner.val();
                        if (isValid(current)) return current;
                    } catch (Throwable ignored) {}
                }
            } catch (Throwable ignored) {}

            Selenide.sleep(150);
        }

        throw new AssertionError(
                "Поле '" + fieldName + "' пустое. Значение не удалось получить."
        );
    }


    @Step("Устанавливаем дату в поле '{fieldMarker}' значением '{value}'")
    public FieldComponent setDateFieldByMarker(String fieldMarker, String value) {
        Logger log = LoggerFactory.getLogger(FieldComponent.class); // если у тебя уже есть log — удали эту строку

        log.info("➡ Начало: установка даты. Маркер='{}', значение='{}'", fieldMarker, value);
        Allure.step("Поиск контейнера по маркеру: " + fieldMarker);

        try {
            // Находим контейнер date-edit по data-item-marker
            SelenideElement container = $x("//*[@data-item-marker='" + fieldMarker + "']")
                    .shouldBe(Condition.visible, Duration.ofSeconds(10));
            Allure.step("Контейнер найден");

            // Внутри находим input и ждём, что он включён/готов
            SelenideElement input = container.$x(".//input")
                    .shouldBe(Condition.enabled, Duration.ofSeconds(10));
            Allure.step("Поле ввода внутри контейнера готово");

            log.info("➡ Активируем поле (клик) и очищаем");
            input.click();
            // более надёжная очистка: CTRL+A + DEL (если clear() нестабилен)
            input.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"));
            input.sendKeys(org.openqa.selenium.Keys.DELETE);
            Allure.step("Поле очищено");

            log.info("➡ Ввод значения '{}' в поле '{}'", value, fieldMarker);
            input.setValue(value);

            log.info("➡ Ожидание применения значения");
            input.shouldHave(Condition.value(value), Duration.ofSeconds(8));
            Allure.step("Значение установлено: " + value);

            log.info("✔ Дата успешно установлена в поле '{}'", fieldMarker);
            return this;

        } catch (Throwable t) {
            log.error("❌ Ошибка при установке даты в поле '{}'. Причина: {}", fieldMarker, t.toString());
            Allure.step("Ошибка при установке даты: " + t.getMessage());
            // Неявно: если у тебя есть attachScreenshot()/attachPageSource() — можно вызвать их здесь.
            throw t;
        }
    }


    @Step("Получить значение поля '{fieldName}' любым возможным способом")
    public String getFieldValueSmart(String fieldName) {

        SelenideElement field = findFieldByLabel(fieldName)
                .shouldBe(Condition.visible);

        long end = System.currentTimeMillis() + timeout;
        String current = null;

        while (System.currentTimeMillis() < end) {

            try {
                // 1️⃣ Стандартный value=""
                current = field.getAttribute("value");
                if (isValid(current)) return current;

                // 2️⃣ val() (selenide)
                try {
                    current = field.val();
                    if (isValid(current)) return current;
                } catch (Throwable ignored) {}

                // 3️⃣ Lookup, dropdown: aria-label
                current = field.getAttribute("aria-label");
                if (isValid(current)) return current;

                // 4️⃣ Display value (очень частый вариант в Creatio)
                current = field.getAttribute("data-display-value");
                if (isValid(current)) return current;

                // 5️⃣ Lookup: title (отображаемое значение)
                current = field.getAttribute("title");
                if (isValid(current)) return current;

                // 6️⃣ Вложенный lookup текст
                SelenideElement lookupSpan = field.closest("div")
                        .$x(".//span[contains(@class,'lookup-edit')]");

                if (lookupSpan.exists()) {
                    current = lookupSpan.getText();
                    if (isValid(current)) return current;
                }

                // 7️⃣ Любой вложенный input (даты, маски, lookup)
                SelenideElement innerInput = field.closest("div")
                        .$x(".//input[not(@type='hidden')]");

                if (innerInput.exists()) {
                    current = innerInput.getAttribute("value");
                    if (isValid(current)) return current;

                    try {
                        current = innerInput.val();
                        if (isValid(current)) return current;
                    } catch (Throwable ignored) {}

                    current = innerInput.getAttribute("data-display-value");
                    if (isValid(current)) return current;
                }

                // 8️⃣ Поле может быть textarea
                SelenideElement textarea = field.closest("div")
                        .$x(".//textarea");

                if (textarea.exists()) {
                    current = textarea.val();
                    if (isValid(current)) return current;

                    current = textarea.getText();
                    if (isValid(current)) return current;
                }

                // 9️⃣ Иногда Creatio пишет текст просто внутрь div/span
                current = field.getText();
                if (isValid(current)) return current;

            } catch (Throwable ignored) {}

            Selenide.sleep(150);
        }

        throw new AssertionError("Поле '" + fieldName + "' не содержит значения после ожидания.");
    }


    // Работает !!!! 06.12.2025
    @Step("Заполнить поле 'Тип получения кредита' значением '{value}' (без скролла страницы)")
    public FieldComponent fillLoadCreditTypeSafely(String value) {

        // 1️⃣ Находим input БЕЗ scrollIntoView
        SelenideElement input = $x("//label[normalize-space()='Тип получения кредита']/../..//input[@type='text']")
                .shouldBe(Condition.visible);

        // 2️⃣ Фокус через JS (НЕ скроллит страницу)
        executeJavaScript("arguments[0].focus();", input);

        // 3️⃣ Принудительно заполняем input через JS
        executeJavaScript(
                "arguments[0].value='';" +
                        "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));" +
                        "arguments[0].value=arguments[1];" +
                        "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));",
                input, value
        );

        // 4️⃣ Даём шанс Creatio открыть listview
        sleep(250);

        ElementsCollection lists = $$x("//div[contains(@class,'listview')]")
                .filter(Condition.visible);

        if (!lists.isEmpty()) {

            SelenideElement list = lists.first();

            SelenideElement item = list.$x(".//li[contains(normalize-space(.), '" + value + "')]")
                    .should(Condition.exist);

            // 5️⃣ Выбираем через JS без скролла
            executeJavaScript(
                    "arguments[0].dispatchEvent(new MouseEvent('mousedown',{bubbles:true}));" +
                            "arguments[0].dispatchEvent(new MouseEvent('mouseup',{bubbles:true}));" +
                            "arguments[0].click();",
                    item
            );

            list.should(Condition.disappear);

        } else {
            // 6️⃣ Если списка нет — просто blur
            executeJavaScript("arguments[0].blur();", input);
        }

        // 7️⃣ Проверка
        input.shouldHave(Condition.value(value));

        // 8️⃣ 🔥 ВОЗВРАЩАЕМ СТРАНИЦУ ВВЕРХ, чтобы последующие кнопки были кликабельны
        executeJavaScript("window.scrollTo(0, 0);");

        return this;
    }









}
