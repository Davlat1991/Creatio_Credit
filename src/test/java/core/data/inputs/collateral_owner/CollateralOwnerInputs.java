package core.data.inputs.collateral_owner;

import core.data.TestData;
import core.data.TestDataLoader;
import core.data.contacts.ContactDataFactory;

public class CollateralOwnerInputs {

    private CollateralOwnerInputs() {}

    public static CollateralOwnerInput clientOwner() {
        TestData data = TestDataLoader.load();

        return new CollateralOwnerInput(
                ContactDataFactory.defaultContact(),
                "Собственник"
        );
    }
}
