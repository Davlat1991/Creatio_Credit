package flows.common;

import core.base.UiContext;
import core.enums.Workspace;
import io.qameta.allure.Step;

/**
 * Infrastructure flow:
 * отвечает ТОЛЬКО за выбор и открытие рабочего места
 */
public class WorkspaceFlow {

    private final UiContext ctx;

    public WorkspaceFlow(UiContext ctx) {
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
        ctx.basePage.clickButtonByDataItemMaker("OBSW"); //view-button-OBSW-wrapperEl 245  // view-button-OBSW-imageEl 254
        ctx.basePage.waitForPage();
    }
}
