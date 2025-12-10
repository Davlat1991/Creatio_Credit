package steps.contacts;


import io.qameta.allure.Step;
import core.base.common.components.FieldComponent;
import core.pages.contacts.ContactCardPage;
import core.base.BasePage;


public class ContactAddressSteps {

    private final FieldComponent field = new FieldComponent();
    private final BasePage base = new BasePage();
    private final ContactCardPage card = new ContactCardPage();

    @Step("Открыть адрес типа '{addressType}'")
    public ContactAddressSteps openAddressByType(String addressType) {
        card.openDetail("Адреса");
        card.openAddressRow(addressType);
        return this;
    }

    @Step("Заполнить адрес типа '{addressType}' — регион: '{region}', дата регистрации: '{date}'")
    public ContactAddressSteps fillAddress(String addressType, String region, String date) {

        openAddressByType(addressType);

        field.select("Регион", region);
        field.setValue("Дата регистрации", date);

        return this;
    }


    @Step("Создать новый адрес типа '{addressType}'")
    public ContactAddressSteps addNewAddress(String addressType) {
        card.openDetail("Адреса");
        card.clickAddAddressButton();
        card.selectAddressType(addressType);
        return this;
    }

    @Step("Сохранить адрес")
    public ContactAddressSteps saveAddress() {
        base.clickButtonByNameCheck("Сохранить");
        card.waitAddressSaved();
        return this;
    }

    @Step("Добавить и заполнить адрес типа '{addressType}'")
    public ContactAddressSteps createAddress(String addressType, String region, String date) {
        return addNewAddress(addressType)
                .fillAddress(addressType, region, date)
                .saveAddress();
    }
}


