package core.data.inputs.collateral_owner;

import core.data.contacts.ContactData;

public class CollateralOwnerInput {

    public final ContactData owner;
    public final String ownershipType;

    public CollateralOwnerInput(
            ContactData owner,
            String ownershipType
    ) {
        this.owner = owner;
        this.ownershipType = ownershipType;
    }
}
