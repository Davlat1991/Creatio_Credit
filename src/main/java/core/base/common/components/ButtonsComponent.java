package core.base.common.components;


import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import core.pages.credit.ContractCreditApplicationPage;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

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

    public ButtonsComponent clickButtonByContainName1(String name2) {

        for (int i = 0; i < 3; i++) {

            SelenideElement button = $x("//span[contains(text(), '" + name2 + "')]")
                    .shouldBe(Condition.visible);

            button.scrollIntoView(true).click();

            try {
                // проверяем что вкладка стала активной
                button.closest("li")
                        .shouldHave(Condition.cssClass("ts-tabpanel-active-item"));

                return this; // успех
            } catch (Exception e) {
                // retry
            }
        }

        throw new RuntimeException("Не удалось нажать на кнопку: " + name2);
    }

    public ButtonsComponent clickButtonByContainNameRetry(String name2) {

        for (int i = 0; i < 3; i++) {
            try {

                // 1️⃣ ищем кнопку
                SelenideElement button = $x("//li//span[normalize-space(text())='" + name2 + "']")
                        .shouldBe(Condition.visible);

                // 2️⃣ скролл (обязательно для tabpanel)
                button.scrollIntoView(true);

                // 3️⃣ JS click (устойчиво к overlay)
                executeJavaScript("arguments[0].click();", button);

                // 4️⃣ проверяем, что вкладка активна
                button.closest("li")
                        .shouldHave(Condition.cssClass("ts-tabpanel-active-item"));

                // 🔥 5️⃣ ЖДЁМ ЯКОРЬ — ДЕТАЛЬ "ЗАЛОГИ"
                $x("//div[@data-item-marker='Залоги']")
                        .shouldBe(Condition.visible);

                return this;

            } catch (Exception e) {
                sleep(300); // retry
            }
        }

        throw new RuntimeException("Не удалось нажать на кнопку: " + name2);
    }


    public ButtonsComponent clickButtonByContainName3(String name2) {

        for (int i = 0; i < 3; i++) {

            try {
                // 🔥 1️⃣ ЖДЁМ АКТИВНОСТЬ (а не Execute)
                SelenideElement activity = $x("//span[contains(text(),'Заполните данные обеспечения')]")
                        .shouldBe(Condition.visible);

                // 🔥 2️⃣ ДЕЛАЕМ HOVER (КЛЮЧЕВОЙ МОМЕНТ)
                activity.hover();

                // 3️⃣ теперь ищем кнопку
                SelenideElement button = $x("//span[contains(text(), '" + name2 + "')]")
                        .shouldBe(Condition.visible);

                // 4️⃣ скролл
                button.scrollIntoView(true);

                // 5️⃣ JS click (защита от overlay)
                executeJavaScript("arguments[0].click();", button);

                // 6️⃣ проверка
                button.closest("li")
                        .shouldHave(Condition.cssClass("ts-tabpanel-active-item"));

                return this;

            } catch (Exception e) {
                sleep(300);
            }
        }

        throw new RuntimeException("Не удалось нажать на кнопку: " + name2);
    }



    public ButtonsComponent clickButtonByContainName2(String name) {

        for (int i = 0; i < 4; i++) {

            try {
                // 1️⃣ ждём пока кнопка станет кликабельной
                SelenideElement button = $x("//span[contains(text(), '" + name + "')]")
                        .shouldBe(Condition.visible)
                        .scrollIntoView(false);

                // 2️⃣ 🔥 ждём исчезновения loader
                $$(" .ts-loader, .mask, .loading-indicator")
                        .filter(Condition.visible)
                        .shouldHave(CollectionCondition.size(0));

                // 3️⃣ 🔥 используем JS click (ключ!)
                executeJavaScript("arguments[0].click();", button);

                // 4️⃣ ждём активацию вкладки
                button.closest("li")
                        .shouldHave(Condition.cssClass("ts-tabpanel-active-item"));

                return this;

            } catch (Exception e) {
                sleep(300);
            }
        }

        throw new RuntimeException("Не удалось кликнуть вкладку: " + name);
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
