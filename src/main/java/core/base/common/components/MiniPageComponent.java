package core.base.common.components;


import com.codeborne.selenide.*;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;


/**
 * Компонент для стабильной работы с MiniPage в Creatio.
 * Поддерживает overlay, кнопки Execute/Save, выбор значений.
 */
public class MiniPageComponent extends Components {

    /**
     * Возвращает mini-page overlay по стандартному data-item-marker='MiniPage'
     * Всегда ждём видимости — мини-страница появляется не сразу!
     */
    private SelenideElement getMiniPage() {
        return $x("//*[@data-item-marker='MiniPage']")
                .shouldBe(visible);
    }

    /**
     * Ожидание закрытия мини-страницы
     */
    @Step("Ожидать закрытие MiniPage")
    public MiniPageComponent waitClose() {
        $x("//*[@data-item-marker='MiniPage']").shouldBe(disappear);
        return this;
    }

    /**
     * Клик по кнопке внутри MiniPage (например Execute, Save, Выполнить)
     */
    @Step("Нажать кнопку '{buttonText}' внутри MiniPage")
    public MiniPageComponent clickButton(String buttonText) {

        SelenideElement miniPage = getMiniPage();

        SelenideElement button = miniPage.$x(
                ".//span[contains(text(),'" + buttonText + "')]"
        ).shouldBe(visible, enabled);

        retryClick(button, "Клик по кнопке внутри MiniPage '" + buttonText + "'");

        return this;
    }

    /**
     * Клик по кнопке с data-item-marker внутри MiniPage
     */
    @Step("Нажать кнопку marker '{marker}' внутри MiniPage")
    public MiniPageComponent clickButtonByDIM(String marker) {

        SelenideElement miniPage = getMiniPage();

        SelenideElement button = miniPage.$x(
                ".//*[@data-item-marker='" + marker + "']"
        ).shouldBe(visible, enabled);

        retryClick(button, "Клик по кнопке с marker='" + marker + "' внутри MiniPage");

        return this;
    }

    /**
     * Выбрать пункт списка внутри MiniPage
     */

    @Step("Выбрать значение '{value}' в списке MiniPage")
    public MiniPageComponent clickListItem(String value) {

        SelenideElement miniPage = getMiniPage();

        SelenideElement item = miniPage.$x(
                ".//li[normalize-space(text())='" + value + "']"
        ).shouldBe(visible);

        jsClick(item); // в Creatio listitem часто не кликается обычным click()

        return this;
    }

    /**
     * Универсальный метод выбора элемента по data-item-marker внутри MiniPage
     */
    @Step("Выбрать пункт marker '{marker}' в MiniPage")
    public MiniPageComponent clickListItemByDIM(String marker) {

        SelenideElement miniPage = getMiniPage();

        SelenideElement item = miniPage.$x(
                ".//*[@data-item-marker='" + marker + "']"
        ).shouldBe(visible);

        jsClick(item);

        return this;
    }

    /**
     * Проверяет текст внутри MiniPage (например "Добавьте и заполните анкеты участников заявки")
     */
    @Step("Проверить, что MiniPage содержит текст '{text}'")
    public MiniPageComponent validateText(String text) {
        getMiniPage().shouldHave(text(text));
        return this;
    }

    @Step("Ожидать открытие MiniPage '{marker}'")
    public MiniPageComponent waitOpen(String marker) {
        $x("//div[@data-item-marker='" + marker + "']")
                .shouldBe(visible);
        return this;
    }


    @Step("Ожидать открытие MiniPage '{markerOrXpath}'")
    public MiniPageComponent waitOpenUni(String markerOrXpath) {

        String xpath;

        if (markerOrXpath.startsWith("//")) {
            xpath = markerOrXpath; // raw xpath
        } else if (markerOrXpath.contains("Container")) {
            xpath = "//*[@id='" + markerOrXpath + "']";  // id контейнера
        } else {
            xpath = "//*[@data-item-marker='" + markerOrXpath + "']";  // стандарт
        }

        $x(xpath).shouldBe(visible);

        return this;
    }



}

