package steps.contacts;


import io.qameta.allure.Step;
import core.data.contacts.ContactData;
import core.base.BasePage;
import core.base.common.components.FieldComponent;
import core.base.common.components.LookupComponent;
import core.pages.contacts.ContactCardPage;
import java.time.format.DateTimeFormatter;

public class ContactCreationSteps {

    private final ContactsPage contactsPage = new ContactsPage();
    private final ContactCardPage cardPage = new ContactCardPage();
    private final FieldComponent field = new FieldComponent();
    private final LookupComponent lookup = new LookupComponent();
    private final BasePage base = new BasePage();

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Step("Открыть mini-page создания контакта")
    public ContactCreationSteps openContactCreationMiniPage() {
        contactsPage.clickAddContactButton();
        cardPage.waitForMiniPage();
        return this;
    }

    @Step("Заполнить ФИО: {data.getLastName()} {data.getFirstName()} {data.getMiddleName()}")
    public ContactCreationSteps fillFullName(ContactData data) {
        field.setValue("Фамилия", data.getLastName());
        field.setValue("Имя", data.getFirstName());
        field.setValue("Отчество", data.getMiddleName());
        return this;
    }

    @Step("Заполнить дату рождения: {data.getBirthDate()}")
    public ContactCreationSteps fillBirthDate(ContactData data) {
        String formattedDate = data.getBirthDate().format(FORMATTER);
        field.setValue("Дата рождения", formattedDate);
        return this;
    }

    @Step("Заполнить мобильный телефон: {data.getMobilePhone()}")
    public ContactCreationSteps fillMobilePhone(ContactData data) {
        field.setValue("Мобильный телефон", data.getMobilePhone());
        return this;
    }

    @Step("Заполнить email: {data.getEmail()}")
    public ContactCreationSteps fillEmail(ContactData data) {
        field.setValue("Email", data.getEmail());
        return this;
    }

    @Step("Сохранить новый контакт")
    public ContactCreationSteps saveContact() {
        base.clickButtonByNameCheck("Сохранить");
        cardPage.waitForCardLoaded();
        return this;
    }

    @Step("Создать контакт полностью: {data.getFullName()}")
    public ContactCreationSteps createContact(ContactData data) {
        return openContactCreationMiniPage()
                .fillFullName(data)
                .fillBirthDate(data)
                .fillMobilePhone(data)
                .fillEmail(data)
                .saveContact();
    }
}


