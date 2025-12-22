package steps.credit;

import core.pages.credit.ContractCreditApplicationPage;
import io.qameta.allure.Step;
import org.testng.Assert;

// src/test/java/steps/credit
public class CreditApplicationAssertions {

    private final ContractCreditApplicationPage page;

    public CreditApplicationAssertions(ContractCreditApplicationPage page) {
        this.page = page;
    }

    @Step("Проверить состояние ордера: {expected}")
    public void assertOrderState(String expected) {

        String actual = page.getOrderState();

        Assert.assertEquals(
                actual,
                expected,
                "Состояние ордера неверное"
        );
    }
}

