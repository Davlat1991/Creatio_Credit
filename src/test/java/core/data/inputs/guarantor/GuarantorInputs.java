package core.data.inputs.guarantor;

import core.data.TestData;
import core.data.TestDataLoader;
import core.data.contacts.ContactDataFactory;


public class GuarantorInputs {

    private GuarantorInputs() {}

    public static GuarantorInput defaultGuarantor() {
        TestData data = TestDataLoader.load();

        return new GuarantorInput(
                ContactDataFactory.defaultContact(),
                "Супруг(а)",
                "Другое"
        );
    }
}
