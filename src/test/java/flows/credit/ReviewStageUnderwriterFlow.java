package flows.credit;

import core.base.UiContext;
import flows.common.ApplicationSearchFlow;
import io.qameta.allure.Step;

public class ReviewStageUnderwriterFlow {


    private final UiContext ctx;
    private final ApplicationSearchFlow applicationSearchFlow;

    public ReviewStageUnderwriterFlow(UiContext ctx) {
        this.ctx = ctx;

        this.applicationSearchFlow = new ApplicationSearchFlow(ctx);
    }

    // =====================================================
    // 🧑‍💼 UNDERWRITER REVIEW
    // =====================================================

    @Step("Review: Underwriter утверждает решение по заявке")
    public void approveReview(String decisionProjectName) {

        // 1️⃣ Открываем заявку по сохранённому номеру
        applicationSearchFlow.openBySavedNumber();

        // 2️⃣ Переходим во вкладку «Решение по заявке»
        openDecisionTab();

        // 3️⃣ Открываем проект решения
        openDecisionProject(decisionProjectName);

        // 4️⃣ Утверждаем решение
        approveDecision();
    }

    // =====================================================
    // INTERNAL STEPS
    // =====================================================

    private void openDecisionTab() {
        ctx.contractPage
                .scrollTabsRight();

        ctx.buttonsComponent
                .clickButtonByContainNameCheck("Решение по заявке");
    }

    private void openDecisionProject(String decisionProjectName) {
        ctx.projectsPage
                .openProjectByName(decisionProjectName);
    }

    private void approveDecision() {
        ctx.basePage
                .waitAndClickByDIM("TakeToWorkButton");

        ctx.basePage
                .waitAndClickByMarkerNew("ApproveButton");
    }
}
