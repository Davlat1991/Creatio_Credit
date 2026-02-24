package flows.credit;

import core.base.UiContext;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.refresh;


public class ReviewStageRetailFlow {



    private final UiContext ui;

    public ReviewStageRetailFlow(UiContext ui) {
        this.ui = ui;
    }

    // =====================================================
    // 🧑‍💼 RETAIL MANAGER — REVIEW STAGE
    // =====================================================

    @Step("Review: Retail Manager завершает документы и ожидает решение")
    public void completeReview() {

        openChecksTab();
        verifyCreditDecisionApproved();

        refreshAndResetView();

        openDecisionTab();
        verifyCommittee();
        saveApplicationNumber();

    }

    // =====================================================
    // INTERNAL STEPS
    // =====================================================


    public void openChecksTab() {
        ui.buttonsComponent
                .clickButtonByContainNameCheck("Проверки");


    }

    //Новый метод нужно протестировать
    private void verifyCreditDecisionApproved() {
        ui.gridAssertions.waitForAnyCreditDecision(); //Одобрить Отказать

    }


    // 🔥 КЛЮЧЕВОЙ МЕТОД
    private void refreshAndResetView() {

        refresh();
    }


    private void openDecisionTab() {

        ui.contractPage
                .scrollTabsRight();


        ui.buttonsComponent
                .clickButtonByContainNameCheck("Решение по заявке");
    }

    private void verifyCommittee() {
        ui.gridAssertions
                .waitForValueInGridColumn("Комитет", "КК4");
    }

    private void saveApplicationNumber() {
        ui.contractPage
                .saveValueByMarker("Number");
    }




}
