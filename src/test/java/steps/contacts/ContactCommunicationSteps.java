package steps.contacts;


import io.qameta.allure.Step;
import core.pages.contacts.ContactCardPage;
import core.base.BasePage;
import core.base.common.components.FieldComponent;
import core.base.common.components.LookupComponent;

public class ContactCommunicationSteps {

    private final ContactCardPage card = new ContactCardPage();
    private final FieldComponent field = new FieldComponent();
    private final LookupComponent lookup = new LookupComponent();
    private final BasePage base = new BasePage();

    /*@Step("Открыть деталь 'Коммуникации'")
    public ContactCommunicationSteps openCommunications() {
        card.openDetail("Коммуникации");
        card.waitForDetailLoaded("Коммуникации");
        return this;
    }

    @Step("Добавить новую коммуникацию")
    public ContactCommunicationSteps addCommunication() {
        card.clickAddRecordInDetail("Коммуникации");
        card.waitForMiniPage();
        return this;
    }*/

    @Step("Выбрать тип коммуникации: {type}")
    public ContactCommunicationSteps selectCommunicationType(String type) {
        lookup.selectLookup("Тип", type);
        return this;
    }

    @Step("Указать вид коммуникации: {kind}")
    public ContactCommunicationSteps selectCommunicationKind(String kind) {
        lookup.selectLookup("Вид коммуникации", kind);
        return this;
    }

    @Step("Заполнить значение коммуникации: '{value}'")
    public ContactCommunicationSteps fillCommunicationValue(String value) {

        field.setValue("Номер/Адрес", value);

        return this;
    }


    @Step("Сохранить коммуникацию")
    public ContactCommunicationSteps saveCommunication() {
        base.clickButtonByNameCheck("Сохранить");
        card.waitForCardLoaded();
        return this;
    }

   /* @Step("Добавить коммуникацию: Тип = {type}, Вид = {kind}, Значение = {value}")
    public ContactCommunicationSteps createCommunication(String type, String kind, String value) {
        return openCommunications()
                .addCommunication()
                .selectCommunicationType(type)
                .selectCommunicationKind(kind)
                .fillCommunicationValue(value)
                .saveCommunication();
    }

    @Step("Проверить, что коммуникация '{value}' отображается в реестре коммуникаций")
    public ContactCommunicationSteps validateCommunicationExists(String value) {
        card.assertRowExistsInDetail("Коммуникации", value);
        return this;
    }*/
}

