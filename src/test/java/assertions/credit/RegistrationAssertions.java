package assertions.credit;

import core.base.UiContext;
import io.qameta.allure.Step;

public class RegistrationAssertions {

    private final UiContext ctx;

    public RegistrationAssertions(UiContext ctx) {
        this.ctx = ctx;
    }

    @Step("Проверить, что регистрация завершена")
    public void registrationStageShouldBeCompleted() {
        ctx.basePage.checkCurrentPage("EntityLoaded");
    }
}
