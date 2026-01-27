package assertions.credit;

import core.base.UiContext;
import io.qameta.allure.Step;

public class PreliminaryCheckAssertions {

    private final UiContext ctx;

    public PreliminaryCheckAssertions(UiContext ctx) {
        this.ctx = ctx;
    }

    @Step("Проверить, что предварительная проверка завершена")
    public void preliminaryCheckShouldBeCompleted() {
        ctx.messageBoxComponent
                .shouldSeeModalWithText("У клиента нет просроченных дней");
    }
}
