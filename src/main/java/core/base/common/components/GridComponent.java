package core.base.common.components;


import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * Компонент для стабильной работы с таблицами (Grid) Creatio.
 * Поддерживает клик, двойной клик, выбор строки, поиск строки.
 */
public class GridComponent extends Components {

    /**
     * Находим грид по data-item-marker (основной способ в Creatio)
     */
    private SelenideElement getGrid(String gridMarker) {
        return $x("//*[@data-item-marker='" + gridMarker + "']")
                .shouldBe(visible);
    }

    /**
     * Возвращает коллекцию строк грида
     */
    private ElementsCollection getRows(SelenideElement grid) {
        return grid.$$x(".//div[contains(@class,'grid-row')]");
    }

    /**
     * Находит строку по тексту (любому полю)
     */
    private SelenideElement findRowByText(SelenideElement grid, String text) {
        return grid.$$x(".//div[contains(@class,'grid-row')]")
                .findBy(text(text))
                .shouldBe(visible);
    }

    /**
     * Находит первую строку грида
     */
    private SelenideElement firstRow(SelenideElement grid) {
        return grid.$$x(".//div[contains(@class,'grid-row')]")
                .first()
                .shouldBe(visible);
    }

    /**
     * Стабильный клик по строке (обязательно scroll + check selection)
     */
    @Step("Выбрать строку: '{text}' в гриде '{gridMarker}'")
    public GridComponent selectRow(String gridMarker, String text) {

        SelenideElement grid = getGrid(gridMarker);
        SelenideElement row = findRowByText(grid, text);

        retryClick(row, "Выбор строки '" + text + "'");

        // Проверяем, что строка выделена
        row.shouldHave(cssClass("grid-row-selected"));

        return this;
    }

    /**
     * Стабильный выбор первой строки грида — часто используется в Creatio
     */
    @Step("Выбрать первую строку в гриде '{gridMarker}'")
    public GridComponent selectFirstRow(String gridMarker) {

        SelenideElement grid = getGrid(gridMarker);
        SelenideElement row = firstRow(grid);

        retryClick(row, "Выбор первой строки");

        row.shouldHave(cssClass("grid-row-selected"));

        return this;
    }

    /**
     * Стабильный double-click по строке
     */
    @Step("Двойной клик по строке: '{text}' в гриде '{gridMarker}'")
    public GridComponent doubleClickRow(String gridMarker, String text) {

        SelenideElement grid = getGrid(gridMarker);
        SelenideElement row = findRowByText(grid, text)
                .scrollIntoView(true)
                .shouldBe(enabled);

        Actions actions = new Actions(WebDriverRunner.getWebDriver());

        actions.doubleClick(row).perform();

        return this;
    }

    /**
     * double-click по первой строке грида
     */
    @Step("Двойной клик по первой строке грида '{gridMarker}'")
    public GridComponent doubleClickFirstRow(String gridMarker) {

        SelenideElement grid = getGrid(gridMarker);
        SelenideElement row = firstRow(grid)
                .scrollIntoView(true);

        Actions actions = new Actions(WebDriverRunner.getWebDriver());
        actions.doubleClick(row).perform();

        return this;
    }

    /**
     * Ожидание появления нужного количества строк
     */
    @Step("Ожидать минимум {minCount} строк в гриде '{gridMarker}'")
    public GridComponent waitForRows(String gridMarker, int minCount) {

        SelenideElement grid = getGrid(gridMarker);

        grid.$$x(".//div[contains(@class,'grid-row')]")
                .shouldHave(CollectionCondition.sizeGreaterThanOrEqual(minCount));

        return this;
    }

    /**
     * Проверяет, что грид содержит строку с текстом
     */
    @Step("Проверить, что грид '{gridMarker}' содержит строку '{text}'")
    public GridComponent shouldContain(String gridMarker, String text) {

        SelenideElement grid = getGrid(gridMarker);

        grid.$$x(".//div[contains(@class,'grid-row')]")
                .findBy(text(text))
                .shouldBe(visible);

        return this;
    }

