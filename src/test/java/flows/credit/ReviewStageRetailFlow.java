package flows.credit;

import com.codeborne.selenide.Selenide;
import core.base.UiContext;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.refresh;

public class ReviewStageRetailFlow {

    private final UiContext ctx;

    public ReviewStageRetailFlow(UiContext ctx) {
        this.ctx = ctx;
    }

    // =====================================================
    // 🧑‍💼 RETAIL MANAGER — REVIEW STAGE
    // =====================================================

    @Step("Review: Retail Manager завершает документы и ожидает решение")
    public void completeReview() {

        openFinishDocumentsMiniPage();
        completeDocumentsTask();
        openChecksTab();

        scrollDownSmall();
        verifyCreditDecisionApproved();
        waitForDecisionCalculation();
        refreshAndResetView();

        openDecisionTab();
        verifyCommittee();
        saveApplicationNumber();

    }

    // =====================================================
    // INTERNAL STEPS
    // =====================================================

    private void openFinishDocumentsMiniPage() {
        ctx.dashboardComponent.clickElementDashboardCheck(
                "Вложить документы и отправить на рассмотрение",
                "Execute",
                "//*[@data-item-marker='MiniPage']"
        );
    }

    private void completeDocumentsTask() {
        ctx.contractPage
                .setfieldScheduleDetailByDIM("Result", "Выполнена");
        ctx.menuComponent
                .clickButtonByLiName("Выполнена");
        ctx.basePage
                .clickButtonByDataItemMaker("SaveEditButton");
    }



    private void openChecksTab() {
        ctx.buttonsComponent
                .clickButtonByContainNameCheck("Проверки");
    }


     private void scrollDownSmall() {
        ctx.basePage
                .scrollDownSmall();
    }


    private void verifyCreditDecisionApproved() {
        ctx.gridAssertions.waitForCreditDecision("Одобрить");

    }

    private void waitForDecisionCalculation() {
        // 🔥 Обоснованный workaround для асинхронного маршрута Creatio
        Selenide.sleep(15000);
    }


    // 🔥 КЛЮЧЕВОЙ МЕТОД
    private void refreshAndResetView() {

        refresh();
    }


    private void openDecisionTab() {

        ctx.contractPage
                .scrollTabsRight();

        ctx.buttonsComponent
                .clickButtonByContainNameCheck("Решение по заявке");
    }

    private void verifyCommittee() {
        ctx.gridAssertions
                .waitForValueInGridColumn("Комитет", "КК4");
    }

    private void saveApplicationNumber() {
        ctx.contractPage
                .saveValueByMarker("Number");
    }



}
