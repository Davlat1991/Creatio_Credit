package flows.credit;



import core.base.UiContext;
import flows.common.ApplicationSearchFlow;
import io.qameta.allure.Step;

/**
 * Stage Flow:
 * Этап подписания договора и проведения кассовых операций
 *
 * Ответственность:
 * - открыть договор по сохранённому номеру
 * - обработать приходный кассовый ордер (комиссия)
 * - обработать расходный кассовый ордер (выдача кредита)
 */
public class SigningStageFlow {

    private final UiContext ui;
    private final ApplicationSearchFlow applicationSearchFlow;

    public SigningStageFlow(UiContext ui) {
        this.ui = ui;
        this.applicationSearchFlow = new ApplicationSearchFlow(ui);
    }

    @Step("Этап подписания и кассовых операций по договору")
    public void completeSigningStage() {

        ui.basePage.closeConsultationPanelIfOpened();

        // 1. Открыть заявку/договор по сохранённому номеру
        applicationSearchFlow.openBySavedСontracts();

        // 2. Перейти на вкладку "Операции по договору"
        ui.buttonsComponent
                .clickButtonByContainNameCheck("Операции по договору");

        // 3. Приходный кассовый ордер (комиссия)
        processIncomingOrder();

        // 4. Расходный кассовый ордер (выдача кредита)
        processOutgoingOrder();
    }

    // ======================================================================
    // 💰 Приходный кассовый ордер (Комиссия)
    // ======================================================================

    @Step("Подтверждение приходного кассового ордера (комиссия)")
    private void processIncomingOrder() {

        ui.buttonsComponent
                .doubleclickButtonByName("Приходный");

        ui.basePage
                .checkCurrentPage("BnzOrderPageContainer");

        ui.creditApplicationAssertions
                .assertOrderState("Новый");

        ui.fieldAssertions
                .checkFieldValueNormalized(
                        "Сумма документа",
                        "200,00"
                );

        ui.basePage
                .clickButtonByNameCheck("Подтвердить");

        ui.messageBoxComponent
                .shouldSeeModalWithText(
                        "Кассовый ордер успешно подтвержден"
                );

        ui.basePage
                .clickButtonByNameCheck("ОК");

        ui.creditApplicationAssertions
                .assertOrderState("Подтвержден");

        ui.printComponent
                .selectPrintOption(
                        "Приходный кассовый ордер по комиссии"
                );

        ui.basePage
                .clickButtonByNameCheck("Закрыть");
    }

    // ======================================================================
    // 💸 Расходный кассовый ордер (Выдача кредита)
    // ======================================================================

    @Step("Подтверждение расходного кассового ордера (выдача кредита)")
    private void processOutgoingOrder() {

        ui.buttonsComponent
                .doubleclickButtonByName("Расходный");

        ui.basePage
                .checkCurrentPage("BnzOrderPageContainer");

        ui.creditApplicationAssertions
                .assertOrderState("Новый");

        ui.fieldAssertions
                .checkFieldValueNormalized(
                        "Сумма документа",
                        "50 000,00"
                );

        ui.basePage
                .clickButtonByNameCheck("Подтвердить");

        ui.messageBoxComponent
                .shouldSeeModalWithText(
                        "Кассовый ордер успешно подтвержден"
                );

        ui.basePage
                .clickButtonByNameCheck("ОК");

        ui.messageBoxComponent
                .shouldSeeModalWithText(
                        "Получение фактических операций клиента по договору успешно выполнено!"
                );

        ui.basePage
                .clickButtonByNameCheck("ОК");



        ui.creditApplicationAssertions
                .assertOrderState("Подтвержден");

        ui.printComponent
                .selectPrintOption(
                        "Расходный кассовый ордер"
                );

        ui.basePage
                .clickButtonByNameCheck("Закрыть");
    }
}

