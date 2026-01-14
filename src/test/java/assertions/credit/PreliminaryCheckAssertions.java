package assertions.credit;

import core.base.TestContext;
import io.qameta.allure.Step;

public class PreliminaryCheckAssertions {

    private final TestContext ctx;

    public PreliminaryCheckAssertions(TestContext ctx) {
        this.ctx = ctx;
    }

    @Step("Проверить, что предварительная проверка завершена")
    public void preliminaryCheckShouldBeCompleted() {
        ctx.messageBoxComponent
                .shouldSeeModalWithText("У клиента нет просроченных дней");
    }
}
