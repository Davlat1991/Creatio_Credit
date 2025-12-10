package core.base.common.components;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Component: логика выдачи кредита (нал/перечислением и т.п.)
 */
public class CreditIssuingComponent extends Components {

    @Step("Открыть меню выдачи кредита и выбрать способ '{issueType}'")
    public CreditIssuingComponent selectIssueType(String issueType) {
        SelenideElement issueCredit = $x("//li[@data-item-marker='Выдача кредита']").shouldBe(Condition.visible);
        issueCredit.hover();
        SelenideElement method = $x("//li[@data-item-marker='" + issueType + "']").shouldBe(Condition.visible, Condition.enabled);
        retryClick(method, "Выбрать способ выдачи: " + issueType);
        // Ждём модалку
        $x("//*[@data-item-marker='Выдача кредита']").shouldBe(Condition.visible);
        return this;
    }

    @Step("Универсальная выдача кредита: {issueType}")
    public CreditIssuingComponent issueCredit(String issueType) {
        for (int i = 1; i <= 5; i++) {
            try {
                selectIssueType(issueType);
                $x("//*[@data-item-marker='Выдача кредита']").shouldBe(Condition.visible).shouldHave(Condition.text("Выдача кредита"));
                return this;
            } catch (Throwable t) {
                if (i == 5) throw t;
            }
        }
        return this;
    }
}