    // =======================================
// 🔥 Выбрать строку по индексу
// =======================================
    @Step("Выбрать строку с индексом {index} в гриде '{gridMarker}'")
    public GridComponent selectRowByIndex(String gridMarker, int index) {
        SelenideElement grid = getGrid(gridMarker);

        SelenideElement row = grid.$$x(".//div[contains(@class,'grid-row')]")
                .get(index)
                .shouldBe(visible, enabled);

        retryClick(row, "Выбор строки по индексу " + index);

        return this;
    }

    // =======================================
// 🔥 Ожидать строку, содержащую текст
// =======================================
    @Step("Ожидать строку содержащую текст '{text}' в гриде '{gridMarker}'")
    public GridComponent waitRowContains(String gridMarker, String text) {

        getGrid(gridMarker)
                .$x(".//div[contains(@class,'grid-row')]//*[contains(text(),'" + text + "')]")
                .shouldBe(visible);

        return this;
    }

    // =======================================
// 🔥 Супер-стабильный double-click через JS
// =======================================
    @Step("Принудительный двойной клик по строке '{text}' в гриде '{gridMarker}'")
    public GridComponent forceDoubleClick(String gridMarker, String text) {

        SelenideElement grid = getGrid(gridMarker);

        SelenideElement row = grid.$$x(".//div[contains(@class,'grid-row')]")
                .findBy(text(text))
                .scrollIntoView(true)
                .shouldBe(enabled);

        try {
            row.doubleClick();
        } catch (Exception e) {
            Selenide.executeJavaScript(
                    "arguments[0].dispatchEvent(new MouseEvent('dblclick', {bubbles:true}))",
                    row
            );
        }

        return this;
    }

    // =======================================
// 🔥 Выбор строки с contains()
// =======================================
    @Step("Выбрать строку, содержащую '{text}', в гриде '{gridMarker}'")
    public GridComponent selectRowContains(String gridMarker, String text) {

        SelenideElement grid = getGrid(gridMarker);

        SelenideElement row =
                grid.$$x(".//div[contains(@class,'grid-row')]//*[contains(text(),'" + text + "')]")
                        .first()
                        .shouldBe(visible)
                        .scrollIntoView(true);

        retryClick(row, "Выбор строки с contains: " + text);

        return this;
    }

    @Step("Двойной клик по строке содержащей текст '{text}' в гриде '{gridMarker}'")
    public GridComponent doubleClickRowByText(String gridMarker, String text) {

        SelenideElement grid = getGrid(gridMarker);

        // Ищем строку, содержащую текст
        SelenideElement row = grid
                .$x(".//div[contains(@class,'grid-row')]//*[contains(text(),'" + text + "')]")
                .shouldBe(visible)
                .scrollIntoView(true)
                .closest(".grid-row");

        try {
            row.doubleClick();
        } catch (Exception e) {
            // fallback — Creatio иногда не реагирует
            Selenide.executeJavaScript(
                    "arguments[0].dispatchEvent(new MouseEvent('dblclick', {bubbles:true}))",
                    row
            );
        }

        return this;
    }

    /**
     * Двойной клик по строке содержащей текст '{text}' во всех видимых гридах (без указания gridMarker).
     * Универсальный метод — полезен если gridMarker неизвестен или динамичен.
     */
    @Step("Двойной клик по строке содержащей текст '{text}' (поиск по всем grid-ам)")
    public GridComponent doubleClickRowByText(String text) {

        // 1) Ищем первую видимую строку grid'а, содержащую текст
        SelenideElement row = $x("//div[contains(@class,'grid-row')][.//*[contains(normalize-space(.), '" + text + "')]]")
                .shouldBe(Condition.visible)
                .scrollIntoView(true);

        // 2) Стабильный doubleClick через Actions, с fallback JS
        try {
            Actions actions = new Actions(WebDriverRunner.getWebDriver());
            actions.doubleClick(row).perform();
        } catch (Throwable e) {
            // fallback — сгенерировать событие double click через JS
            Selenide.executeJavaScript(
                    "arguments[0].dispatchEvent(new MouseEvent('dblclick', {bubbles:true, cancelable:true}));",
                    row
            );
        }

        return this;
    }



}
