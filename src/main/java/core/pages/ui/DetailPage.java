package core.pages.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

public class DetailPage {

    /**
     * Надёжно открывает деталь по её заголовку.
     */
    public DetailPage openDetailByName1(String detailName) {

        SelenideElement header =
                $x("//span[normalize-space()='" + detailName + "']")
                        .shouldBe(Condition.visible);

        SelenideElement container = header.closest("div");

        if (container.getAttribute("class") != null &&
                container.getAttribute("class").contains("collapsed")) {

            header.scrollIntoView(true).click();

            container.shouldNotHave(
                    Condition.attributeMatching("class", ".*collapsed.*")
            );
        }

        return this;
    }

    /**
     * Проверяет, что деталь открыта и видима.
     */
    public DetailPage assertDetailOpened(String detailName) {

        SelenideElement header =
                $x("//span[normalize-space()='" + detailName + "']")
                        .shouldBe(Condition.visible);

        SelenideElement container = header.closest("div");

        container.shouldNotHave(
                Condition.attributeMatching("class", ".*collapsed.*")
        );

        return this;
    }

    /**
     * Универсальный метод клика по элементу внутри детали — по тегу и тексту.
     * Работает и со span, и с div, и с label.
     */
    public DetailPage clickElementByTagAndName(String tag, String name) {

        SelenideElement element =
                $x("//" + tag + "[normalize-space()='" + name + "']")
                        .shouldBe(Condition.visible)
                        .scrollIntoView(true);

        element.click();

        return this;
    }


    //Работает 19.12.2025

    public DetailPage openDetailByName2(String detailName) {
        if ($x("//span[.='" + detailName + "']/../..")
                .getAttribute("class")
                .contains("collapsed")) {

            $x("//span[.='" + detailName + "']").click();
        }
        return this;
    }



    // 14.01.2026

    @Step("Открыть деталь '{detailTitle}'")
    public void openDetailByName3(String detailTitle) {
        $x("//span[normalize-space()='" + detailTitle + "']")
                .shouldBe(Condition.visible)
                .click();
    }

    @Step("Дождаться загрузки grid детали")
    public void waitUntilDetailGridReady() {
        $x(".//div[contains(@class,'grid-layout')]")
                .shouldBe(Condition.visible);
    }

    @Step("Получить ID активной записи детали")
    public String getActiveDetailRecordId() {

        SelenideElement row =
                $(".doc-row")
                        .shouldBe(Condition.exist);

        return row.getAttribute("data-id");
    }

    @Step("Обновить деталь")
    public void refreshDetail() {
        Selenide.refresh();
        waitUntilDetailGridReady();
    }

    @Step("Открыть деталь '{detailName}'")
    public DetailPage openDetailByName(String detailName) {

        SelenideElement container =
                $x("//span[.='" + detailName + "']/../..");

        if (container.getAttribute("class").contains("collapsed")) {
            $x("//span[.='" + detailName + "']").click();
        }

        return this;
    }




}




