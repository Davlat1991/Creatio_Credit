package steps.contacts;


import io.qameta.allure.Step;
import org.testng.Assert;
import core.base.common.components.FieldComponent;
import core.pages.contacts.ContactCardPage;
import core.data.contacts.ContactData;

public class ContactValidationSteps {

    private final FieldComponent field = new FieldComponent();
    private final ContactCardPage card = new ContactCardPage();

    @Step("Проверить, что Фамилия = '{data.getLastName()}'")
    public ContactValidationSteps validateLastName(ContactData data) {
        String actual = field.getValue("Фамилия");
        Assert.assertEquals(actual, data.getLastName(), "Фамилия указана неверно");
        return this;
    }

    @Step("Проверить, что Имя = '{data.getFirstName()}'")
    public ContactValidationSteps validateFirstName(ContactData data) {
        String actual = field.getValue("Имя");
        Assert.assertEquals(actual, data.getFirstName(), "Имя указано неверно");
        return this;
    }

    @Step("Проверить, что Отчество = '{data.getMiddleName()}'")
    public ContactValidationSteps validateMiddleName(ContactData data) {
        String actual = field.getValue("Отчество");
        Assert.assertEquals(actual, data.getMiddleName(), "Отчество указано неверно");
        return this;
    }

    @Step("Проверить, что Дата рождения = '{data.getBirthDate()}'")
    public ContactValidationSteps validateBirthDate(ContactData data) {
        String actual = field.getValue("Дата рождения");
        String expected = data.getBirthDate().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        Assert.assertEquals(actual, expected, "Дата рождения указана неверно");
        return this;
    }

    @Step("Проверить номер мобильного телефона = '{data.getMobilePhone()}'")
    public ContactValidationSteps validateMobilePhone(ContactData data) {
        String actual = field.getValue("Мобильный телефон");
        Assert.assertEquals(actual, data.getMobilePhone(), "Мобильный телефон указан неверно");
        return this;
    }

    @Step("Проверить email = '{data.getEmail()}'")
    public ContactValidationSteps validateEmail(ContactData data) {
        String actual = field.getValue("Email");
        Assert.assertEquals(actual, data.getEmail(), "Email указан неверно");
        return this;
    }

    @Step("Проверить полный набор основных данных контакта")
    public ContactValidationSteps validateAllMainInfo(ContactData data) {
        return validateLastName(data)
                .validateFirstName(data)
                .validateMiddleName(data)
                .validateBirthDate(data)
                .validateMobilePhone(data)
                .validateEmail(data);
    }
}

