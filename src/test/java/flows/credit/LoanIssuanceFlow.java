package flows.credit;

import core.base.UiContext;
import flows.common.ApplicationSearchFlow;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.refresh;

public class LoanIssuanceFlow {

    private static final String CONTRACT_PAGE_MARKER =
            "BnzContractCreditPageContainer";

    private final UiContext ui;
    private final ApplicationSearchFlow applicationSearchFlow;

    public LoanIssuanceFlow(UiContext ui) {
        this.ui = ui;
        this.applicationSearchFlow = new ApplicationSearchFlow(ui);
    }

    // ============================================================
    // 🚀 PUBLIC API
    // ============================================================

    @Step("Loan Issuance: полный сценарий выдачи кредита")
    public void issueLoan() {

        ui.basePage.closeConsultationPanelIfOpened();

        // 0️⃣ Открываем заявку по сохранённому номеру
        applicationSearchFlow.openBySavedNumber();

        // 1️⃣–5️⃣ Бизнес-процесс выдачи кредита
        completeKkDecisionCheck();
        createAbsContract();
        bindAccountsAndSchedule();
        signAndIssueCredit();
        verifyOrdersAndPrint();
    }

    // ============================================================
    // 1️⃣ ПРОВЕРКА РЕШЕНИЯ КК
    // ============================================================

    private void completeKkDecisionCheck() {

        ui.dashboardComponent
                .clickElementDashboardCheck(
                        "Проверка решения КК",
                        "Execute",
                        "//*[@data-item-marker='MiniPage']"
                )
        ;
        ui.lookupComponent
                .setfieldScheduleDetailByDIM("Result", "Выполнена")
        ;
        ui.menuComponent
                .clickButtonByLiName("Выполнена");

        ui.basePage
                .clickButtonByDataItemMaker("SaveEditButton");
    }

    // ============================================================
    // 2️⃣ СОЗДАНИЕ ДОГОВОРА В АБС
    // ============================================================

    private void createAbsContract() {

        ui.dashboardComponent
                .clickElementDashboardName(
                        "Создание договора в АБС (печать договоров для встречи)");
        ui.contractPage
                .clickContractAutoWait(CONTRACT_PAGE_MARKER);

        ui.basePage
                .clickButtonOnPageByName(CONTRACT_PAGE_MARKER, "Действия");

        ui.menuComponent
                .clickButtonByLiName("Создание договора");

        ui.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Вид планирования",
                        "Аннуитетный_005"
                );

        ui.contractPage
                .fillLoadCreditTypeSafely("Получает сегодня")
                .selectLoadCreditTypeNew("Получает сегодня");

        ui.basePage
                .clickButtonByName("Выбрать");

        ui.messageBoxComponent
                .shouldSeeModalWithText("Нет задолженности!");
        ui.basePage
                .clickButtonByNameCheck("ОК");

        ui.messageBoxComponent
                .shouldSeeModalWithText("Договор успешно создан");
        ui.basePage
                .clickButtonByNameCheck("ОК");

        ui.basePage
                .clickButtonById(
                        "BnzContractCreditPageBnzCreateSavingAcountContractButtonButton-imageEl"
                );

        ui.messageBoxComponent
                .shouldSeeModalWithText(
                        "Депозитный договор успешно создан в АБС"
                );

        ui.basePage
                .clickButtonByNameCheck("ОК");

        ui.fieldUtils
                .saveValueDIMCheckWorkNEW("BnzDepositBankAccount");
        ui.contractPage
                .clickButtonByNameCheck("Закрыть");

        refresh();
    }


    // ============================================================
    // 3️⃣ ПРИВЯЗКА СЧЁТОВ И ГРАФИКА
    // ============================================================

    private void bindAccountsAndSchedule() {

        ui.lookupComponent
                .clickSearchIconID(
                        "BnzContractCreditPageBnzCreditBankAccountLookupEdit"
                )
                .selectValueInLookupWorkNEW("searchEdit");

        ui.basePage
                .clickButtonByNameCheck("Сохранить")
                .clickButtonByNameCheck("Действия");

        ui.messageBoxComponent
                .clickAndCheckModal("Получение графика платежей")
                .shouldSeeModalWithText("График платежей успешно получен");
        ui.basePage
                .clickButtonByNameCheck("ОК")
                .clickButtonByNameCheck("Действия");

        ui.messageBoxComponent
                .clickAndCheckModal("Привязка счета к договору")
                .shouldSeeModalWithText("Счет успешно привязан к кредитному договору");

        ui.basePage
                .clickButtonByNameCheck("ОК");
    }

    // ============================================================
    // 4️⃣ ПОДПИСАНИЕ И ВЫДАЧА КРЕДИТА
    // ============================================================

    private void signAndIssueCredit() {

        ui.dashboardComponent
                .clickElementDashboardCheck(
                        "Создание договора в АБС (печать договоров для встречи)",
                        "Execute",
                        "//*[@data-item-marker='MiniPage']"
                );
        ui.contractPage
                .setfieldScheduleDetailByDIM("Result", "Выполнена");
        ui.menuComponent
                .clickButtonByLiName("Выполнена");

        ui.basePage
                .clickButtonByDataItemMaker("SaveEditButton")
                .clickButtonByNameCheck("Действия");

        ui.messageBoxComponent
                .clickAndCheckModal("Подписание договора")
                .shouldSeeModalWithText("Резервирование счетов выполнено успешно"
                );

        ui.basePage
                .clickButtonByNameCheck("ОК")
                .clickButtonByNameCheck("Действия");

        ui.contractPage
                .issueCreditUniversal("Наличными");

        ui.basePage
                .clickButtonByNameCheck("Подтвердить");

        ui.messageBoxComponent
                .shouldBeModalOpened("Комиссия за выдачу кредита");

        ui.basePage
                .clickButtonByNameCheck("Подтвердить");

        ui.messageBoxComponent
                .shouldSeeModalWithText(
                        "Действие успешно выполнено. Необходимо оплатить комиссию за кредит"
                );
        ui.basePage
                .clickButtonByNameCheck("ОК");

        ui.messageBoxComponent
                .shouldSeeModalWithText(
                        "Кассовый ордер успешно сформирован"
                );
        ui.basePage
                .clickButtonByNameCheck("ОК");

        ui.messageBoxComponent
                .shouldSeeModalWithText(
                        "График платежей успешно получен"
                );
        ui.basePage
                .clickButtonByNameCheck("ОК");
    }

    // ============================================================
    // 5️⃣ ПРОВЕРКИ И ПЕЧАТЬ
    // ============================================================

    private void verifyOrdersAndPrint() {

        ui.contractPage
                .saveValueByMarker("Number");
        ui.buttonsComponent
                .clickButtonByContainNameCheck("Операции по договору")
                .doubleclickButtonByName("Приходный")
                .checkCurrentPage("BnzOrderPageContainer");

        ui.creditApplicationAssertions
                .assertOrderState("Новый");
        ui.fieldAssertions
                .checkFieldValueNormalized("Сумма документа", "200,00");

        ui.basePage
                .clickButtonByNameCheck("Закрыть");

        ui.buttonsComponent
                .doubleclickButtonByName("Расходный")
                .checkCurrentPage("BnzOrderPageContainer");

        ui.creditApplicationAssertions
                .assertOrderState("Новый");
        ui.fieldAssertions
                .checkFieldValueNormalized(
                        "Сумма документа",
                        "50 000,00"
                );

        ui.basePage
                .clickButtonByNameCheck("Закрыть");

        ui.printComponent
                .selectPrintOption(
                        "Чек лист маълумотнома(оферта)"
                );
    }
}
