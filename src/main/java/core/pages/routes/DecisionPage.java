package core.pages.routes;


import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class DecisionPage {

    @Step("Ожидание открытия страницы решения")
    public DecisionPage waitOpened() {
        $x("//*[contains(.,'Решение по заявке')]")
                .shouldBe(visible);
        return this;
    }

    @Step("Одобрить заявку")
    public DecisionPage approve() {
        $x("//button[contains(.,'Одобрить')]")
                .shouldBe(enabled).click();
        return this;
    }

    @Step("Отказать по заявке")
    public DecisionPage decline() {
        $x("//button[contains(.,'Отказать')]")
                .shouldBe(enabled).click();
        return this;
    }
}
