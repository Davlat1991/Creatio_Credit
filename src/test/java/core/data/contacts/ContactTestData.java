package core.data.contacts;

public class ContactTestData {

    private String firstname;
    private String lastname;
    private String middlename;
    private String birthday;
    private String documentType;
    private String documentNumber;

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }
}
