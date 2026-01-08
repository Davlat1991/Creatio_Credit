package core.assertions;

import com.codeborne.selenide.*;
import core.base.common.components.GridComponent;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;


public class GridAssertions {

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





}
