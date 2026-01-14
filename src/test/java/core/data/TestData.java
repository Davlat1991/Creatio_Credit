package core.data;

import core.data.contacts.ContactTestData;
import core.data.documents.DocumentsTestData;
import core.data.products.LoanDefaults;
import core.data.users.UserTestData;

import java.util.Map;

public class TestData {

    private Map<String, UserTestData> users;
    private Map<String, ContactTestData> contacts;
    private LoanDefaults loanDefaults;
    private DocumentsTestData documents;

    // USERS
    public UserTestData user(String key) {
        return users.get(key);
    }

    // CONTACTS
    public ContactTestData defaultContact() {
        return contacts.get("default");
    }

    // LOAN
    public LoanDefaults loanDefaults() {
        return loanDefaults;
    }

    // DOCUMENTS
    public DocumentsTestData documents() {
        return documents;
    }


}
