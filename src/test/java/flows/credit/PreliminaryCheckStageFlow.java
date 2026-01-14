package flows.credit;

import core.base.TestContext;
import io.qameta.allure.Step;

public class PreliminaryCheckStageFlow {

    private final TestContext ctx;

    public PreliminaryCheckStageFlow(TestContext ctx) {
        this.ctx = ctx;
    }

    @Step("Стадия: Предварительная проверка")
    public void completePreliminaryCheckStage() {

        completeParticipantsQuestionnaireTask();
        completeClientCheckTask();
    }

    // ----------------------------------------------------------------
    // 1. Добавьте и заполните анкеты участников заявки
    // ----------------------------------------------------------------

    private void completeParticipantsQuestionnaireTask() {

        ctx.dashboardComponent
                .clickElementDashboardCheck(
                        "Добавьте и заполните анкеты участников заявки",
                        "Execute",
                        "//*[@data-item-marker='MiniPage']"
                );

        ctx.contractPage
                .setfieldScheduleDetailByDIM("Result", "Выполнена");

        ctx.menuComponent
                .clickButtonByLiName("Выполнена");

        ctx.basePage
                .clickButtonByDataItemMakerCheck("SaveEditButton");

        ctx.messageBoxComponent
                .shouldSeeModalWithText("Нет задолженности!");

        ctx.basePage
                .clickButtonByDataItemMaker("ОК");

        ctx.messageBoxComponent
                .shouldSeeModalWithText("У клиента нет просроченных дней");

        ctx.basePage
                .clickButtonByDataItemMaker("ОК");
    }

    // ----------------------------------------------------------------
    // 2. Проверка клиента
    // ----------------------------------------------------------------

    private void completeClientCheckTask() {

        ctx.dashboardComponent
                .clickElementDashboardWait("Проверка клиента", "Approve");

        ctx.basePage
                .clickButtonByDataItemMaker("SaveEditButton");

        ctx.dashboardComponent
                .clickElementDashboardCheck(
                        "Заполните данные обеспечения и поручительства",
                        "Execute",
                        "//*[@data-item-marker='MiniPage']"
                );

        ctx.contractPage
                .setfieldScheduleDetailByDIM("ProcessResult", "Выполнена");

        ctx.menuComponent
                .clickButtonByLiName("Выполнена");

        ctx.basePage
                .clickButtonByDataItemMaker("SaveEditButton");
    }
}
