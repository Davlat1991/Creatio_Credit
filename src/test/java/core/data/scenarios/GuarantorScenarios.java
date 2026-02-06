// core/data/scenarios/GuarantorScenarios.java
package core.data.scenarios;

import core.data.TestData;
import core.data.TestDataLoader;
import core.data.mappers.ContactDataMapper;

public class GuarantorScenarios {

    private static final TestData data = TestDataLoader.load();

    private GuarantorScenarios() {}

    public static GuarantorScenario none() {
        return new GuarantorScenario(null);
    }

    public static GuarantorScenario defaultGuarantor() {
        return new GuarantorScenario(
                ContactDataMapper.from(data.defaultContact())
        );
    }
}
