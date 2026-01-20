package flows.credit;

import core.base.TestContext;
import io.qameta.allure.Step;

public class PreliminaryCheckStageFlow {

    private final TestContext ctx;

    public PreliminaryCheckStageFlow(TestContext ctx) {
        this.ctx = ctx;
    }

    // =====================================================
    // 🔍 PRELIMINARY CHECK STAGE
    // =====================================================

    @Step("Preliminary Check: выполнение всех проверок")
    public void completePreliminaryCheckStage() {

        completeParticipantsQuestionnaire();
        completeClientCheck();
    }

    // =====================================================
    // TASK 1: QUESTIONNAIRES
    // =====================================================

    private void completeParticipantsQuestionnaire() {

        openParticipantsQuestionnaireTask();
        markTaskAsCompleted();
        confirmNoDebtMessages();
    }

    private void openParticipantsQuestionnaireTask() {
        ctx.dashboardComponent.clickElementDashboardCheck(
                "Добавьте и заполните анкеты участников заявки",
                "Execute",
                "//*[@data-item-marker='MiniPage']"
        );
    }

    private void markTaskAsCompleted() {
        ctx.contractPage.setfieldScheduleDetailByDIM("Result", "Выполнена");
        ctx.menuComponent.clickButtonByLiName("Выполнена");
        ctx.basePage.clickButtonByDataItemMakerCheck("SaveEditButton");
    }

    private void confirmNoDebtMessages() {
        ctx.messageBoxComponent.shouldSeeModalWithText("Нет задолженности!");
        ctx.basePage.clickButtonByDataItemMaker("ОК");

        ctx.messageBoxComponent.shouldSeeModalWithText("У клиента нет просроченных дней");
        ctx.basePage.clickButtonByDataItemMaker("ОК");
    }

    // =====================================================
    // TASK 2: CLIENT CHECK
    // =====================================================

    private void completeClientCheck() {

        openClientCheckTask();
        approveClientCheck();
        completeCollateralAndGuaranteeTask();
    }

    private void openClientCheckTask() {
        ctx.dashboardComponent.clickElementDashboardWait(
                "Проверка клиента",
                "Approve"
        );
    }

    private void approveClientCheck() {
        ctx.basePage.clickButtonByDataItemMaker("SaveEditButton");
    }

    private void completeCollateralAndGuaranteeTask() {

        ctx.dashboardComponent.clickElementDashboardCheck(
                "Заполните данные обеспечения и поручительства",
                "Execute",
                "//*[@data-item-marker='MiniPage']"
        );

        ctx.contractPage.setfieldScheduleDetailByDIM("ProcessResult", "Выполнена");
        ctx.menuComponent.clickButtonByLiName("Выполнена");
        ctx.basePage.clickButtonByDataItemMaker("SaveEditButton");
    }
}
