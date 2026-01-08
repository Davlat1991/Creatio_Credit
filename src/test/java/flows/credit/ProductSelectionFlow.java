package flows.credit;

import io.qameta.allure.Step;
import core.base.BasePage;
import core.base.common.components.LookupComponent;
import core.base.common.components.MessageBoxComponent;
import core.pages.credit.ContractCreditApplicationPage;

public class ProductSelectionFlow {

    private final LookupComponent lookup = new LookupComponent();
    private final BasePage basePage = new BasePage();
    private final MessageBoxComponent messageBox = new MessageBoxComponent();
    private final ContractCreditApplicationPage contractPage =
            new ContractCreditApplicationPage();

    @Step("Подбор и создание кредитного продукта")
    public void selectAndCreateProduct(
            String productType,
            String creditPurpose,
            String amount,
            String term,
            String currency
    ) {

        fillProductParameters(
                productType,
                creditPurpose,
                amount,
                term,
                currency
        );

        selectProductFromGrid();
        configureSchedule();
        calculateAndCreateApplication();
    }

    // =========================
    // INTERNAL STEPS
    // =========================

    private void fillProductParameters(
            String productType,
            String creditPurpose,
            String amount,
            String term,
            String currency
    ) {
        lookup
                .setHandBookFieldByValueCheck("Вид продукта", productType)
                .setHandBookFieldByValueCheck("Цель кредитования", creditPurpose)
                .setFieldByValueCheck("Сумма", amount)
                .setFieldByValueCheck("Срок, мес.", term)
                .setHandBookFieldByValueCheck("Валюта", currency);

        contractPage.clickButtonByNameCheck("Подобрать");
    }

    private void selectProductFromGrid() {
        contractPage
                .clickFirstRowInGridAndWaitButton(
                        "grid-TsiOpportunityConditionSelectionDetailDataGridGrid-wrap",
                        "Выбрать"
                );

        basePage.clickButtonByDataItemMakerCheck("Выбрать");
    }

    private void configureSchedule() {
        lookup.setFieldByValueCheck("Запрашиваемая дата погашения", "3");

        basePage.clickButtonById(
                "KzParameterScheduleDetailAddRecordButtonButton-imageEl"
        );

        contractPage
                .setfieldScheduleDetailByDIM("KzNumber", "2")
                .setHandBookFieldByValue("KzTypeScheduler", "Аннуитетный")
                .setfieldScheduleDetailByDIM("KzTermMonth", "36");
    }

    private void calculateAndCreateApplication() {
        basePage
                .clickButtonByDataItemMaker("save")
                .clickButtonByNameCheck("Рассчитать")
                .clickButtonByNameCheck("Создать заявку");

        messageBox.shouldSeeModalWithText("Нет задолженности!");
        basePage.clickButtonByDataItemMaker("ОК");
    }
}
