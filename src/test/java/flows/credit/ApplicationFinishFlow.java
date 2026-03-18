package flows.credit;


import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import core.base.UiContext;
import core.pages.credit.ContractCreditApplicationPage;
import flows.common.ApplicationSearchFlow;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static core.pages.credit.ContractCreditApplicationPage.CONTRACT_PAGE_MARKER;

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
    private static final String APPLICATION_PAGE_MARKER =
            "FinApplicationPageContainer";
    private static final Logger log = LoggerFactory.getLogger(AttachDocumentsFlow.class);

    public ApplicationFinishFlow(UiContext ui) {
        this.ui = ui;
        this.applicationSearchFlow = new ApplicationSearchFlow(ui);
    }

    @Step("Этап завершения выдачи кредита")
    public void completeApplicationFinish() {

        ui.basePage.closeConsultationPanelIfOpened();
        // 1. Открыть договор по сохранённому номеру
        //applicationSearchFlow.openBySavedСontracts();

        $x("//div[@data-item-marker='" + CONTRACT_PAGE_MARKER + "']")
                .shouldBe(visible);

        openApplicationFromField();
        refresh();
        openContract();

        // 2. Перейти на вкладку "Операции по договору"
        ui.buttonsComponent
                .clickButtonByContainNameCheck("Операции по договору");

        // 3. Проверка приходного ордера
        checkConfirmedIncomingOrder();

        // 4. Проверка расходного ордера
        checkConfirmedOutgoingOrder();
        refresh();
        ui.buttonsComponent
                .clickButtonByContainNameCheck("Файлы и примечания");
        checkFileUploaded("Справка о доходах.pdf");
        downloadFile();
        openApplicationFromField();
        completeDawnmloadDocument();



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
    // ✅ Подтверждение получения документа
    // ======================================================================
    @Step("Открыть заявку по полю 'Заявка'")
    public void downloadFile() {

        SelenideElement applicationLink = $x("//div[@data-item-marker='Справка о доходах.pdf']//a")
                .shouldBe(visible)
                .scrollIntoView(false);

        applicationLink.click();
    }

    @Step("Открыть заявку по полю 'Заявка'")
    public void openApplicationFromField() {

        SelenideElement applicationLink = $x("//div[@data-item-marker='FinApplication']//a")
                .shouldBe(visible)
                .scrollIntoView(false);

        applicationLink.click();

        // ✅ проверка что открылась заявка
        $x("//div[@data-item-marker='" + APPLICATION_PAGE_MARKER + "']")
                .shouldBe(visible);
    }


    // ======================================================================
    // ✅ Завершение выдачи кредита
    // ======================================================================

    @Step("Проверить, что файл '{fileName}' загружен")
    private void checkFileUploaded(String fileName) {

        Allure.step("🔍 Начата проверка файла: " + fileName);
        log.info("Проверка файла: {}", fileName);

        ui.basePage.waitUntilNotBusy2();

        $x("//div[@data-item-marker='" + fileName + "']")
                .shouldBe(visible);

        Allure.step("✅ Файл найден в списке: " + fileName);
        log.info("Файл найден в списке: {}", fileName);
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


    @Step("Открыть вкладку Параметры договора")
    private void openContract() {

        ui.dashboardComponent
                .clickElementDashboardName(
                        "Подтвердить получение документа");
        ui.contractPage
                .clickContractAutoWait(CONTRACT_PAGE_MARKER);
    }

    @Step("Завершение активности \"Подтвердить получение документа\"")
    private void completeDawnmloadDocument() {

        ui.dashboardComponent.clickElementDashboardCheck(
                "Подтвердить получение документа",
                "Execute",
                "//*[@data-item-marker='MiniPage']"
        );

        ui.contractPage
                .setfieldScheduleDetailByDIM("Result", "Подтвердить получение документа");

        ui.menuComponent
                .clickButtonByLiName("Подтвердить получение документа");

        ui.basePage
                .clickButtonByDataItemMaker("SaveEditButton");
    }


}

