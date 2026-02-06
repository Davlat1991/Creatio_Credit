// core/data/scenarios/GuarantorScenario.java
package core.data.scenarios;

import core.data.contacts.ContactData;

public class GuarantorScenario {

    public final ContactData guarantor;

    public GuarantorScenario(ContactData guarantor) {
        this.guarantor = guarantor;
    }

    public boolean hasGuarantor() {
        return guarantor != null;
    }
}
