package core.base.common.components;


import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import core.pages.credit.ContractCreditApplicationPage;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static core.base.common.components.LookupComponent.log;

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
    public ButtonsComponent clickByDataItemMarker(String marker1) {
        SelenideElement button = $x("//span[@data-item-marker='" + marker1 + "']");
        retryClick(button, "Кнопка marker='" + marker1 + "'");
        return this;
    }

    @Step("Click button by marker: {marker}")
    public ButtonsComponent clickByMarker(String marker1) {
        log.info("Click button marker: '{}'", marker1);
        SelenideElement btn = $x("//span[@data-item-marker='" + marker1 + "']")
                .shouldBe(visible, enabled);
        executeJavaScript("arguments[0].click();", btn);
        return this;
    }


    @Step("Поиск участника по ФИО: {fullName}")
    public void searchParticipantByName1(String fullName) {
        log.info("Search participant: '{}'", fullName);

        // Ждём что поле Отчества не пустое (любое значение)
        $x("//div[@id='BnzSearchContactModalPageMiddleNameContainer_Control']//input")
                .shouldNotBe(empty, Duration.ofSeconds(20));

        // Ждём появления кнопки Поиск
        SelenideElement searchBtn = $x("//span[@data-item-marker='SearchButton']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(enabled, Duration.ofSeconds(5));

        executeJavaScript("arguments[0].click();", searchBtn);

        try {
            $x("//span[contains(@class,'grid-label') and normalize-space(.)='ФИО']")
                    .shouldBe(visible, Duration.ofSeconds(10));
        } catch (Throwable e) {
            log.warn("Search result not appeared, retrying click...");
            executeJavaScript("arguments[0].click();", searchBtn);
            $x("//span[contains(@class,'grid-label') and normalize-space(.)='ФИО']")
                    .shouldBe(visible, Duration.ofSeconds(15));
        }

        log.info("Search results appeared");
    }


    @Step("Поиск участника по ФИО: {fullName}")
    public void searchParticipantByName2(String fullName) {
        log.info("Search participant: '{}'", fullName);

        // Ждём появления кнопки Поиск в модале
        SelenideElement searchBtn = $x("//span[@data-item-marker='SearchButton']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(enabled, Duration.ofSeconds(5));

        // Кликаем
        executeJavaScript("arguments[0].click();", searchBtn);

        // Ждём результат: колонка ФИО в гриде результатов
        // Если через 10 сек результата нет — кликаем ещё раз (один retry)
        try {
            $x("//span[contains(@class,'grid-label') and normalize-space(.)='ФИО']")
                    .shouldBe(visible, Duration.ofSeconds(15));
        } catch (Throwable e) {
            log.warn("Search result not appeared, retrying click...");
            executeJavaScript("arguments[0].click();", searchBtn);
            $x("//span[contains(@class,'grid-label') and normalize-space(.)='ФИО']")
                    .shouldBe(visible, Duration.ofSeconds(15));
        }

        log.info("Search results appeared");
    }


    @Step("Поиск участника по ФИО: {fullName}")
    public void searchParticipantByName(String fullName) {
        log.info("Search participant: '{}'", fullName);

        // Ждём модал
        $x("//div[contains(@class,'bnz-search-questionnaire-container')]")
                .shouldBe(visible, Duration.ofSeconds(15));

        // Берём SearchButton ТОЛЬКО внутри модала участника
        SelenideElement searchBtn = $x(
                "//div[@id='BnzSearchContactModalPageCardContentContainerContainer']" +
                        "//span[@data-item-marker='SearchButton']"
        ).shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(enabled, Duration.ofSeconds(5));

        executeJavaScript("arguments[0].click();", searchBtn);

        try {
            $x("//span[contains(@class,'grid-label') and normalize-space(.)='ФИО']")
                    .shouldBe(visible, Duration.ofSeconds(15));
        } catch (Throwable e) {
            log.warn("Search result not appeared, retrying click...");
            executeJavaScript("arguments[0].click();", searchBtn);
            $x("//span[contains(@class,'grid-label') and normalize-space(.)='ФИО']")
                    .shouldBe(visible, Duration.ofSeconds(15));
        }

        log.info("Search results appeared");
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

    public ButtonsComponent clickButtonByName(String name1) {
        $x("//span[normalize-space()='" + name1 + "']")
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

    @Step("Открыть вкладку 'Документы'")
    public void openDocumentsTab() {
        $x("//span[normalize-space()='Документы']")
                .shouldBe(Condition.visible)
                .click();
    }




}
