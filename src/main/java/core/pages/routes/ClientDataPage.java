package core.pages.routes;


import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class ClientDataPage {

    private final SelenideElement fioField =
            $x("//input[@data-item-marker='ClientName']");

    private final SelenideElement passportField =
            $x("//input[@data-item-marker='PassportNumber']");

    private final SelenideElement nextButton =
            $x("//button[contains(.,'Далее')]");

    @Step("Заполнить данные клиента: ФИО '{fio}', паспорт '{passport}'")
    public ClientDataPage fillClientData(String fio, String passport) {
        fioField.shouldBe(visible).setValue(fio);
        passportField.shouldBe(visible).setValue(passport);
        return this;
    }

    @Step("Перейти к следующему шагу")
    public StandardRoutePage next() {
        nextButton.shouldBe(enabled).click();
        return new StandardRoutePage().waitOpened();
    }
}
