package core.base.common.components;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.$$x;

/**
 * Универсальный компонент справочника Creatio (Lookup).
 * Работает стабильно с динамическим DOM, hidden listview, JS-перекрытиями.
 */
public class LookupComponent extends Components {

    /**
     * Возвращает контейнер поля по его label.
     */
    private SelenideElement fieldContainer(String label) {
        return $x("//label[normalize-space(text())='" + label + "']/../..")
                .shouldBe(visible);
    }

    /**
     * Определяет input внутри lookup.
     */
    private SelenideElement getInput(SelenideElement container) {
        ElementsCollection inputs = container.$$x(".//input[not(@type='hidden')]");
        return inputs.isEmpty() ? null : inputs.first();
    }

    /**
     * Открывает список lookup:
     * - или вводом в input
     * - или кликом по стрелке
     */
    private void activateLookup(SelenideElement container, String value) {

        SelenideElement input = getInput(container);

        if (input != null) {
            // lookup с полем ввода
            input.shouldBe(visible, enabled);
            input.click();
            input.sendKeys(Keys.CONTROL + "a");
            input.sendKeys(Keys.BACK_SPACE);
            input.sendKeys(value);
        } else {
            // lookup без input — открываем стрелку
            SelenideElement arrow = container.$x(".//*[contains(@class,'arrow') or contains(@class,'icon')]");
            click(arrow, "Клик по стрелке lookup");
        }
    }

    /**
     * Собирает правильный visible listview (а не hidden!).
     */
    private SelenideElement waitListViewVisible() {
        return $$x("//div[contains(@class,'listview')]")
                .filter(visible)
                .first()
                .shouldBe(visible);
    }

    /**
     * Выбирает значение из lookup по тексту.
     */
    @Step("Выбрать значение '{value}' в справочнике '{label}'")
    public LookupComponent select(String label, String value) {

        SelenideElement container = fieldContainer(label);

        // Активируем lookup
        activateLookup(container, value);

        // Ждём видимый список
        SelenideElement listView = waitListViewVisible();

        // Находим пункт
        SelenideElement item = listView
                .$x(".//li[normalize-space(text())='" + value + "']")
                .shouldBe(visible);

        // Надёжный выбор через JS + mouse events
        jsClick(item);

        // Ждём исчезновения списка — обязательный шаг
        listView.should(disappear);

        // Проверка выбранного значения
        SelenideElement input = getInput(container);
        if (input != null) {
            input.shouldHave(exactValue(value));
        }

        return this;
    }

    /**
     * Принудительно выбирает значение даже если listview скрыт.
     * Использует data-item-marker и ручную установку value.
     */
    @Step("Принудительно выбрать значение '{value}' в справочнике '{label}' (force mode)")
    public LookupComponent forceSelect(String label, String value) {

        SelenideElement container = fieldContainer(label);
        SelenideElement input = getInput(container);

        if (input == null)
            throw new RuntimeException("У поля '" + label + "' нет input — forceSelect невозможно!");

        // marker lookup-контейнера
        String marker = container.getAttribute("data-item-marker");

        SelenideElement listView = $x("//div[@data-item-marker='" + marker + "' and contains(@class,'listview')]");

        SelenideElement item = listView
                .$x(".//li[@data-item-marker='" + value + "']")
                .should(exist);

        // Принудительная установка
        Selenide.executeJavaScript(
                "arguments[0].value = arguments[1];" +
                        "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));" +
                        "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                input, value
        );

        input.sendKeys(Keys.TAB);

        input.shouldHave(exactValue(value));

        return this;
    }

    public void selectValue(String marker, String value) {

        // 1️⃣ Поле lookup
        SelenideElement input = $x("//*[@data-item-marker='" + marker + "']//input")
                .shouldBe(Condition.visible, Condition.enabled);

        input.click();
        input.clear();
        input.setValue(value);

        // 2️⃣ Ждём появления listview
        SelenideElement item = $x("//li[@data-item-marker='" + value + "' or normalize-space(text())='" + value + "']")
                .shouldBe(Condition.visible);

        // 3️⃣ JS click — самый стабильный
        item.click();

        // 4️⃣ Проверяем выбранное значение
        input.shouldHave(Condition.value(value));
    }

    public LookupComponent selectLookup(String label, String value) {

        // Клик в lookup-поле
        $x("//label[normalize-space()='" + label + "']/../..//input")
                .shouldBe(Condition.visible)
                .click();

        // Ввод текста для поиска
        $x("//label[normalize-space()='" + label + "']/../..//input")
                .setValue(value);

        // Выбор из listview
        $x("//div[contains(@class,'listview')]//li[normalize-space()='" + value + "']")
                .shouldBe(Condition.visible)
                .click();

        return this;
    }

}

