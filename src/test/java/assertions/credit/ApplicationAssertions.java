package assertions.credit;

import core.base.UiContext;
import io.qameta.allure.Step;

public class ApplicationAssertions {

    private final UiContext ctx;

    public ApplicationAssertions(UiContext ctx) {
        this.ctx = ctx;
    }

    @Step("Проверить, что заявка успешно создана")
    public void applicationShouldBeCreated() {
        ctx.messageBoxComponent
                .shouldSeeModalWithText("Нет задолженности!");
    }
}
