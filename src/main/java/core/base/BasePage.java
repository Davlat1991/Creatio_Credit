package core.base;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
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

    protected void safeClick(SelenideElement element) {
        element.shouldBe(visible, enabled)
                .scrollIntoView(true);
        try {
            element.click();
        } catch (Throwable t) {
            // fallback to JS click
            executeJavaScript("arguments[0].scrollIntoView(true); arguments[0].click();", element);
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

    //Новый рабочий метод
    @Step("Выход из системы")
    public void logout() {

        System.out.println("➡ Клик по кнопке профиля");
        $x("//*[@data-item-marker='userProfileButton']")
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .click();
        Allure.step("Клик по профилю выполнен");

        System.out.println("➡ Клик по пункту меню 'Выход'");
        $x("//*[@data-item-marker='Выход']")
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .click();
        Allure.step("Клик по 'Выход' выполнен");

        // 🔥 ЛУЧШАЯ И СТАБИЛЬНАЯ ПРОВЕРКА (по DOM реальной страницы)
        $x("//*[@id='loginContainer']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15));

        System.out.println("✔ Logout подтверждён — loginContainer отображается");
        Allure.step("Logout подтверждён — loginContainer отображается");
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

    @Step("Завершить консультацию")
    public void completeConsultation() {

        // 1. Нажимаем кнопку Завершить в панели консультации
        System.out.println("➡ Клик по кнопке 'Завершить' в ConsultationPanel");
        SelenideElement completeBtnPanel = $x("//*[@data-item-marker='CompleteConsultationButton']")
                .shouldBe(Condition.visible, Duration.ofSeconds(10));
        executeJavaScript("arguments[0].click();", completeBtnPanel);
        Allure.step("Клик по кнопке панели 'Завершить'");

        // 2. Ждём появления модального окна
        System.out.println("⏳ Ожидание появления модального окна завершения консультации...");
        SelenideElement modalCompleteBtn = $x("//*[@data-item-marker='CompleteButton']")
                .shouldBe(Condition.visible, Duration.ofSeconds(10));

        // 3. Кликаем кнопку Завершить в модальном окне
        System.out.println("➡ Клик по кнопке 'Завершить' в модальном окне");
        executeJavaScript("arguments[0].click();", modalCompleteBtn);
        Allure.step("Клик по кнопке модального окна 'Завершить'");

        System.out.println("✔ Консультация успешно завершена");
    }








}


