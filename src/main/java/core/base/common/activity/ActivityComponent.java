package core.base.common.activity;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import core.base.BasePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;


public class ActivityComponent extends BasePage {

    @Step("Выбрать значение '{value}' в поле '{field}'")
    public ActivityComponent select(ActivityField field, String value) {

        SelenideElement input = $x(
                "//div[@data-item-marker='" + field.marker + "']//input"
        ).shouldBe(Condition.visible);

        // ждём, что поле реально активно
        input.shouldBe(Condition.not(Condition.attribute("readonly")));

        input.click();
        input.clear();
        input.sendKeys(value);

        // listview
        SelenideElement listView = $$x("//div[contains(@class,'listview')]")
                .findBy(Condition.visible);

        SelenideElement item = listView
                .$x(".//li[normalize-space(.)='" + value + "']")
                .shouldBe(Condition.visible);

        item.click();

        // soft-validation
        SelenideElement confirm = input.closest(".base-edit")
                .$x(".//div[contains(@id,'soft-validation-confirm')]");

        if (confirm.exists() && confirm.isDisplayed()) {
            jsClick(confirm);
        }

        input.shouldHave(Condition.exactValue(value));

        waitNextFieldActivation(field);

        return this;
    }

    /**
     * Ждём, пока Creatio активирует следующий lookup
     */
    private void waitNextFieldActivation(ActivityField field) {

        ActivityField next = switch (field) {
            case SECTOR -> ActivityField.SEGMENT;
            case SEGMENT -> ActivityField.SUB_SEGMENT;
            default -> null;
        };

        if (next == null) {
            return;
        }

        SelenideElement nextInput = $x(
                "//div[@data-item-marker='" + next.marker + "']//input"
        );

        nextInput.shouldBe(Condition.visible)
                .shouldBe(Condition.not(Condition.attribute("readonly")));

        // server rebuild
        sleep(300);
    }
}

