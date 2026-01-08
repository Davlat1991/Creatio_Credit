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


    @Step("Кликнуть кнопку на странице '{pageMarker}' по тексту: '{name}'")
    public ButtonsComponent clickOnPageByName(String pageMarker, String name) {
        SelenideElement pageContainer = $x("//*[@data-item-marker='" + pageMarker + "']")
                .shouldBe(visible);
        SelenideElement button = pageContainer.$x(".//span[normalize-space(text())='" + name + "']")
                .shouldBe(visible, enabled);
        retryClick(button, "Кнопка на странице '" + pageMarker + "' -> '" + name + "'");
        return this;
    }


    @Step("Клик по кнопке data-item-marker='{marker}'")
    public ButtonsComponent clickByDataItemMarker(String marker) {
        SelenideElement button = $x("//span[@data-item-marker='" + marker + "']");
        retryClick(button, "Кнопка marker='" + marker + "'");
        return this;
    }


    @Step("Клик по span с id='{id}'")
    public ButtonsComponent clickById(String id) {
        SelenideElement button = $x("//span[@id='" + id + "']");
        retryClick(button, "Кнопка id='" + id + "'");
        return this;
    }


    @Step("Клик по элементу <{tag}> с data-item-marker='{dim}'")
    public ButtonsComponent clickElementByTagAndDIM(String tag, String dim) {
        SelenideElement element = $x("//" + tag + "[@data-item-marker='" + dim + "']");
        retryClick(element, "Элемент <" + tag + "> dim='" + dim + "'");
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

    public ButtonsComponent clickButtonByContainName(String NameNew) {
        $x("//span[contains(text(), '" + NameNew + "')]").click();
        return this;
    }

    public ButtonsComponent doubleclickButtonByName(String nameButton){
        $x("//span[.='" + nameButton + "']").doubleClick();

        return this;
    }



}
