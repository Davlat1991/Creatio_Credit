package core.data.contacts;


import java.time.LocalDate;

public class ContactData {

    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final LocalDate birthDate;
    private final String passportNumber;
    private final String passportIssuedBy;
    private final LocalDate passportIssueDate;
    private final String mobilePhone;
    private final String email;
    private final String address;

    // Конструктор
    public ContactData(String firstName,
                       String middleName,
                       String lastName,
                       LocalDate birthDate,
                       String passportNumber,
                       String passportIssuedBy,
                       LocalDate passportIssueDate,
                       String mobilePhone,
                       String email,
                       String address) {

        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.passportNumber = passportNumber;
        this.passportIssuedBy = passportIssuedBy;
        this.passportIssueDate = passportIssueDate;
        this.mobilePhone = mobilePhone;
        this.email = email;
        this.address = address;
    }

    // Геттеры
    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getPassportIssuedBy() {
        return passportIssuedBy;
    }

    public LocalDate getPassportIssueDate() {
        return passportIssueDate;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "ContactData{fullName='" + getFullName() + "'}";
    }


}