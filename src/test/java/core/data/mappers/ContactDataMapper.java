package core.data.mappers;

import core.data.contacts.ContactTestData;
import core.data.contacts.ContactData;

import java.time.LocalDate;

public class ContactDataMapper {

    private ContactDataMapper() {
        // util class
    }

    public static ContactData from(ContactTestData testData) {

        return new ContactData(
                testData.getFirstname(),   // firstName
                testData.getMiddlename(),  // middleName
                testData.getLastname(),    // lastName
                parseDate(testData.getBirthday()),
                testData.getDocumentNumber(),
                testData.getDocumentType(),
                null,
                null,
                null,
                null
        );
    }

    private static LocalDate parseDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return LocalDate.parse(value);
    }
}
