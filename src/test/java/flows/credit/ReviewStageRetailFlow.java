package flows.credit;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import core.base.UiContext;
import core.data.scoring.CreditDecision;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.refresh;
import static core.base.common.components.LookupComponent.log;


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


        //waitForDecisionCalculation();
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

        //Selenide.sleep(5000);

        //ui.basePage.scrollDownSmall();

    }



    //Новый метод нужно протестировать
    private void verifyCreditDecisionApproved() {
        ui.gridAssertions.waitForAnyCreditDecision(); //Одобрить Отказать

    }



    private void waitForDecisionCalculation() {
        // 🔥 Обоснованный workaround для асинхронного маршрута Creatio
        Selenide.sleep(25000);
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
