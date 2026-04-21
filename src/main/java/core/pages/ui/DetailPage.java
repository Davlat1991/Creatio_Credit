package core.pages.ui;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

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

    @Step("Открыть деталь '{detailName}'")
    public DetailPage openDetailByName5(String detailName) {

        SelenideElement header = $x("//span[normalize-space()='" + detailName + "']");

        header.shouldBe(Condition.visible);

        SelenideElement container = header.closest("div");

        if (container.getAttribute("class").contains("collapsed")) {
            header.click();
        }

        // ✅ ВАЖНО: дождаться загрузки внутренностей
        container.shouldBe(Condition.visible);

        return this;
    }

    @Step("Открыть деталь '{detailName}'")
    public DetailPage openDetailByName4(String detailName) {

        // 🔥 ищем caption (родителя, а не span)
        SelenideElement caption = $x(
                "//div[@data-item-marker='tab-content-container-marker' and not(contains(@style,'display: none'))]" +
                        "//span[normalize-space()='" + detailName + "']/ancestor::div[contains(@class,'ts-controlgroup-caption-wrap')]"
        ).shouldBe(Condition.visible);

        // 🔥 кликаем по caption (НЕ по span!)
        caption.scrollIntoView(true);
        Selenide.executeJavaScript("arguments[0].click();", caption);

        // 🔥 ждём раскрытие (появление кнопки "Файлы")
        caption.closest("div")
                .$x(".//span[contains(text(),'Файлы')]")
                .shouldBe(Condition.visible, Duration.ofSeconds(10));

        return this;
    }


    public DetailPage openDetailByName(String tabId, String detailName) {

        SelenideElement caption = $x(
                "//div[@id='" + tabId + "' and not(contains(@style,'display: none'))]" +
                        "//span[normalize-space()='" + detailName + "']" +
                        "/ancestor::div[contains(@class,'ts-controlgroup-caption-wrap')]"
        ).shouldBe(Condition.visible, Duration.ofSeconds(15));

        Selenide.executeJavaScript("arguments[0].click();", caption);

        caption.closest("div")
                .$x(".//span[contains(text(),'Файлы')]")
                .shouldBe(Condition.visible);

        return this;
    }

    @Step("Нажать '+' (Добавить запись) в detail '{detailName}'")
    public void clickAddRecordInDetail(String detailName) {

        // 1️⃣ Находим detail по data-item-marker
        SelenideElement detail = $x(
                "//div[contains(@class,'detail') and @data-item-marker='" + detailName + "']"
        ).shouldBe(Condition.visible)
                .scrollIntoView(true);

        // 2️⃣ Внутри него находим кнопку "+"
        SelenideElement addButton = detail.$x(
                ".//span[@data-item-marker='AddRecordButton']"
        ).shouldBe(Condition.visible, Condition.enabled);

        // 3️⃣ JS click — стандарт для Creatio
        executeJavaScript("arguments[0].click();", addButton);
    }

    @Step("Открыть деталь '{name}' (stable)")
    public void openDetailStable1(String name) {

        SelenideElement detail = $x("//span[normalize-space()='" + name + "']");

        detail.scrollIntoView(true)
                .shouldBe(Condition.visible)
                .hover()
                .click();

        // ждём пока появится AddRecordButton внутри этой детали
        $$("input[data-item-marker='AddRecordButton']")
                .shouldBe(CollectionCondition.sizeGreaterThan(0));
    }

    @Step("Открыть деталь '{name}' (stable)")
    public void openDetailStable(String name) {

        // Берём только видимый span (игнорируем скрытые вкладки)
        SelenideElement detail = $$x("//span[normalize-space()='" + name + "']")
                .filter(Condition.visible)
                .first();

        detail.shouldBe(Condition.visible, Duration.ofSeconds(30))
                .scrollIntoView(true)
                .hover()
                .click();

        $$("input[data-item-marker='AddRecordButton']")
                .shouldBe(CollectionCondition.sizeGreaterThan(0));
    }




}




