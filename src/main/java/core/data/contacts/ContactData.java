package core.data.contacts;


import java.time.LocalDate;

public class ContactData {

    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    private String passportNumber;
    private String passportIssuedBy;
    private LocalDate passportIssueDate;
    private String mobilePhone;
    private String email;
    private String address;

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