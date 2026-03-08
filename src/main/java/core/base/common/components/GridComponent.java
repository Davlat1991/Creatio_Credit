package core.base.common.components;


import com.codeborne.selenide.*;
import core.pages.contacts.ContactCardPage;
import core.pages.credit.ContractCreditApplicationPage;
import io.qameta.allure.Step;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

/**
 * Компонент для стабильной работы с таблицами (Grid) Creatio.
 * Поддерживает клик, двойной клик, выбор строки, поиск строки.
 */
public class GridComponent extends Components {

    public static final Logger log =
            LoggerFactory.getLogger(GridComponent.class);

    /**
     * Находим грид по data-item-marker (основной способ в Creatio)
     */
    public SelenideElement getGrid(String gridMarker) {
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

    public SelenideElement findRowByText(String text) {

        SelenideElement grid = getActiveGrid();

        return findRowByText(grid, text);
    }

    private SelenideElement getActiveGrid() {

        return $("[data-item-marker='Grid']")
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

    //Imigration


    public GridComponent DoubleclickByDIM(String value) {

        SelenideElement element =
                $x("//div[@data-item-marker='" + value + "' and contains(@class, 'grid-listed-row')]");

        Actions actions = new Actions(getWebDriver());
        actions.doubleClick(element).perform();

        return this;
    }






    //Новый метод 06.12.2025

    @Step("Клик по первой строке грида '{gridWrapId}' и ожидание кнопки '{buttonText}'")
    public GridComponent clickFirstRowInGridAndWaitButton(String gridWrapId, String buttonText) {

        for (int attempt = 1; attempt <= 5; attempt++) {
            try {
                log.info("Попытка №" + attempt +
                        ": клик по первой строке грида '" + gridWrapId +
                        "' и ожидание кнопки '" + buttonText + "'");

                // 1. Находим контейнер грида
                SelenideElement gridWrap = $x("//div[@id='" + gridWrapId + "']")
                        .shouldBe(visible)
                        .scrollIntoView(true);

                // 2. Ищем ПЕРВУЮ строку, которая не является header
                SelenideElement row = gridWrap.$$x(".//div[contains(@class,'grid-row') and not(contains(@class,'grid-header'))]")
                        .filter(visible)
                        .first()
                        .shouldBe(visible, enabled);

                // 3. Выполняем клик через JS (самый стабильный в Creatio)
                executeJavaScript("arguments[0].click();", row);

                // 4. Ждём кнопку
                $x("//span[normalize-space()='" + buttonText + "']")
                        .shouldBe(visible, Duration.ofSeconds(5))
                        .shouldBe(enabled);

                log.info("Кнопка '" + buttonText + "' появилась");
                return this;

            } catch (Exception e) {
                log.info("Попытка №" + attempt + " не удалась, повторяем...");

                if (attempt == 5) {
                    throw new AssertionError(
                            "После клика по строке грида '" + gridWrapId +
                                    "' кнопка '" + buttonText + "' не появилась", e
                    );
                }
            }
        }
        return this;
    }

// Нужно протеститровать метод + waitForButton
    @Step("Клик по первой строке грида '{gridWrapId}'")
    public GridComponent clickFirstRow(String gridWrapId) {

        SelenideElement gridWrap = $x("//div[@id='" + gridWrapId + "']")
                .shouldBe(visible)
                .scrollIntoView(true);

        SelenideElement row = gridWrap
                .$$x(".//div[contains(@class,'grid-row') and not(contains(@class,'grid-header'))]")
                .filter(visible)
                .first()
                .shouldBe(visible, enabled);

        // Самый стабильный клик для Creatio
        executeJavaScript("arguments[0].click();", row);

        return this;
    }

    public GridComponent openDetailRow(String rowName) {
        $x("//div[contains(@class,'grid')]//div[text()='" + rowName + "']")
                .shouldBe(visible)
                .click();
        return this;
    }





}
