package assertions.credit;

import core.base.UiContext;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$x;

public class ProductSelectionAssertions {

    private final UiContext ctx;

    public ProductSelectionAssertions(UiContext ctx) {
        this.ctx = ctx;
    }

    @Step("Проверить, что продукт выбран")
    public void productShouldBeSelected() {
        $x("//span[text()='Выбрать']")
                .should(disappear);
    }
}
