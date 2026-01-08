package core.base.common.components;

import com.codeborne.selenide.*;
import core.base.BasePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * Универсальный компонент для всех UI-элементов Creatio.
 * Даёт стабильные методы поиска, клика, скролла и JS-взаимодействия.
 */
public class Components extends BasePage {


    protected SelenideElement findByXpath(String xpath) {
        return $x(xpath).shouldBe(visible);
    }

    protected SelenideElement findVisible(String xpath) {
        return $x(xpath).shouldBe(visible, enabled);
    }

    @Step("Клик по элементу: {description}")
    protected void click(SelenideElement element, String description) {
        retryClick(element, description);
    }

    /**
     * Мягкий безопасный клик с retry — критично для Creatio,
     * т.к. DOM часто перерисовывается.
     */
    protected void retryClick(SelenideElement element, String description) {
        int attempts = 3;
        Throwable last = null;

        for (int i = 1; i <= attempts; i++) {
            try {
                element.shouldBe(Condition.visible, Condition.enabled)
                        .scrollIntoView(true)
                        .click();
                return;
            } catch (Throwable e) {
                last = e;

                // ждём, пока DOM стабилизируется
                $$(".ts-loader, .ui-loader, .process-indicator")
                        .shouldHave(CollectionCondition.size(0));
            }
        }
        throw new RuntimeException("Не удалось кликнуть по элементу: " + description, last);
    }


    protected void jsClick(SelenideElement element) {
        Selenide.executeJavaScript("arguments[0].click();", element);
    }

    protected void hover(SelenideElement element) {
        element.hover();
    }

    protected void waitDisappear(SelenideElement element) {
        element.shouldBe(disappear);
    }



}
