package core.base.common.components;


import com.codeborne.selenide.*;
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

    //Imigration



    public GridComponent DoubleclickByDIM(String value) {

        SelenideElement element =
                $x("//div[@data-item-marker='" + value + "' and contains(@class, 'grid-listed-row')]");

        Actions actions = new Actions(getWebDriver());
        actions.doubleClick(element).perform();

        return this;
    }


    @Step("Ожидаем появление значения '{value}' в колонке '{columnName}'")
    public void waitForCreditDecision(String columnName, String value) {

        System.out.println("➡ Ждём, когда в колонке '" + columnName + "' появится значение '" + value + "'");

        long timeoutMs = Duration.ofSeconds(60).toMillis();
        long start = System.currentTimeMillis();

        // Xpath колонки: ищем div где label имеет title=columnName
        String headerXpath = "//label[@title='" + columnName + "']";

        // Проверяем, что колонка вообще существует (один раз)
        $x(headerXpath).shouldBe(Condition.visible, Duration.ofSeconds(10));

        // Ищем строки грида
        String rowXpath = "//div[contains(@class,'grid-listed-row') or contains(@class,'grid-row')]";

        while (System.currentTimeMillis() - start < timeoutMs) {

            ElementsCollection rows = $$x(rowXpath);

            for (SelenideElement row : rows) {
                try {
                    // Ищем значение внутри строки
                    SelenideElement cell = row.$x(".//span[@grid-data-type='text' and normalize-space()='" + value + "']");
                    if (cell.exists() && cell.isDisplayed()) {
                        System.out.println("✔ Значение найдено в строке: " + value);
                        return;
                    }

                } catch (Exception ignored) {}
            }

            System.out.println("⏳ Значение '" + value + "' пока не найдено — ждём...");
            Selenide.sleep(500);
        }

        throw new AssertionError("❌ Значение '" + value + "' в колонке '" + columnName + "' так и не появилось!");
    }


    @Step("Ожидаем появление значения '{value}' в колонке '{columnName}'")
    public void waitForValueInGridColumn(String columnName, String value) {
        System.out.println("➡ Ждём значение '" + value + "' в колонке '" + columnName + "'");

        // Ждём, что хотя бы один блок заголовков появится (защита от пустой страницы)
        $$x("//div[contains(@class,'grid-captions')]//label")
                .shouldBe(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(10));

        // Повторяем попытки поиска в пределах таймаута (будет учитывать перерисовку грида)
        long timeoutMs = Duration.ofSeconds(60).toMillis();
        long start = System.currentTimeMillis();

        while (System.currentTimeMillis() - start < timeoutMs) {
            try {
                // 1) Получаем заголовки заново (каждый цикл — свежие элементы)
                ElementsCollection headers = $$x("//div[contains(@class,'grid-captions')]//label");

                int columnIndex = -1;
                for (int i = 0; i < headers.size(); i++) {
                    String h = headers.get(i).getText().trim();
                    System.out.println("→ Заголовок найден: '" + h + "' (index " + i + ")");
                    if (h.equalsIgnoreCase(columnName)) {
                        columnIndex = i + 1; // XPath индекс 1-based
                        break;
                    }
                }

                if (columnIndex == -1) {
                    System.out.println("⚠ Колонка '" + columnName + "' не найдена пока — повторяем...");
                    Thread.sleep(500);
                    continue;
                }

                System.out.println("✔ Колонка найдена. Индекс = " + columnIndex);

                // 2) Берём строки заново
                ElementsCollection rows = $$x("//div[contains(@class,'grid-listed-row') or contains(@class,'grid-row')]");

                if (rows.isEmpty()) {
                    System.out.println("⚠ Пока нет строк — ждём...");
                    Thread.sleep(500);
                    continue;
                }

                // 3) Проверяем каждую строку: скроллим её и читаем ячейку по columnIndex
                for (SelenideElement row : rows) {
                    try {
                        row.scrollIntoView(false);
                        // универсальный селектор ячейки внутри строки; адаптируй class 'grid-cols-4' при необходимости
                        SelenideElement cell = row.$x(".//div[contains(@class,'grid-cols-')][" + columnIndex + "]");
                        if (!cell.exists()) {
                            // пробуем альтернативный путь: поиск по span внутри div
                            cell = row.$x(".//div[" + columnIndex + "]//span");
                        }
                        if (cell.exists() && cell.isDisplayed()) {
                            String cellText = cell.getText().trim();
                            System.out.println("→ Проверяем ячейку: '" + cellText + "'");
                            if (cellText.equalsIgnoreCase(value)) {
                                System.out.println("✔ Значение найдено: " + value);
                                return;
                            }
                        }
                    } catch (org.openqa.selenium.StaleElementReferenceException ser) {
                        System.out.println("♻ Ссылка протухла (Stale) — пропускаем и повторим цикл");
                        // continue to next row
                    }
                }

                System.out.println("⚠ Значение '" + value + "' пока не найдено — повторяем...");
                Thread.sleep(500);

            } catch (Throwable t) {
                System.out.println("❗ Ошибка внутри цикла: " + t.getClass().getSimpleName() + " - " + t.getMessage());
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            }
        }

        throw new AssertionError("Значение '" + value + "' в колонке '" + columnName + "' не появилось за отведённое время");
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




}
