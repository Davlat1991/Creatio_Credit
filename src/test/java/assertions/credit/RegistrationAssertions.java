package assertions.credit;

import core.base.TestContext;
import io.qameta.allure.Step;

public class RegistrationAssertions {

    private final TestContext ctx;

    public RegistrationAssertions(TestContext ctx) {
        this.ctx = ctx;
    }

    @Step("Проверить, что регистрация завершена")
    public void registrationStageShouldBeCompleted() {
        ctx.basePage.checkCurrentPage("EntityLoaded");
    }
}
