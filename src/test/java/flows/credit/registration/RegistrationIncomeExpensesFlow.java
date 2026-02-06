package flows.credit.registration;

import core.base.UiContext;
import core.base.common.activity.ActivityField;
import core.data.registration.ExpenseData;
import core.data.registration.IncomeData;
import core.data.registration.RegistrationIncomeExpensesData;
import io.qameta.allure.Step;

public class RegistrationIncomeExpensesFlow {

    private final UiContext ui;

    public RegistrationIncomeExpensesFlow(UiContext ui) {
        this.ui = ui;
    }

    @Step("Заполнение доходов и расходов")
    public void fillIncomeAndExpenses(RegistrationIncomeExpensesData data) {

        setRiskAndAdditionalInfo();
        addIncome(data.getIncome());
        addExpense(data.getExpense());
    }

    // ================= PRIVATE =================

    private void setRiskAndAdditionalInfo() {

        ui.lookupComponent
                .setHandBookFieldByValueCheck("Уровень риска", "Низкий");

        ui.buttonsComponent
                .clickButtonByContainNameCheck("ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ");

        ui.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Клиент предоставил подтверждение дохода","Да")
                .setHandBookFieldByValueCheck(
                        "Тип документа подтверждения дохода",
                        "Выписка с банковского счета");
    }

    private void addIncome(IncomeData income) {

        ui.basePage
                .clickButtonById("BnzAppIncomesDetailAddRecordButtonButton-imageEl");

        ui.contractPage
                .setfieldScheduleDetailByDIM("TsiKind", income.getType());
        ui.menuComponent
                .clickButtonByLiName(income.getType());

        ui.contractPage
                .setfieldScheduleDetailByDIM("TsiFrequencyType", income.getFrequency());
        ui.menuComponent
                .clickButtonByLiName(income.getFrequency());

        ui.contractPage
                .setfieldScheduleDetailByDIM("TsiAmountBC", income.getAmount());
        ui.basePage
                .clickButtonByDataItemMaker("save");
    }


    private void addExpense(ExpenseData expense) {

        ui.basePage
                .clickButtonById("BnzAppExpensesDetailAddRecordButtonButton-imageEl");

        ui.checkboxComponent
                .CheckBoxValue("BnzAppExpensesDetailTsiKindComboBoxEdit-el");
        ui.menuComponent
                .clickButtonByLiName(expense.getType());

        ui.checkboxComponent
                .CheckBoxValue("BnzAppExpensesDetailTsiFrequencyTypeComboBoxEdit-el");
        ui.menuComponent
                .clickButtonByLiName(expense.getFrequency());

        ui.domActions
                .clickDivbyId(
                        "BnzAppExpensesDetailTsiAmountBCFloatEdit-wrap",
                        expense.getAmount()
                );
        ui.basePage
                .clickButtonByDataItemMaker("save");
    }

    @Step("Заполнение деятельности физического лица")
    public void fillIndividualActivitySelfEmployed() {
        ui.buttonsComponent
                .clickButtonByContainNameCheck("ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ");

        ui.detailPage.clickAddRecordInDetail("Деятельность физ.лица");


        ui.activityComponent
                .select(ActivityField.SECTOR, "Самозанятость")
                .select(ActivityField.SEGMENT, "Агро")
                .select(ActivityField.SUB_SEGMENT, "Животновод")
                .select(ActivityField.SALES_TYPE, "Оптовая")
                .select(ActivityField.MARKET, "Авангард");
        ui.checkboxComponent
                .ensureCheckboxChecked("BnzIsPrimary");

        ui.contractPage
                .clickButtonByNameCheck("Сохранить");

    }


    @Step("Заполнение деятельности физического лица")
    public void fillIndividualActivityEmployed() {
        ui.buttonsComponent
                .clickButtonByContainNameCheck("ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ");

        ui.detailPage.clickAddRecordInDetail("Деятельность физ.лица");


        ui.activityComponent
                .select(ActivityField.SECTOR, "Работник организации")
                .select(ActivityField.SEGMENT, "Экономист")
                .select(ActivityField.SUB_SEGMENT, "Бухгалтер")
                .select(ActivityField.SALES_TYPE, "Не торгует");
                //.select(ActivityField.MARKET, "Авангард");
        ui.checkboxComponent
                .ensureCheckboxChecked("BnzIsPrimary");

        ui.contractPage
                .clickButtonByNameCheck("Сохранить");

    }


}










