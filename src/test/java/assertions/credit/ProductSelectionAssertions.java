package assertions.credit;

import core.base.TestContext;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$x;

public class ProductSelectionAssertions {

    private final TestContext ctx;

    public ProductSelectionAssertions(TestContext ctx) {
        this.ctx = ctx;
    }

    @Step("Проверить, что продукт выбран")
    public void productShouldBeSelected() {
        $x("//span[text()='Выбрать']")
                .should(disappear);
    }
}
