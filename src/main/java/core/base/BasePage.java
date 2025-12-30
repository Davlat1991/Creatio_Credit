package core.base;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import core.pages.credit.ContractCreditApplicationPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import java.time.Duration;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * BasePage — легкий фундамент для компонентов и PageObjects.
 * Только универсальные утилиты: safeClick, safeType, jsClick, waitForPageLoad, waitUntilNotBusy.
 */
public class BasePage {

    //Скролл вправо (Вкладки) 07.12.2025 //Работает
    @Step("Нажать на элемент")
    public void safeClick(SelenideElement element) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                element.shouldBe(Condition.visible, Condition.enabled)
                        .scrollIntoView(true)
                        .click();
                return;
            } catch (Exception e) {
                attempts++;
                if (attempts == 3) {
                    throw e;
                }
                Selenide.sleep(500);
            }
        }
    }


//Проверить метод и заменить или удалить !!!
    protected void safeClickNew(SelenideElement element) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                element.shouldBe(visible, enabled)
                        .scrollIntoView(true)
                        .click();
                return;
            } catch (Throwable t) {
                attempts++;
                if (attempts == 3) {
                    executeJavaScript(
                            "arguments[0].scrollIntoView(true); arguments[0].click();",
                            element
                    );
                }
                Selenide.sleep(300);
            }
        }
    }


    protected void safeType(SelenideElement element, String value) {
        element.shouldBe(visible, enabled)
                .scrollIntoView(true);
        try {
            element.clear();
            element.setValue(value);
        } catch (Throwable t) {
            // fallback set via JS and dispatch input event
            executeJavaScript(
                    "arguments[0].value = arguments[1];" +
                            "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));",
                    element, value
            );
        }
    }


    protected void jsClick(SelenideElement element) {
        executeJavaScript("arguments[0].scrollIntoView(true); arguments[0].click();", element);
    }


    public void scrollToTop() {
        executeJavaScript("window.scrollTo(0,0);");
    }


    @Step("Скроллим немного вниз")
    public void scrollDownSmall() {
        System.out.println("🔽 Скроллим на один экран вниз");
        executeJavaScript("window.scrollBy(0, 500)");
    }


    protected void waitForPageLoad() {
        // ждем, пока document.readyState станет complete
        Wait().until(webDriver ->
                executeJavaScript("return document.readyState").equals("complete")
        );

        // ждем исчезновение всех индикаторов загрузки Creatio
        $$(".ts-loader, .ui-loader, .process-indicator, .loading-indicator, .mask")
                .filter(Condition.visible)
                .shouldHave(CollectionCondition.size(0));
    }


    protected void waitUntilNotBusy() {
        $$(".ts-loader, .ui-loader, .process-indicator, .loading-indicator, .mask")
                .shouldHave(CollectionCondition.size(0));
    }

    /**
     * Общий retry wrapper — компоненты могут использовать при необходимости.
     * Usage example inside component: retry(() -> { safeClick(elem); return true; }, 3);
     */
    protected <T> T retry(java.util.concurrent.Callable<T> callable, int attempts, long sleepMs) {
        Throwable last = null;
        for (int i = 1; i <= attempts; i++) {
            try {
                return callable.call();
            } catch (Throwable e) {
                last = e;
                if (i == attempts) break;
                Selenide.sleep(sleepMs);
            }
        }
        throw new RuntimeException("Retry failed after " + attempts + " attempts", last);
    }


    protected void clickElementByTagAndNameNew(String tag, String name) {
        SelenideElement element = $x("//" + tag + "[normalize-space()='" + name + "']")
                .shouldBe(visible)
                .scrollIntoView(true);

        safeClick(element);
    }


    @Step("Клик по элементу <{tag}> с текстом '{name}'")
    public BasePage clickElementByTagAndName(String tag, String name) {

        SelenideElement element = $x("//" + tag + "[normalize-space()='" + name + "']")
                .shouldBe(visible)
                .scrollIntoView(true);

        // Creatio DOM: обычный .click() часто не срабатывает → используем JS
        Selenide.executeJavaScript("arguments[0].click();", element);

        return this;
    }


    @Step("Клик по элементу <{tag}> с data-item-marker='{dim}'")
    public BasePage clickElementByTagAndDIM(String tag, String dim) {

        SelenideElement element = $x("//" + tag + "[@data-item-marker='" + dim + "']")
                .shouldBe(visible)
                .scrollIntoView(true);

        // Creatio часто блокирует обычный click()
        Selenide.executeJavaScript("arguments[0].click();", element);

        return this;
    }


    public BasePage clickButtonByNameCheck(String nameButton) {
        SelenideElement button = $x("//span[.='" + nameButton + "']")
                .shouldBe(visible)
                .shouldBe(enabled)
                .scrollIntoView(true);

        button.click();

        return this;
    }


    protected void waitForLoader() {
        $x("//div[contains(@class,'ts-loader-mask')]")
                .should(disappear, Duration.ofSeconds(15));
    }


