package core.data.inputs.guarantor;

import core.data.TestData;
import core.data.TestDataLoader;
import core.data.mappers.ContactDataMapper;

public class GuarantorInputs {

    private GuarantorInputs() {}

    public static GuarantorInput defaultGuarantor() {
        TestData data = TestDataLoader.load();

        return new GuarantorInput(
                ContactDataMapper.from(data.defaultContact()),
                "Супруг(а)",
                "Другое"
        );
    }
}
