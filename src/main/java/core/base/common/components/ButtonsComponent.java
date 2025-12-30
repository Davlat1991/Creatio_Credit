package core.base.common.components;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import core.pages.credit.ContractCreditApplicationPage;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;

/**
 * ButtonsComponent — все универсальные клики вынесены сюда.
 * Делает клики стабильными (scrollIntoView, visible, enabled, retry).
 */
public class ButtonsComponent extends Components {

    @Step("Кликнуть кнопку по тексту: '{name}'")
    public ButtonsComponent clickByName(String name) {
        SelenideElement button = $x("//span[normalize-space(text())='" + name + "']");
        retryClick(button, "Кнопка '" + name + "'");
        return this;
    }

    @Step("Кликнуть кнопку по тексту с проверкой доступности: '{name}'")
    public ButtonsComponent clickByNameCheck(String name) {
        SelenideElement button = $x("//span[normalize-space(text())='" + name + "']")
                .shouldBe(visible, enabled)
                .scrollIntoView(true);
        retryClick(button, "Кнопка (check) '" + name + "'");
        return this;
    }

    @Step("Кликнуть кнопку на странице '{pageMarker}' по тексту: '{name}'")
    public ButtonsComponent clickOnPageByName(String pageMarker, String name) {
        SelenideElement pageContainer = $x("//*[@data-item-marker='" + pageMarker + "']")
                .shouldBe(visible);
        SelenideElement button = pageContainer.$x(".//span[normalize-space(text())='" + name + "']")
                .shouldBe(visible, enabled);
        retryClick(button, "Кнопка на странице '" + pageMarker + "' -> '" + name + "'");
        return this;
    }

    @Step("Кликнуть кнопку на странице (с retry) '{pageMarker}' -> '{name}'")
    public ButtonsComponent clickOnPageByNameNew(String pageMarker, String name) {

        int maxAttempts = 3;
        Throwable lastError = null;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                SelenideElement pageContainer = $x("//*[@data-item-marker='" + pageMarker + "']")
                        .shouldBe(visible, Duration.ofSeconds(5));

                SelenideElement button = pageContainer.$x(".//span[normalize-space(text())='" + name + "']")
                        .shouldBe(visible, enabled);

                retryClick(button, "Кнопка (new) '" + name + "' на странице '" + pageMarker + "'");
                return this;

            } catch (Throwable e) {
                lastError = e;
                if (attempt == maxAttempts) {
                    throw new RuntimeException(
                            "❌ Не удалось нажать кнопку '" + name +
                                    "' на странице '" + pageMarker + "' после " + maxAttempts + " попыток", e);
                }
                // мягкий retry
                sleep(400);
            }
        }

        throw new RuntimeException("Неизвестная ошибка клика", lastError);
    }

    @Step("Клик по span с ID-кнопки и текстом '{name}'")
    public ButtonsComponent clickByIdName(String name) {
        SelenideElement button = $x("//span[contains(@id,'Button')][normalize-space(text())='" + name + "']");
        retryClick(button, "Кнопка idName '" + name + "'");
        return this;
    }

    @Step("Клик по кнопке data-item-marker='{marker}'")
    public ButtonsComponent clickByDataItemMarker(String marker) {
        SelenideElement button = $x("//span[@data-item-marker='" + marker + "']");
        retryClick(button, "Кнопка marker='" + marker + "'");
        return this;
    }

    @Step("Клик по кнопке data-item-marker (check)='{marker}'")
    public ButtonsComponent clickByDataItemMarkerCheck(String marker) {
        SelenideElement button = $x("//span[@data-item-marker='" + marker + "']")
                .shouldBe(visible, enabled);
        retryClick(button, "Кнопка marker check='" + marker + "'");
        return this;
    }

    @Step("Клик по span с id='{id}'")
    public ButtonsComponent clickById(String id) {
        SelenideElement button = $x("//span[@id='" + id + "']");
        retryClick(button, "Кнопка id='" + id + "'");
        return this;
    }

    @Step("Клик по элементу <{tag}> с текстом '{name}'")
    public ButtonsComponent clickElementByTagAndName(String tag, String name) {
        SelenideElement element = $x("//" + tag + "[normalize-space(text())='" + name + "']");
        retryClick(element, "Элемент <" + tag + "> '" + name + "'");
        return this;
    }

    @Step("Клик по элементу <{tag}> с data-item-marker='{dim}'")
    public ButtonsComponent clickElementByTagAndDIM(String tag, String dim) {
        SelenideElement element = $x("//" + tag + "[@data-item-marker='" + dim + "']");
        retryClick(element, "Элемент <" + tag + "> dim='" + dim + "'");
        return this;
    }

    /**
     * Универсальная безопасная обёртка (для внешнего использования).
     * Делегирует в retryClick (Components).
     */
    @Step("Безопасный клик по элементу (универсальный)")
    public ButtonsComponent safeClickElement(SelenideElement element) {
        retryClick(element, "safeClickElement");
        return this;
    }

    @Step("Двойной клик по элементу с marker '{marker}'")
    public ButtonsComponent doubleClickByMarker(String marker) {
        SelenideElement el = $x("//*[@data-item-marker='" + marker + "']")
                .shouldBe(visible, enabled);

        el.doubleClick();
        return this;
    }

    @Step("Двойной клик по кнопке по имени '{name}'")
    public ButtonsComponent doubleClickByName(String name) {
        SelenideElement el = $x("//span[normalize-space(text())='" + name + "']")
                .shouldBe(visible, enabled);

        el.doubleClick();
        return this;
    }

        //Миграция методов

    public ButtonsComponent clickButtonByName(String name) {
        $x("//span[normalize-space()='" + name + "']")
                .shouldBe(visible, enabled)
                .scrollIntoView(true)
                .click();
        return this;
    }

    public ButtonsComponent clickButtonByNameCheck(String nameButton) {

        SelenideElement button = $x("//span[.='" + nameButton + "']")
                .shouldBe(visible)
                .shouldBe(enabled);

        button.click();

        return this;
    }

    public ButtonsComponent clickButtonByContainName(String NameNew) {
        $x("//span[contains(text(), '" + NameNew + "')]").click();
        return this;
    }

    public ButtonsComponent doubleclickButtonByName(String nameButton){
        $x("//span[.='" + nameButton + "']").doubleClick();

        return this;
    }

    public ButtonsComponent clickButtonByContainNameCheck(String Value) {
        SelenideElement element = $x("//span[contains(text(), '" + Value + "')]")
                .shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .shouldHave(Condition.text(Value));

        element.hover();                    // помогает "активировать" элемент
        element.shouldBe(Condition.interactable); // теперь interactable безопасен

        element.click();

        return this;
    }

    /** Клик кнопки по частичному совпадение имени и индекс */
    public ButtonsComponent clickButtonByNameContains(String nameButton, int index){
        $x("(//span[contains(.,'" + nameButton + " (')])[" + index + "]").click(); //span[contains(.,'Файлы (')][1]
        return this;
    }



}
