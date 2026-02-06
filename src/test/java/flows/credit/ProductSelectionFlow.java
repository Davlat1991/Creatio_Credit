package flows.credit;

import core.base.UiContext;
import io.qameta.allure.Step;

public class ProductSelectionFlow {

    private final UiContext ui;

    public ProductSelectionFlow(UiContext ui) {
        this.ui = ui;
    }

    @Step("Подбор и выбор кредитного продукта")
    public void selectProduct(
            String productType,
            String creditPurpose,
            String amount,
            String term,
            String currency
    ) {

        fillProductCriteria(
                productType,
                creditPurpose,
                amount,
                term,
                currency
        );

        findProducts();
        chooseFirstProduct();
    }

    // ---------------- PRIVATE STEPS ----------------

    private void fillProductCriteria(
            String productType,
            String creditPurpose,
            String amount,
            String term,
            String currency
    ) {

        ui.lookupComponent
                .setHandBookFieldByValueCheck("Вид продукта", productType)
                .setHandBookFieldByValueCheck("Цель кредитования", creditPurpose)
                .setFieldByValueCheck("Сумма", amount)
                .setFieldByValueCheck("Срок, мес.", term)
                .setHandBookFieldByValueCheck("Валюта", currency);
    }

    private void findProducts() {
        ui.contractPage.clickButtonByNameCheck("Подобрать");
    }

    private void chooseFirstProduct() {
        ui.contractPage.clickFirstRowInGridAndWaitButton(
                "grid-TsiOpportunityConditionSelectionDetailDataGridGrid-wrap",
                "Выбрать"
        );

        ui.basePage.clickButtonByDataItemMakerCheck("Выбрать");
    }
}
