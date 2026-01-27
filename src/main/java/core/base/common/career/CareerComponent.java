package core.base.common.career;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.$$x;

public class CareerComponent {

    @Step("Выбрать значение '{value}' в поле '{field.marker}'")
    public CareerComponent setLookupByMarker(CareerField field, String value) {

        SelenideElement input = $x(
                "//*[@data-item-marker='" + field.marker + "']//input"
        ).shouldBe(Condition.visible, Condition.enabled);

        input.click();
        input.clear();
        input.sendKeys(value);

        SelenideElement listView = $$x("//div[contains(@class,'listview')]")
                .findBy(Condition.visible);

        SelenideElement item = listView
                .$x(".//li[normalize-space(.)='" + value + "']")
                .shouldBe(Condition.visible);

        item.click();

        // 🔥 soft-validation (если есть)
        SelenideElement confirm = input.closest(".base-edit")
                .$x(".//div[contains(@id,'soft-validation-confirm')]");

        if (confirm.exists() && confirm.isDisplayed()) {
            Selenide.executeJavaScript("arguments[0].click();", confirm);
        }

        input.shouldHave(Condition.exactValue(value));

        // небольшой буфер под server rebuild
        Selenide.sleep(200);

        return this;
    }

    @Step("Заполнить текстовое поле '{field.marker}' значением '{value}'")
    public CareerComponent setTextByMarker(CareerField field, String value) {

        SelenideElement input = $x(
                "//*[@data-item-marker='" + field.marker + "']//input"
        ).shouldBe(Condition.visible, Condition.enabled);

        input.click();
        input.clear();
        input.setValue(value)
                .shouldHave(Condition.value(value));

        return this;
    }
}


