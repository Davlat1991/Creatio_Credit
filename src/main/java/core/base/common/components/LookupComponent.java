package core.base.common.components;

import com.codeborne.selenide.*;
import core.pages.credit.ContractCreditApplicationPage;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


/**
 * Универсальный компонент справочника Creatio (Lookup).
 * Работает стабильно с динамическим DOM, hidden listview, JS-перекрытиями.
 */
public class LookupComponent extends Components {

    public static final Logger log =
            LoggerFactory.getLogger(LookupComponent.class);
    private String savedValue;


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


    @Step("Выбрать значение '{value}' в выпадающем поле '{marker}'")
    public LookupComponent selectDropdownValueWithCheck(String marker, String value) {

        SelenideElement input = $x("//*[@data-item-marker='" + marker + "']//input");

        for (int attempt = 1; attempt <= 5; attempt++) {
            try {
                log.warn(
                        "Попытка №%s: выбор значения '%s' в поле '%s'",
                        attempt, value, marker
                );

                // 1️⃣ Кликаем по полю
                input.scrollIntoView(true)
                        .shouldBe(visible, enabled)
                        .click();

                // 2️⃣ ЖДЁМ появления списка
                SelenideElement dropdownContainer =
                        $x("//div[contains(@class,'listview-scroll')]")
                                .shouldBe(visible);

                // 3️⃣ ИЩЕМ ПУНКТ ПО ТЕКСТУ ИЛИ MARKER
                SelenideElement option = dropdownContainer
                        .$x(".//li[normalize-space(.)='" + value + "' or @data-item-marker='" + value + "']")
                        .shouldBe(visible);

                // 4️⃣ КЛИК ПО ЗНАЧЕНИЮ
                option.scrollIntoView(true).click();

                // 5️⃣ ПРОВЕРКА, ЧТО ЗНАЧЕНИЕ УСТАНОВИЛОСЬ
                input.shouldHave(Condition.value(value));

                log.info(String.format(
                        "Значение '%s' успешно выбрано в поле '%s'",
                        value, marker
                ));

                return this;

            } catch (Exception e) {

                log.warn("Ошибка на попытке " + attempt + ": " + e.getMessage());

                if (attempt == 5) {
                    throw new AssertionError(
                            "Не удалось выбрать значение '" + value +
                                    "' в поле '" + marker + "' за 5 попыток", e);
                }
            }
        }

        return this;
    }


    public LookupComponent clickSearchIconID(String lookupID) {

        // 1: Находим wrapper (куда нужно наводить мышку, чтобы лупа показалась)
        SelenideElement wrap = $x("//div[@id='" + lookupID + "-wrap']")
                .shouldBe(visible);

        wrap.hover(); // Обязательно!

        // 2: Находим правую иконку — ЭТО ЛУПА
        SelenideElement searchIcon = $x("//div[@id='" + lookupID + "-right-icon']")
                .shouldBe(visible)
                .shouldBe(enabled);

        // 3: Делаем JS-клик, потому что обычный click() может не работать
        executeJavaScript("arguments[0].click();", searchIcon);

        return this;
    }


    //Работает !!! Всталвяет сохраненное значение Сберегательного счёта
    public LookupComponent selectValueInLookupWork(String marker) {

        if (this.savedValue == null) {
            throw new IllegalStateException("❌ Нет сохранённого значения для вставки!");
        }

        // 1) Ищем wrapper lookup по data-item-marker
        SelenideElement wrapper = $x("//*[@data-item-marker='" + marker + "']")
                .shouldBe(visible)
                .shouldBe(enabled);

        // 2) Ищем input внутри wrapper
        SelenideElement input = wrapper.$("input")
                .shouldBe(visible)
                .shouldBe(enabled);

        // 3) Вставляем значение
        input.click();
        input.setValue(this.savedValue);

        // 4) Проверяем, что значение реально вставлено
        input.shouldHave(value(this.savedValue));

        // 5) RETRY ПОИСКА результата (до 10 попыток)
        SelenideElement itemRow = null;

        for (int i = 0; i < 10; i++) {
            try {
                // Нажимаем кнопку "Поиск"
                $x("//*[@data-tag='SearchButton']")
                        .shouldBe(visible)
                        .shouldBe(enabled)
                        .click();

                // Ищем строку результата по data-item-marker (это самый точный локатор!)
                itemRow = $x("//div[contains(@class,'grid-listed-row') and @data-item-marker='"
                        + this.savedValue + "']")
                        .shouldBe(visible, Duration.ofSeconds(1));

                break; // найдено → выходим

            } catch (Throwable ignored) {
                System.out.println("⏳ Ждём, пока появится счёт или номер..." + (i+1) + "/10");
                Selenide.sleep(1000);
            }
        }

        // Если после 10 попыток строка так и не появилась
        if (itemRow == null) {
            throw new AssertionError("❌ Счёт '" + this.savedValue + "' не найден в lookup после 10 попыток!");
        }

        // 6) Клик по найденной строке
        itemRow.click();

        // 7) Проверяем, что строка выделена
        // ВЫДЕЛЕННАЯ строка имеет класс grid-row-selected — мы нашли это по твоему DOM!
        itemRow.shouldHave(cssClass("grid-row-selected"));

        // 8) Нажимаем кнопку "Выбрать"
        $x("//*[@data-tag='SelectButton']")
                .shouldBe(visible)
                .shouldBe(enabled)
                .click();

        System.out.println("✅ Значение успешно выбрано в lookup: " + this.savedValue);

        return this;
    }

