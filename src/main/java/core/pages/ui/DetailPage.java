package core.pages.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$x;

public class DetailPage {

    /**
     * Надёжно открывает деталь по её заголовку.
     */
    public DetailPage openDetailByName(String detailName) {

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
}