//Добавлен вручную 18.12.2025
    public BasePage clickButtonById(String buttonId) {

        SelenideElement button = $x("//span[@id='" + buttonId + "']")
                .shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .scrollIntoView(true);

        try {
            button.click();
        } catch (Throwable e) {
            executeJavaScript("arguments[0].click();", button);
        }

        return this;
    }


    //Добавлен вручную 18.12.2025
    public BasePage clickButtonByDataItemMaker(String dataItemMarker) {
        $x("//span[@data-item-marker='" + dataItemMarker + "']").click();
        return this;
    }


    protected void clickElementByTagAndDIMNew(String tag, String dataItemMarker) {
        SelenideElement element = $x("//" + tag + "[@data-item-marker='" + dataItemMarker + "']")
                .shouldBe(visible)
                .scrollIntoView(true);

        safeClick(element);
    }


    public BasePage clickButtonByDataItemMakerCheck(String dataItemMarker) {
        SelenideElement button = $x("//span[@data-item-marker='" + dataItemMarker + "']")
                .shouldBe(Condition.visible)       // ждём появления
                .shouldBe(enabled);      // ждём доступности для клика

        button.click();

        return this;
    }


    @Step("Ожидание загрузки страницы")
    public void waitForPage() {
        for (int i = 0; i < 40; i++) {

            boolean ready = Selenide.executeJavaScript(
                    "return document.readyState === 'complete';"
            );

            boolean busy = false;
            for (SelenideElement e : $$(".ts-loader, .ui-loader, .process-indicator, .loading-indicator, .mask")) {
                if (e.exists() && e.is(Condition.visible)) {
                    busy = true;
                    break;
                }
            }

            if (ready && !busy) {
                Selenide.sleep(200);
                return;
            }

            Selenide.sleep(250);
        }

        throw new RuntimeException("Страница не загрузилась вовремя");
    }



    //Новый метод 05.12.2025 Статус:

    public BasePage clickByDataMarker(String markernew) {
        SelenideElement el = $x("//*[@data-item-marker='" + markernew + "']")
                .shouldBe(Condition.visible)
                .scrollIntoView(true)
                .shouldBe(enabled);

        el.click();
        return this;
    }


    public BasePage clickButtonOnPageByName(String pageMarker, String nameButton) {

        SelenideElement pageContainer =
                $x("//*[@data-item-marker='" + pageMarker + "']")
                        .shouldBe(visible);

        SelenideElement button =
                pageContainer.$x(".//span[.='" + nameButton + "']")
                        .shouldBe(visible)
                        .shouldBe(enabled);

        button.click();

        return this;
    }


    public BasePage clickButtonByName(String nameButton){
        $x("//span[.='" + nameButton + "']").click();
        return this;
    }



    //Migration

    @Step("Проверить, что текущая страница имеет маркер '{expectedPageMarker}'")
    public BasePage checkCurrentPage(String expectedPageMarker) {

        for (int attempt = 1; attempt <= 5; attempt++) {
            try {
                $x("//*[@data-item-marker='" + expectedPageMarker + "']")
                        .should(appear);
                return this;

            } catch (Throwable e) {
                if (attempt == 5) {
                    throw e;
                }
            }
        }
        return this;
    }



    @Step("Ожидаем появление кнопки по маркеру '{marker}' и нажимаем на неё")
    public void waitAndClickByDIM(String value) {

        String xpath = "//*[@data-item-marker='" + value + "']";

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🔎 Старт ожидания кнопки");
        System.out.println("➡ Маркер кнопки: " + value);
        System.out.println("➡ XPath: " + xpath);
        System.out.println("➡ Максимум попыток: 30 (интервал 5 сек)");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        int retries = 30;
        int pauseMs = 5000;

        for (int i = 1; i <= retries; i++) {

            System.out.println("🔁 Попытка " + i + " из " + retries);

            try {
                SelenideElement button = $x(xpath);

                if (button.exists()) {
                    System.out.println("   ✔ Элемент существует в DOM");

                    if (button.isDisplayed()) {
                        System.out.println("   ✔ Элемент видимый → пытаемся нажать...");

                        button
                                .shouldBe(Condition.visible, Duration.ofSeconds(5))
                                .shouldBe(Condition.enabled, Duration.ofSeconds(5))
                                .click();

                        System.out.println("🎉 УСПЕХ! Кнопка нажата → data-item-marker='" + value + "'");
                        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                        return;
                    } else {
                        System.out.println("   ⚠ Элемент найден, но пока НЕ видим → ждём...");
                    }
                } else {
                    System.out.println("   ⏳ Кнопка пока не найдена в DOM");
                }

            } catch (Exception e) {
                System.out.println("   ⚠ Ошибка при обращении к элементу: " + e.getMessage());
                System.out.println("   ↺ Повторяем попытку...");
            }

            // ⬇⬇⬇ ДОБАВЛЕН refresh — единственное изменение! ⬇⬇⬇
            System.out.println("🔄 Обновляем страницу (refresh), чтобы подтянуть актуальные данные...");
            Selenide.refresh();

            Selenide.sleep(pauseMs);
        }

        System.out.println("❌ ОШИБКА: Кнопка так и не появилась!");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        throw new AssertionError("Кнопка с data-item-marker='" + value + "' не появилась за заданное время!");
    }



    @Step("Ожидаем появление кнопки по маркеру '{marker}' и нажимаем на неё")
    public void waitAndClickByMarkerNew(String DIM) {

        String xpath = "//*[@data-item-marker='" + DIM + "']";

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🔎 Старт ожидания кнопки");
        System.out.println("➡ Маркер кнопки: " + DIM);
        System.out.println("➡ XPath: " + xpath);
        System.out.println("➡ Максимум попыток: 20 (интервал 3 сек)");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        int retries = 20;
        int pauseMs = 3000;

        for (int i = 1; i <= retries; i++) {

            System.out.println("🔁 Попытка " + i + " из " + retries);

            try {
                SelenideElement button = $x(xpath);

                if (button.exists()) {
                    System.out.println("   ✔ Элемент существует в DOM");

                    if (button.isDisplayed()) {
                        System.out.println("   ✔ Элемент видимый → пытаемся нажать...");

                        button
                                .shouldBe(Condition.visible, Duration.ofSeconds(5))
                                .shouldBe(Condition.enabled, Duration.ofSeconds(5))
                                .click();

                        System.out.println("🎉 УСПЕХ! Кнопка нажата → data-item-marker='" + DIM + "'");
                        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                        return;
                    } else {
                        System.out.println("   ⚠ Элемент найден, но пока НЕ видим → ждём...");
                    }
                } else {
                    System.out.println("   ⏳ Кнопка пока не найдена в DOM");
                }

            } catch (Exception e) {
                System.out.println("   ⚠ Ошибка при обращении к элементу: " + e.getMessage());
                System.out.println("   ↺ Повторяем попытку...");
            }

            Selenide.sleep(pauseMs);
        }

        System.out.println("❌ ОШИБКА: Кнопка так и не появилась!");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        throw new AssertionError("Кнопка с data-item-marker='" + DIM + "' не появилась за заданное время!");
    }



    // Универсальный метод, протестировать и удалить старые методы
    @Step("Ожидаем кнопку по marker '{marker}' и кликаем")
    public void waitAndClickByMarker(String marker) {

        String xpath = "//*[@data-item-marker='" + marker + "']";

        int retries = 20;
        int pauseMs = 3000;

        for (int i = 1; i <= retries; i++) {
            try {
                SelenideElement button = $x(xpath);

                if (button.exists() && button.isDisplayed()) {
                    button
                            .shouldBe(visible, Duration.ofSeconds(5))
                            .shouldBe(enabled, Duration.ofSeconds(5))
                            .click();
                    return;
                }

            } catch (Exception ignored) {
            }

            Selenide.sleep(pauseMs);
        }

        throw new AssertionError(
                "Кнопка с data-item-marker='" + marker + "' не появилась за отведённое время"
        );
    }


}


