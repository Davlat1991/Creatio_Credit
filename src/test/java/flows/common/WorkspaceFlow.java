package flows.common;

import com.codeborne.selenide.Selenide;
import core.base.UiContext;
import core.enums.Workspace;
import io.qameta.allure.Step;

/**
 * Infrastructure flow:
 * отвечает ТОЛЬКО за выбор и открытие рабочего места
 */
public class WorkspaceFlow {

    private final UiContext ui;

    public WorkspaceFlow(UiContext ui) {
        this.ui = ui;
    }

    // ================= PUBLIC API =================

    @Step("Выбор рабочего места: {workspace}")
    public void select(Workspace workspace) {
        switchTo(workspace.getUiName());
    }

    // ================= INTERNAL =================

    private void switchTo(String workspaceName) {
        ui.workspaceSteps.selectWorkAccess(workspaceName);
        ui.basePage.waitForPage();
        ui.basePage.ensureConsultationPanelOpened();


        //ui.basePage.clickButtonById("view-button-OBSW-imageEl"); // 245  // view-button-OBSW-imageEl 254

    }
}
