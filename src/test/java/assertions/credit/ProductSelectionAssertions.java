package assertions.credit;

import core.base.UiContext;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$x;

public class ProductSelectionAssertions {

    private final UiContext ui;

    public ProductSelectionAssertions(UiContext ui) {
        this.ui = ui;
    }

    @Step("Проверить, что продукт выбран")
    public void productShouldBeSelected() {
        $x("//span[text()='Выбрать']")
                .should(disappear);
    }
}
