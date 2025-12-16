package core.pages.routes;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class StandardRoutePage {

    private static final String PAGE_MARKER =
            "//*[contains(text(),'Стандартный кредитный маршрут')]";

    private final SelenideElement amountField =
            $x("//input[@data-item-marker='Amount']");

    private final SelenideElement termField =
            $x("//input[@data-item-marker='Term']");

    private final SelenideElement saveButton =
            $x("//button[@data-item-marker='SaveButton']");

    private final SelenideElement submitButton =
            $x("//button[contains(.,'Отправить на рассмотрение')]");

    private final SelenideElement approveButton =
            $x("//button[contains(.,'Одобрить')]");

    private final SelenideElement declineButton =
            $x("//button[contains(.,'Отказать')]");


    // ---------------------------------------------------------------
    // WAIT UNTIL PAGE LOAD
    // ---------------------------------------------------------------
    @Step("Ожидание загрузки страницы стандартного маршрута")
    public StandardRoutePage waitOpened() {
        $x(PAGE_MARKER).shouldBe(visible);
        return this;
    }


    // ---------------------------------------------------------------
    // BASIC FIELDS
    // ---------------------------------------------------------------
    @Step("Заполнить сумму '{amount}' и срок '{term}'")
    public StandardRoutePage fillBasicFields(String amount, String term) {
        amountField.shouldBe(visible).setValue(amount);
        termField.shouldBe(visible).setValue(term);
        return this;
    }


    // ---------------------------------------------------------------
    // SAVE / SEND
    // ---------------------------------------------------------------
    @Step("Сохранить заявку")
    public StandardRoutePage save() {
        saveButton.shouldBe(enabled).click();
        return this;
    }

    @Step("Отправить заявку на рассмотрение")
    public StandardRoutePage sendToReview() {
        submitButton.shouldBe(enabled).click();
        return this;
    }


    // ---------------------------------------------------------------
    // DECISION: APPROVE / DECLINE
    // ---------------------------------------------------------------
    @Step("Принять решение: ОДОБРЕНО")
    public StandardRoutePage approve() {

        approveButton.shouldBe(visible).click();

        // Creatio открывает мини-страницу подтверждения
        SelenideElement miniPage = $x("//*[@data-item-marker='MiniPage']")
                .shouldBe(visible);

        miniPage.$x(".//span[contains(text(),'Выполнить')]")
                .shouldBe(enabled)
                .click();

        return this;
    }

    @Step("Принять решение: ОТКАЗАНО")
    public StandardRoutePage decline() {

        declineButton.shouldBe(visible).click();

        SelenideElement miniPage = $x("//*[@data-item-marker='MiniPage']")
                .shouldBe(visible);

        // Причина отказа (если требуется)
        miniPage.$x(".//textarea|.//input")
                .setValue("Автотест — отказ");

        miniPage.$x(".//span[contains(text(),'Выполнить')]")
                .shouldBe(enabled)
                .click();

        return this;
    }


    // ---------------------------------------------------------------
    // STATUS
    // ---------------------------------------------------------------
    @Step("Проверить статус заявки = '{expected}'")
    public StandardRoutePage verifyStatus(String expected) {
        $x("//*[contains(@class,'status') and contains(text(),'" + expected + "')]")
                .shouldBe(visible);
        return this;
    }
}
