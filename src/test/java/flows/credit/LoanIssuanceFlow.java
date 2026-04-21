package flows.credit;

import com.codeborne.selenide.*;
import core.base.UiContext;
import core.data.collateral.CollateralData;
import core.enums.CollateralType;
import flows.common.ApplicationSearchFlow;
import flows.credit.signing.SigningContractFlow;
import flows.credit.signing.SigningPrintFlow;
import io.qameta.allure.Step;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class LoanIssuanceFlow {
    private static final String CONTRACT_PAGE_MARKER =
            "BnzContractCreditPageContainer";

    private final UiContext ui;
    private final ApplicationSearchFlow applicationSearchFlow;
    private final SigningContractFlow contractFlow;
    private final SigningPrintFlow printFlow;


    public LoanIssuanceFlow(UiContext ui) {
        this.ui = ui;
        this.applicationSearchFlow = new ApplicationSearchFlow(ui);
        this.contractFlow = new SigningContractFlow(ui);
        this.printFlow = new SigningPrintFlow(ui);
    }


    // ============================================================
    // 🚀 PUBLIC API
    // ============================================================

    @Step("Loan Issuance: полный сценарий выдачи кредита")
    public void issueLoan() {

        ui.basePage.closeConsultationPanelIfOpened();

        // 1️. Открываем заявку по сохранённому номеру
        applicationSearchFlow.openBySavedNumber();

        completeKkDecisionCheck();

        openContract();

        // 1️⃣ Создание договора
        createAbsContract();

        bindAccountsAndSchedule();

        // 2️⃣ вкладка параметры
        openContractParametersTab();

        // 3️⃣ скролл до детали
        scrollToCollateralDetail();

        // 4️⃣ показать все строки
        if ($("[data-item-marker='loadMore']").exists()) {
            ui.basePage.clickButtonByDataItemMaker("loadMore");
        }

        // 5️⃣ ждать пока загрузятся строки
        getCollateralRows().shouldBe(CollectionCondition.sizeGreaterThan(0));

        ElementsCollection rows = getCollateralRows();
        System.out.println("Найдено залогов: " + rows.size());

        openCollateralByName("Денежные средства (депозитные счета)");

        waitForCollateralPage();

        createCollateralContract();

        printCollateralContract("Шартномаи гарави пасандоз");

        waitForConfidantGrid();

        selectConfidantByPosition("Сардор дар Идораи амалиётb");

        // сохранить и закрыть залог
        saveAndCloseCollateral();


        /*openCollateralByName("Золотые изделия");

        waitForCollateralPage();

        createCollateralContract();

        printCollateralContract("Шартномаи гарави дорои");

        waitForConfidantGrid();

        selectConfidantByPosition("Сардор дар Идораи амалиётb");

        // сохранить и закрыть залог
        saveAndCloseCollateral();


        openContract();
        openContractParametersTab();
        scrollToCollateralDetail();
        if ($("[data-item-marker='loadMore']").exists()) {
            ui.basePage.clickButtonByDataItemMaker("loadMore");

        }
        getCollateralRows().shouldBe(CollectionCondition.sizeGreaterThan(0));
        rows = getCollateralRows();
        System.out.println("Найдено залогов: " + rows.size());
        openCollateralByName("Будущий урожай");
        waitForCollateralPage();
        createCollateralContract();
        printCollateralContract("Шартномаи гарави пахта");
        waitForConfidantGrid();

        selectConfidantByPosition("Сардор дар Идораи амалиётb");
        // сохранить и закрыть залог
        saveAndCloseCollateral();*/

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





    @Step("Открыть вкладку Параметры договора")
    private void openContractParametersTab() {

        ui.buttonsComponent
                .clickButtonByContainNameCheck("Параметры договора");
    }

    @Step("Открыть вкладку Параметры договора")
    private void openContract() {

        ui.dashboardComponent
                .clickElementDashboardName(
                        "Создание договора в АБС (печать договоров для встречи)");
        ui.contractPage
                .clickContractAutoWait(CONTRACT_PAGE_MARKER);
    }



    @Step("Получить строки залогов из детали Договоры обеспечения")
    private ElementsCollection getCollateralRows() {

        return ui.gridComponent.getCollateralContractRows();
    }

    @Step("Прокрутить страницу до детали Договоры обеспечения")
    private void scrollToCollateralDetail() {

        SelenideElement detail = $x("//*[@data-item-marker='Договоры обеспечения']");

        executeJavaScript("arguments[0].scrollIntoView(true);", detail);

        detail.shouldBe(Condition.visible);
    }



    @Step("Открыть залог: {type}")
    private void openCollateralByType(CollateralType type) {

        String collateralName = type.getUiName();

        ElementsCollection rows = getCollateralRows();

        rows.findBy(Condition.text(collateralName))
                .scrollTo()
                .doubleClick();
    }

    public void openCollaterals(CollateralType... types) {

        for (CollateralType type : types) {
            openCollateralByType(type);
        }
    }

    private void openCollateral(CollateralType type) {

        String collateralName = type.getUiName();

        getCollateralRows()
                .findBy(Condition.text(collateralName))
                .scrollTo()
                .doubleClick();
    }



    private void openCollateralByName(String collateralName) {

        SelenideElement row = $x(
                "//span[@grid-data-type='text' and normalize-space()='" + collateralName + "']"
        ).shouldBe(Condition.visible);

        actions()
                .doubleClick(row)
                .perform();
    }

    @Step("Создать договор обеспечения")
    private void createCollateralContract() {

        ui.basePage
                .clickButtonByNameCheck("Действия");

        ui.menuComponent
                .clickButtonByLiName("Создание договора обеспечения");

        ui.messageBoxComponent
                .shouldSeeModalWithText("Договор успешно создан");

        ui.basePage
                .clickButtonByNameCheck("ОК");
    }

    @Step("Печать договора обеспечения")
    private void printCollateralContract(String printFormName) {

        // открыть меню печати
        ui.basePage.clickButtonByNameCheck("Печать");

        // ждать пока появится список печатных форм
        SelenideElement form =
                $x("//ul[@data-item-marker='PrintButton']//li[normalize-space()='" + printFormName + "']")
                        .shouldBe(Condition.visible);

        form.click();
    }

    //Альтернатива
    @Step("Ожидание открытия страницы выбора доверенного лица")
    private void waitForConfidantPage() {

        $("[data-item-marker='BnzSelectConfidantPageContainer']")
                .shouldBe(Condition.visible);
    }

    @Step("Ожидание открытия страницы выбора доверенного лица")
    private void waitForConfidantGrid() {

        $("[data-item-marker='Должностные лица']")
                .shouldBe(Condition.visible);
    }

    @Step("Выбор доверенного лица по должности")
    private void selectConfidantByPosition(String position) {

        // нажать на должность
        SelenideElement row = $x(
                "//span[@grid-data-type='text' and normalize-space()='" + position + "']"
        ).shouldBe(Condition.visible);

        row.click();

        // нажать кнопку Выбрать
        $x("//span[text()='Выбрать']")
                .shouldBe(Condition.visible)
                .click();
    }

    @Step("Сохранить и закрыть страницу залога")
    private void saveAndCloseCollateral() {

        ui.basePage
                .clickButtonByMarkerIfVisible("SaveButton");

        ui.basePage
                .clickButtonByMarkerIfVisible("SaveButton");
    }



    @Step("Обработка залога: {type}")
    private void processCollateral(CollateralType type) {

        // 1 открыть залог
        openCollateralByName(type.getUiName());

        // 2 создать договор
        createCollateralContract();

        // 3 печать (если есть)
        if (type.hasPrintForm()) {

            printCollateralContract(type.getPrintForm());

            waitForConfidantPage();

            selectConfidantByPosition("Сардор дар Идораи амалиёт");
        }

        // 4 сохранить и закрыть
        saveAndCloseCollateral();
    }

    @Step("Обработка залогов")
    private void processCollaterals(CollateralType... types) {

        for (CollateralType type : types) {

            processCollateral(type);
        }
    }

    @Step("Ожидание открытия страницы залога")
    private void waitForCollateralPage() {

        $("[data-item-marker='BnzContractEnsuringPageContainer']")
                .shouldBe(Condition.visible);
    }
}
