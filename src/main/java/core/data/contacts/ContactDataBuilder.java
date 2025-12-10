package core.data.contacts;


import java.time.LocalDate;

public class ContactDataBuilder {

    private String firstName = "TestFirst";
    private String middleName = "TestMiddle";
    private String lastName = "TestLast";
    private LocalDate birthDate = LocalDate.of(1990, 1, 1);
    private String passportNumber = "AA1234567";
    private String passportIssuedBy = "Internal Affairs Department";
    private LocalDate passportIssueDate = LocalDate.of(2010, 5, 10);
    private String mobilePhone = "+992901112233";
    private String email = "test@example.com";
    private String address = "Khujand, Tajikistan";

    // 🔹 Пустой конструктор для кастомизации
    public ContactDataBuilder() {}

    // 🔹 Метод defaultTajik() — быстрый пресет
    public static ContactDataBuilder defaultTajik() {
        return new ContactDataBuilder()
                .withFirstName("Ali")
                .withMiddleName("Valiyevich")
                .withLastName("Valiyev")
                .withBirthDate(LocalDate.of(1995, 3, 20))
                .withPassportNumber("AA9876543")
                .withPassportIssuedBy("Khujand IIB")
                .withPassportIssueDate(LocalDate.of(2015, 1, 1))
                .withMobilePhone("+992900001122")
                .withEmail("ali.valiyev@example.com")
                .withAddress("Khujand city");
    }

    // ---------------------------
    // 🔧 Builder setters
    // ---------------------------

    public ContactDataBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ContactDataBuilder withMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public ContactDataBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ContactDataBuilder withBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public ContactDataBuilder withPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
        return this;
    }

    public ContactDataBuilder withPassportIssuedBy(String passportIssuedBy) {
        this.passportIssuedBy = passportIssuedBy;
        return this;
    }

    public ContactDataBuilder withPassportIssueDate(LocalDate passportIssueDate) {
        this.passportIssueDate = passportIssueDate;
        return this;
    }

    public ContactDataBuilder withMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public ContactDataBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public ContactDataBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    // ---------------------------
    // 🏗 Build ContactData object
    // ---------------------------
    public ContactData build() {
        return new ContactData(
                firstName,
                middleName,
                lastName,
                birthDate,
                passportNumber,
                passportIssuedBy,
                passportIssueDate,
                mobilePhone,
                email,
                address
        );
    }
}
