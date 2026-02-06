package flows.credit;


import core.base.UiContext;
import flows.common.ApplicationSearchFlow;
import io.qameta.allure.Step;

/**
 * Stage Flow:
 * Завершение выдачи кредита
 *
 * Проверяет:
 * - наличие и состояние приходного и расходного ордеров
 * Выполняет:
 * - завершение консультации / выдачи кредита
 */
public class ApplicationFinishFlow {

    private final UiContext ui;
    private final ApplicationSearchFlow applicationSearchFlow;

    public ApplicationFinishFlow(UiContext ui) {
        this.ui = ui;
        this.applicationSearchFlow = new ApplicationSearchFlow(ui);
    }

    @Step("Этап завершения выдачи кредита")
    public void completeApplicationFinish() {

        ui.basePage.closeConsultationPanelIfOpened();

        // 1. Открыть договор по сохранённому номеру
        applicationSearchFlow.openBySavedСontracts();

        // 2. Перейти на вкладку "Операции по договору"
        ui.buttonsComponent
                .clickButtonByContainNameCheck("Операции по договору");

        // 3. Проверка приходного ордера
        checkConfirmedIncomingOrder();

        // 4. Проверка расходного ордера
        checkConfirmedOutgoingOrder();

        // 5. Завершить выдачу кредита
        finishCreditIssuance();
    }

    // ======================================================================
    // 💰 Проверка приходного ордера
    // ======================================================================

    @Step("Проверка подтверждённого приходного кассового ордера")
    private void checkConfirmedIncomingOrder() {

        ui.buttonsComponent
                .doubleclickButtonByName("Приходный");

        ui.basePage
                .checkCurrentPage("BnzOrderPageContainer");

        ui.creditApplicationAssertions
                .assertOrderState("Подтвержден");

        ui.basePage
                .clickButtonByNameCheck("Закрыть");
    }

    // ======================================================================
    // 💸 Проверка расходного ордера
    // ======================================================================

    @Step("Проверка подтверждённого расходного кассового ордера")
    private void checkConfirmedOutgoingOrder() {

        ui.buttonsComponent
                .doubleclickButtonByName("Расходный");

        ui.basePage
                .checkCurrentPage("BnzOrderPageContainer");

        ui.creditApplicationAssertions
                .assertOrderState("Подтвержден");

        ui.basePage
                .clickButtonByNameCheck("Закрыть");
    }

    // ======================================================================
    // ✅ Завершение выдачи кредита
    // ======================================================================

    @Step("Завершение выдачи кредита через консультационную панель")
    private void finishCreditIssuance() {

        ui.basePage.ensureConsultationPanelOpened();

        ui.contractPage
                .completeConsultation();
    }
}

