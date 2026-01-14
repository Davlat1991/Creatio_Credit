package flows.credit;

import core.base.TestContext;
import io.qameta.allure.Step;

public class ProductSelectionFlow {

    private final TestContext ctx;

    public ProductSelectionFlow(TestContext ctx) {
        this.ctx = ctx;
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

        ctx.lookupComponent
                .setHandBookFieldByValueCheck("Вид продукта", productType)
                .setHandBookFieldByValueCheck("Цель кредитования", creditPurpose)
                .setFieldByValueCheck("Сумма", amount)
                .setFieldByValueCheck("Срок, мес.", term)
                .setHandBookFieldByValueCheck("Валюта", currency);
    }

    private void findProducts() {
        ctx.contractPage.clickButtonByNameCheck("Подобрать");
    }

    private void chooseFirstProduct() {
        ctx.contractPage.clickFirstRowInGridAndWaitButton(
                "grid-TsiOpportunityConditionSelectionDetailDataGridGrid-wrap",
                "Выбрать"
        );

        ctx.basePage.clickButtonByDataItemMakerCheck("Выбрать");
    }
}
