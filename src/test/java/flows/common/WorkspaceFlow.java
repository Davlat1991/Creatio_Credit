package flows.common;

import core.base.TestContext;
import core.enums.Workspace;
import io.qameta.allure.Step;

/**
 * Infrastructure flow:
 * отвечает ТОЛЬКО за выбор и открытие рабочего места
 */
public class WorkspaceFlow {

    private final TestContext ctx;

    public WorkspaceFlow(TestContext ctx) {
        this.ctx = ctx;
    }

    // ================= PUBLIC API =================

    @Step("Выбор рабочего места: {workspace}")
    public void select(Workspace workspace) {
        switchTo(workspace.getUiName());
    }

    // ================= INTERNAL =================

    private void switchTo(String workspaceName) {
        ctx.workspaceSteps.selectWorkAccess(workspaceName);
        ctx.basePage.clickButtonById("view-button-OBSW-imageEl");
        ctx.basePage.waitForPage();
    }
}
