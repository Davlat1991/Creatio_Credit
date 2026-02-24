package flows.credit;

import core.base.UiContext;
import io.qameta.allure.Step;

public class PreliminaryCheckStageFlow {

    private final UiContext ui;

    public PreliminaryCheckStageFlow(UiContext ui) {
        this.ui = ui;
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
        ui.dashboardComponent.clickElementDashboardCheck(
                "Добавьте и заполните анкеты участников заявки",
                "Execute",
                "//*[@data-item-marker='MiniPage']"
        );
    }

    private void markTaskAsCompleted() {
        ui.contractPage.setfieldScheduleDetailByDIM("Result", "Выполнена");
        ui.menuComponent.clickButtonByLiName("Выполнена");
        ui.basePage.clickButtonByDataItemMakerCheck("SaveEditButton");
    }

    private void confirmNoDebtMessages() {
        ui.messageBoxComponent.shouldSeeModalWithText("Нет задолженности!");
        ui.basePage.clickButtonByDataItemMaker("ОК");

        ui.messageBoxComponent.shouldSeeModalWithText("У клиента нет просроченных дней");
        ui.basePage.clickButtonByDataItemMaker("ОК");
    }

    // =====================================================
    // TASK 2: CLIENT CHECK
    // =====================================================

    private void completeClientCheck() {

        openClientCheckTask();
        approveClientCheck();
        //completeCollateralAndGuaranteeTask();
    }

    private void openClientCheckTask() {
        ui.dashboardComponent.clickElementDashboardWait(
                "Проверка клиента",
                "Approve"
        );
    }

    private void approveClientCheck() {
        ui.basePage.clickButtonByDataItemMaker("SaveEditButton");
    }

    private void completeCollateralAndGuaranteeTask() {

        ui.dashboardComponent.clickElementDashboardCheck(
                "Заполните данные обеспечения и поручительства",
                "Execute",
                "//*[@data-item-marker='MiniPage']"
        );

        ui.contractPage.setfieldScheduleDetailByDIM("ProcessResult", "Выполнена");
        ui.menuComponent.clickButtonByLiName("Выполнена");
        ui.basePage.clickButtonByDataItemMaker("SaveEditButton");
    }
}
