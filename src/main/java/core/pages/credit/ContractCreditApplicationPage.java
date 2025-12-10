package core.pages.credit;


import core.base.BasePage;
import core.base.common.components.*;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

/**
 * ContractCreditApplicationPage — PageObject высокого уровня.
 * В нём локально подключены только нужные компоненты (не в BasePage).
 */
public class ContractCreditApplicationPage extends BasePage {

    // Локальные компоненты страницы
    private final ButtonsComponent buttons = new ButtonsComponent();
    private final FieldComponent fields = new FieldComponent();
    private final LookupComponent lookup = new LookupComponent();
    private final GridComponent grid = new GridComponent();
    private final MiniPageComponent miniPage = new MiniPageComponent();
    private final DashboardComponent dashboard = new DashboardComponent();
    private final DashboardActionsComponent dashboardActions = new DashboardActionsComponent();
    private final FiltersComponent filters = new FiltersComponent();
    private final FileUploadComponent files = new FileUploadComponent();
    private final MessageBoxComponent messages = new MessageBoxComponent();
    private final CheckboxComponent checkbox = new CheckboxComponent();


    private String savedValue;

    @Step("Выбрать продукт '{product}'")
    public ContractCreditApplicationPage selectProduct(String product) {
        lookup.selectValue("Product", product);
        return this;
    }

    @Step("Заполнить сумму кредита '{amount}'")
    public ContractCreditApplicationPage fillLoanAmount(String amount) {
        fields.setValue("LoanAmount", amount);
        return this;
    }

    @Step("Получить и открыть график платежей")
    public ContractCreditApplicationPage openPaymentSchedule() {
        buttons.clickByName("Получить график платежей");
        grid.doubleClickFirstRow("PaymentScheduleDetail");
        return this;
    }

    @Step("Выдать кредит наличными")
    public ContractCreditApplicationPage issueCreditCash() {
        dashboardActions.issueCredit("Наличными");
        return this;
    }

    @Step("Утвердить решение по заявке")
    public ContractCreditApplicationPage approveDecision() {
        dashboardActions.approve();
        return this;
    }

    @Step("Сохранить значение поля по marker '{marker}'")
    public ContractCreditApplicationPage saveValue(String marker) {
        this.savedValue = fields.getValue(marker);
        return this;
    }

    @Step("Вставить сохранённое значение в поле '{marker}'")
    public ContractCreditApplicationPage pasteSavedValue(String marker) {
        fields.setValue(marker, savedValue);
        return this;
    }

    public String getSavedValue() {
        return this.savedValue;
    }

    public void clickButton(String name) {
        new ButtonsComponent().clickByName(name);
    }

    public void clickAddBorrower() {
        new ButtonsComponent().clickByDataItemMarker("AddBorrowerButton");
    }

    public String getApplicationNumber() {
        return $x("//*[@data-item-marker='Number']").getValue();
    }


}

