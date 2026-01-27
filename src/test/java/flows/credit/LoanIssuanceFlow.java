package flows.credit;

import core.base.UiContext;
import flows.common.ApplicationSearchFlow;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.refresh;

public class LoanIssuanceFlow {

    private static final String CONTRACT_PAGE_MARKER =
            "BnzContractCreditPageContainer";

    private final UiContext ctx;
    private final ApplicationSearchFlow applicationSearchFlow;

    public LoanIssuanceFlow(UiContext ctx) {
        this.ctx = ctx;
        this.applicationSearchFlow = new ApplicationSearchFlow(ctx);
    }

    // ============================================================
    // 🚀 PUBLIC API
    // ============================================================

    @Step("Loan Issuance: полный сценарий выдачи кредита")
    public void issueLoan() {

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

        ctx.dashboardComponent
                .clickElementDashboardCheck(
                        "Проверка решения КК",
                        "Execute",
                        "//*[@data-item-marker='MiniPage']"
                )
        ;
        ctx.lookupComponent
                .setfieldScheduleDetailByDIM("Result", "Выполнена")
        ;
        ctx.menuComponent
                .clickButtonByLiName("Выполнена");

        ctx.basePage
                .clickButtonByDataItemMaker("SaveEditButton");
    }

    // ============================================================
    // 2️⃣ СОЗДАНИЕ ДОГОВОРА В АБС
    // ============================================================

    private void createAbsContract() {

        ctx.dashboardComponent
                .clickElementDashboardName(
                        "Создание договора в АБС (печать договоров для встречи)");
        ctx.contractPage
                .clickContractAutoWait(CONTRACT_PAGE_MARKER);

        ctx.basePage
                .clickButtonOnPageByName(CONTRACT_PAGE_MARKER, "Действия");

        ctx.menuComponent
                .clickButtonByLiName("Создание договора");

        ctx.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Вид планирования",
                        "Аннуитетный_005"
                );

        ctx.contractPage
                .fillLoadCreditTypeSafely("Получает сегодня")
                .selectLoadCreditTypeNew("Получает сегодня");

        ctx.basePage
                .clickButtonByName("Выбрать");

        ctx.messageBoxComponent
                .shouldSeeModalWithText("Нет задолженности!");
        ctx.basePage
                .clickButtonByNameCheck("ОК");

        ctx.messageBoxComponent
                .shouldSeeModalWithText("Договор успешно создан");
        ctx.basePage
                .clickButtonByNameCheck("ОК");

        ctx.basePage
                .clickButtonById(
                        "BnzContractCreditPageBnzCreateSavingAcountContractButtonButton-imageEl"
                );

        ctx.messageBoxComponent
                .shouldSeeModalWithText(
                        "Депозитный договор успешно создан в АБС"
                );

        ctx.basePage
                .clickButtonByNameCheck("ОК");

        ctx.fieldUtils
                .saveValueDIMCheckWorkNEW("BnzDepositBankAccount");
        ctx.contractPage
                .clickButtonByNameCheck("Закрыть");

        refresh();
    }


    // ============================================================
    // 3️⃣ ПРИВЯЗКА СЧЁТОВ И ГРАФИКА
    // ============================================================

    private void bindAccountsAndSchedule() {

        ctx.lookupComponent
                .clickSearchIconID(
                        "BnzContractCreditPageBnzCreditBankAccountLookupEdit"
                )
                .selectValueInLookupWorkNEW("searchEdit");

        ctx.basePage
                .clickButtonByNameCheck("Сохранить")
                .clickButtonByNameCheck("Действия");

        ctx.messageBoxComponent
                .clickAndCheckModal("Получение графика платежей")
                .shouldSeeModalWithText("График платежей успешно получен");
        ctx.basePage
                .clickButtonByNameCheck("ОК")
                .clickButtonByNameCheck("Действия");

        ctx.messageBoxComponent
                .clickAndCheckModal("Привязка счета к договору")
                .shouldSeeModalWithText("Счет успешно привязан к кредитному договору");

        ctx.basePage
                .clickButtonByNameCheck("ОК");
    }

    // ============================================================
    // 4️⃣ ПОДПИСАНИЕ И ВЫДАЧА КРЕДИТА
    // ============================================================

    private void signAndIssueCredit() {

        ctx.dashboardComponent
                .clickElementDashboardCheck(
                        "Создание договора в АБС (печать договоров для встречи)",
                        "Execute",
                        "//*[@data-item-marker='MiniPage']"
                );
        ctx.contractPage
                .setfieldScheduleDetailByDIM("Result", "Выполнена");
        ctx.menuComponent
                .clickButtonByLiName("Выполнена");

        ctx.basePage
                .clickButtonByDataItemMaker("SaveEditButton")
                .clickButtonByNameCheck("Действия");

        ctx.messageBoxComponent
                .clickAndCheckModal("Подписание договора")
                .shouldSeeModalWithText("Резервирование счетов выполнено успешно"
                );

        ctx.basePage
                .clickButtonByNameCheck("ОК")
                .clickButtonByNameCheck("Действия");

        ctx.contractPage
                .issueCreditUniversal("Наличными");

        ctx.basePage
                .clickButtonByNameCheck("Подтвердить");

        ctx.messageBoxComponent
                .shouldBeModalOpened("Комиссия за выдачу кредита");

        ctx.basePage
                .clickButtonByNameCheck("Подтвердить");

        ctx.messageBoxComponent
                .shouldSeeModalWithText(
                        "Действие успешно выполнено. Необходимо оплатить комиссию за кредит"
                );
        ctx.basePage
                .clickButtonByNameCheck("ОК");

        ctx.messageBoxComponent
                .shouldSeeModalWithText(
                        "Кассовый ордер успешно сформирован"
                );
        ctx.basePage
                .clickButtonByNameCheck("ОК");

        ctx.messageBoxComponent
                .shouldSeeModalWithText(
                        "График платежей успешно получен"
                );
        ctx.basePage
                .clickButtonByNameCheck("ОК");
    }

    // ============================================================
    // 5️⃣ ПРОВЕРКИ И ПЕЧАТЬ
    // ============================================================

    private void verifyOrdersAndPrint() {

        ctx.contractPage
                .saveValueByMarker("Number");
        ctx.buttonsComponent
                .clickButtonByContainNameCheck("Операции по договору")
                .doubleclickButtonByName("Приходный")
                .checkCurrentPage("BnzOrderPageContainer");

        ctx.creditApplicationAssertions
                .assertOrderState("Новый");
        ctx.fieldAssertions
                .checkFieldValueNormalized("Сумма документа", "200,00");

        ctx.basePage
                .clickButtonByNameCheck("Закрыть");

        ctx.buttonsComponent
                .doubleclickButtonByName("Расходный")
                .checkCurrentPage("BnzOrderPageContainer");

        ctx.creditApplicationAssertions
                .assertOrderState("Новый");
        ctx.fieldAssertions
                .checkFieldValueNormalized(
                        "Сумма документа",
                        "50 000,00"
                );

        ctx.basePage
                .clickButtonByNameCheck("Закрыть");

        ctx.printComponent
                .selectPrintOption(
                        "Чек лист маълумотнома(оферта)"
                );
    }
}
