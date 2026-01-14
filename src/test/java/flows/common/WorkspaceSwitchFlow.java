package flows.common;

import core.base.TestContext;
import io.qameta.allure.Step;

/**
 * Infrastructure flow:
 * отвечает за выбор и открытие рабочего места (workspace)
 */
public class WorkspaceSwitchFlow {

    private final TestContext ctx;

    public WorkspaceSwitchFlow(TestContext ctx) {
        this.ctx = ctx;
    }

    @Step("Выбрать рабочее место: {workspaceName}")
    public void switchTo(String workspaceName) {

        ctx.workspaceSteps
                .selectWorkAccess(workspaceName);

        // кнопка "Открыть рабочее место"
        ctx.basePage
                .clickButtonById("view-button-OBSW-imageEl");

        // минимальная защита от race condition
        ctx.basePage
                .waitForPage();
    }
}
