package core.data.scenarios;

import core.data.TestData;
import core.data.TestDataLoader;

public class IncomeScenarios {

    private IncomeScenarios() {}

    public static IncomeScenario otherIncome() {
        TestData data = TestDataLoader.load();

        return new IncomeScenario(
                data.registrationIncomeExpenses()
        );
    }
}
