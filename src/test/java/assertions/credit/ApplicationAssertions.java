package assertions.credit;

import core.base.TestContext;
import io.qameta.allure.Step;

public class ApplicationAssertions {

    private final TestContext ctx;

    public ApplicationAssertions(TestContext ctx) {
        this.ctx = ctx;
    }

    @Step("Проверить, что заявка успешно создана")
    public void applicationShouldBeCreated() {
        ctx.messageBoxComponent
                .shouldSeeModalWithText("Нет задолженности!");
    }
}
