package flows.credit;

import com.codeborne.selenide.Selenide;
import core.base.UiContext;
import flows.common.ApplicationSearchFlow;
import io.qameta.allure.Step;

public class ReviewStageUnderwriterFlow {


    private final UiContext ui;
    private final ApplicationSearchFlow applicationSearchFlow;

    public ReviewStageUnderwriterFlow(UiContext ui) {
        this.ui = ui;

        this.applicationSearchFlow = new ApplicationSearchFlow(ui);
    }

    // =====================================================
    // 🧑‍💼 UNDERWRITER REVIEW
    // =====================================================

    @Step("Review: Underwriter утверждает решение по заявке")
    public void approveReview(String decisionProjectName) {

        ui.basePage.closeConsultationPanelIfOpened();

        // 1️. Открываем заявку по сохранённому номеру
        applicationSearchFlow.openBySavedNumber();

        // 2️. Переходим во вкладку «Решение по заявке»
        openDecisionTab();

        // 3️. Открываем проект решения
        openDecisionProject(decisionProjectName);

        // 4. Взять в работу
        TakeToWork();

        // 5. Утверждаем решение
        approveDecision();
    }

    // =====================================================
    // INTERNAL STEPS
    // =====================================================

    private void openDecisionTab() {
        ui.contractPage
                .scrollTabsRight();

        ui.buttonsComponent
                .clickButtonByContainNameCheck("Решение по заявке");
    }

    private void openDecisionProject(String decisionProjectName) {
        ui.projectsPage
                .openProjectByName(decisionProjectName);
    }

    private void TakeToWork() {
        ui.basePage
                .waitAndClickByDIM("TakeToWorkButton");

        Selenide.sleep (3000);

        ui.buttonsComponent
                .clickButtonByContainNameCheck("Решение");
        ui.lookupComponent
                .setHandBookFieldByValueCheck("Вид кредита для Проекта решения", "Кредит");

    }


    private void approveDecision() {

        ui.basePage
                .waitAndClickByMarkerNew("ApproveButton");
    }
}
