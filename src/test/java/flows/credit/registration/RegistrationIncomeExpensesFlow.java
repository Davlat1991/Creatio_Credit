package flows.credit.registration;

import core.base.TestContext;
import core.data.registration.ExpenseData;
import core.data.registration.IncomeData;
import core.data.registration.RegistrationIncomeExpensesData;
import io.qameta.allure.Step;

public class RegistrationIncomeExpensesFlow {

    private final TestContext ctx;

    public RegistrationIncomeExpensesFlow(TestContext ctx) {
        this.ctx = ctx;
    }

    @Step("Заполнение доходов и расходов")
    public void fillIncomeAndExpenses(RegistrationIncomeExpensesData data) {

        setRiskAndAdditionalInfo();
        addIncome(data.getIncome());
        addExpense(data.getExpense());
    }

    // ================= PRIVATE =================

    private void setRiskAndAdditionalInfo() {

        ctx.lookupComponent
                .setHandBookFieldByValueCheck("Уровень риска", "Низкий");

        ctx.buttonsComponent
                .clickButtonByContainNameCheck("ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ");

        ctx.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Клиент предоставил подтверждение дохода",
                        "Да"
                )
                .setHandBookFieldByValueCheck(
                        "Тип документа подтверждения дохода",
                        "Выписка с банковского счета"
                );
    }

    private void addIncome(IncomeData income) {

        ctx.basePage
                .clickButtonById("BnzAppIncomesDetailAddRecordButtonButton-imageEl");

        ctx.contractPage
                .setfieldScheduleDetailByDIM("TsiKind", income.getType());
        ctx.menuComponent
                .clickButtonByLiName(income.getType());

        ctx.contractPage
                .setfieldScheduleDetailByDIM("TsiFrequencyType", income.getFrequency());
        ctx.menuComponent
                .clickButtonByLiName(income.getFrequency());

        ctx.contractPage
                .setfieldScheduleDetailByDIM("TsiAmountBC", income.getAmount());
    }

    private void addExpense(ExpenseData expense) {

        ctx.basePage
                .clickButtonById("BnzAppExpensesDetailAddRecordButtonButton-imageEl");

        ctx.checkboxComponent
                .CheckBoxValue("BnzAppExpensesDetailTsiKindComboBoxEdit-el");
        ctx.menuComponent
                .clickButtonByLiName(expense.getType());

        ctx.checkboxComponent
                .CheckBoxValue("BnzAppExpensesDetailTsiFrequencyTypeComboBoxEdit-el");
        ctx.menuComponent
                .clickButtonByLiName(expense.getFrequency());

        ctx.domActions
                .clickDivbyId(
                        "BnzAppExpensesDetailTsiAmountBCFloatEdit-wrap",
                        expense.getAmount()
                );
    }
}
