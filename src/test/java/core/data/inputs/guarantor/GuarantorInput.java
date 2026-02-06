package core.data.inputs.guarantor;

import core.data.contacts.ContactData;

public class GuarantorInput {

    public final ContactData contact;
    public final String relationType;
    public final String incomeSource;

    public GuarantorInput(
            ContactData contact,
            String relationType,
            String incomeSource
    ) {
        this.contact = contact;
        this.relationType = relationType;
        this.incomeSource = incomeSource;
    }
}