    public LookupComponent setHandBookFieldByValueCheck(String nameField, String value) {

        setFieldByValueCheck(nameField, value);

        SelenideElement item = $x("//div[contains(@class,'listview')]//li[normalize-space(.)='" + value + "']")
                .shouldBe(Condition.visible);

        item.click();

        $x("//label[normalize-space(.)='" + nameField + "']/../..//input")
                .shouldHave(Condition.exactValue(value));

        return this;
    }

    public LookupComponent setFieldByValueCheck(String nameField, String value) {

        SelenideElement input = $x("//label[.='" + nameField + "']/../..//input");

        input.setValue(value)
                .shouldHave(Condition.value(value));

        return this;
    }


    //Работает 06.12.2025
    @Step("Ввести и выбрать значение '{value}' в поле по DIM '{name}'")
    public LookupComponent setFieldScheduleDetailByDIMCheck(String name, String value) {

        // 1️⃣ Находим input
        SelenideElement input = $x("//div[@data-item-marker='" + name + "']/input")
                .shouldBe(visible, enabled)
                .scrollIntoView(true);

        // 2️⃣ Вводим текст
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.BACK_SPACE);
        input.sendKeys(value);

        // 3️⃣ Ждём появления видимого listview
        SelenideElement listView = $$x("//div[contains(@class,'listview')]")
                .findBy(visible)
                .shouldBe(visible);

        // 4️⃣ Ищем LI по data-item-marker или тексту
        SelenideElement item = listView.$x(".//li[@data-item-marker='" + value + "' or normalize-space(.)='" + value + "']")
                .shouldBe(visible, enabled);

        // 5️⃣ Делаем стабильный JS-клик (для Creatio критично)
        Selenide.executeJavaScript("arguments[0].click();", item);

        // 6️⃣ Ждём, что список закроется = выбор зафиксирован
        listView.should(disappear);

        // 7️⃣ Проверяем, что поле теперь имеет выбранное значение
        input.shouldHave(Condition.exactValue(value));

        return this;
    }

    //Работает 06.12.2025
    @Step("Ввести и выбрать значение '{value}' в поле по DIM '{name}'")
    public LookupComponent setFieldScheduleDetailByDIMNewCheck(String name, String value) {

        // 1️⃣ Находим input
        SelenideElement input = $x("//div[@data-item-marker='" + name + "']/input")
                .shouldBe(visible, enabled)
                .scrollIntoView(true);

        // 2️⃣ Вводим текст
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.BACK_SPACE);
        input.sendKeys(value);

        // 3️⃣ Ждём появления видимого listview
        SelenideElement listView = $$x("//div[contains(@class,'listview')]")
                .findBy(visible)
                .shouldBe(visible);

        // 4️⃣ Ищем LI по data-item-marker или тексту
        SelenideElement item = listView.$x(".//li[@data-item-marker='" + value + "' or normalize-space(.)='" + value + "']")
                .shouldBe(visible, enabled);

        // 5️⃣ Делаем стабильный JS-клик (для Creatio критично)
        Selenide.executeJavaScript("arguments[0].click();", item);

        // 6️⃣ Ждём, что список закроется = выбор зафиксирован
        listView.should(disappear);

        // 7️⃣ Проверяем, что поле теперь имеет выбранное значение
        input.shouldHave(Condition.exactValue(value));

        return this;
    }

}

