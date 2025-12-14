package core.base;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
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

    protected void scrollToTop() {
        executeJavaScript("window.scrollTo(0,0);");
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


    protected void clickElementByTagAndDIMNew(String tag, String dataItemMarker) {
        SelenideElement element = $x("//" + tag + "[@data-item-marker='" + dataItemMarker + "']")
                .shouldBe(visible)
                .scrollIntoView(true);

        safeClick(element);
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



}


