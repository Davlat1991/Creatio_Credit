package core.pages.credit;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import core.base.BasePage;
import core.base.common.components.FieldComponent;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Condition.*;

public class SimpleRoutePage extends BasePage {

    private final SelenideElement title =
            $x("//span[contains(@class,'t-title') or contains(text(),'Заявка')]");

    private final SelenideElement saveButton =
            $x("//span[@data-item-marker='SaveButton' or text()='Сохранить']");

    private final SelenideElement statusField =
            $x("//span[contains(@data-item-marker,'Status')]");

    // Используем ТВОЙ компонент
    private final FieldComponent fields = new FieldComponent();

    @Step("Ожидать открытие упрощённого маршрута")
    public SimpleRoutePage waitOpened() {
        title.shouldBe(visible);
        return this;
    }

    @Step("Заполнить обязательные поля упрощённого маршрута")
    public SimpleRoutePage fillRequiredFields(String fullName, String phone) {

        fields.setValue("ФИО", fullName);
        fields.setValue("Телефон", phone);

        return this;
    }

    @Step("Сохранить заявку")
    public SimpleRoutePage save() {
        saveButton.shouldBe(visible, enabled).click();
        waitForLoader();
        return this;
    }

    @Step("Проверить статус заявки: {expectedStatus}")
    public SimpleRoutePage verifyStatus(String expectedStatus) {

        statusField.shouldBe(visible)
                .shouldHave(text(expectedStatus));

        return this;
    }

    private final SelenideElement errorMessage =
            $x("//div[contains(@class,'validation') or contains(@class,'message')]");

    @Step("Проверить сообщение об ошибке: {expected}")
    public SimpleRoutePage shouldHaveError(String expected) {
        errorMessage.shouldBe(visible).shouldHave(text(expected));
        return this;
    }

}
