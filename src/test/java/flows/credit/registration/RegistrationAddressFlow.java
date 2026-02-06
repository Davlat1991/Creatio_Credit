package flows.credit.registration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import core.base.UiContext;
import core.base.common.address.AddressField;
import core.utils.AllureAttachments;
import core.utils.LogStep;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class RegistrationAddressFlow {

    private final UiContext ui;


    public RegistrationAddressFlow(UiContext ui) {
        this.ui = ui;

    }

    @Step("Заполнение адресов")
    public void fillAddresses() {

        // Регистрация

        ui.buttonsComponent
                .doubleclickButtonByName("Регистрация");
        ui.contactAddressPage
                .waitForAddressPageLoaded();

        ui.dateFieldComponent
                .setDateFieldByMarker("BnzRegistrationDate", "01.01.2020");
        ui.contractPage
                .clickButtonByNameCheck("Сохранить");

        ui.basePage
                .waitForPage();

        // Фактический
        ui.lookupComponent
                .setHandBookFieldByValueCheck("Тип клиента", "Такрори");

        ui.buttonsComponent
                .doubleclickButtonByName("Фактический");
        ui.contactAddressPage
                .waitForAddressPageLoaded();
        ui.dateFieldComponent
                .setDateFieldByMarker("BnzRegistrationDate", "01.01.2020");
        ui.contractPage
                .clickButtonByNameCheck("Сохранить");
        ui.basePage
                .waitForPage();
        //Средства связи
        ui.lookupComponent
                .selectDropdownValueWithCheckNew("BnzAffiliation", "Мобильный");

        //ctx.contactCommunicationDetailPage.removeAllCommunications(); //Удаление всех записей детали "Средства связи"



        /*ui.contactCommunicationDetailPage.addSingleMobilePhone(
                "+992",
                "92",
                "9292929"
        );*/ //Добавление новыз записей в детали "Средства связи" - нужно доработать метод!!!


    }

    // Связь с ПЗЛ
    // (true) выбрать "Да"
    // (false); выбрать "Нет"
    public void setPzlRelation(boolean expectedValue) {

        String value = expectedValue ? "Да" : "Нет";

        LogStep.info("⏳ Установка значения '" + value + "' в поле 'Связь с ПЗЛ'");

        SelenideElement pzlBlock = $("[data-item-marker='ПЗЛ']")
                .shouldBe(Condition.visible);

        // 📎 Прикладываем DOM блока ПЗЛ
        AllureAttachments.attachHtml(
                "🧩 ПЗЛ DOM (до)",
                pzlBlock.getAttribute("outerHTML")
        );

        SelenideElement yesRadio = pzlBlock
                .find("[data-item-marker='BnzPZLTrue']")
                .closest(".radio-button-container")
                .find(".t-radio-wrap");

        SelenideElement noRadio = pzlBlock
                .find("[data-item-marker='BnzPZLFalse']")
                .closest(".radio-button-container")
                .find(".t-radio-wrap");

        boolean isYesSelectedBefore = yesRadio.has(Condition.cssClass("t-radio-checked"));

        if (expectedValue && !isYesSelectedBefore) {
            yesRadio.click();
        }

        if (!expectedValue && isYesSelectedBefore) {
            noRadio.click();
        }

        if (expectedValue) {
            yesRadio.shouldHave(Condition.cssClass("t-radio-checked"));
        } else {
            noRadio.shouldHave(Condition.cssClass("t-radio-checked"));
        }

        // 📎 DOM после
        AllureAttachments.attachHtml(
                "🧩 ПЗЛ DOM (до)",
                pzlBlock.getAttribute("outerHTML")
        );

        LogStep.info("✔ Поле 'Связь с ПЗЛ' успешно установлено в значение '" + value + "'");
    }


    @Step("Заполнение адреса места работы")
    public void fillWorkAddressSelfEmployed() {

        // 0️⃣ Открываем detail и нажимаем "+"
        ui.detailPage.clickAddRecordInDetail("Адрес места работы");

        ui.addressComponent
                .selectLookup(AddressField.COUNTRY, "Точикистон")    //Страна
                .selectLookup(AddressField.REGION, "Вилояти Сугд")   //Регион
                .selectLookup(AddressField.DISTRICT, "Хучанд")       //Район
                .selectLookup(AddressField.CITY, "ш. Хучанд")        //Населенный пункт
                .selectLookup(AddressField.STREET_TYPE, "Проспект")  //Тип улицы
                .setText(AddressField.STREET, "Исмоили Сомони")      //Улица
                .setText(AddressField.HOUSE, "19")                   //Дом
                .setText(AddressField.BUILDING, "2/7")               //Корпус
                .setText(AddressField.APARTMENT, "48")               //Квартира/Офис
                .setText(AddressField.INDEX, "735700")               //Индекс
                .setText(AddressField.REG_DATE, "01.01.2020")        //Дата регистрации
        ;

        ui.contractPage
                .clickButtonByNameCheck("Сохранить");

    }


    @Step("Заполнение адреса места работы")
    public void fillWorkAddressEmployee() {

        // 0️⃣ Открываем detail и нажимаем "+"
        ui.detailPage.clickAddRecordInDetail("Адрес места работы");

        ui.addressComponent
                .selectLookup(AddressField.COUNTRY, "Точикистон")    //Страна
                .selectLookup(AddressField.REGION, "Вилояти Сугд")   //Регион
                .selectLookup(AddressField.DISTRICT, "Хучанд")       //Район
                .selectLookup(AddressField.CITY, "ш. Хучанд")        //Населенный пункт
                .selectLookup(AddressField.STREET_TYPE, "Проспект")  //Тип улицы
                .setText(AddressField.STREET, "Исмоили Сомони")      //Улица
                .setText(AddressField.HOUSE, "19")                   //Дом
                .setText(AddressField.BUILDING, "2/7")               //Корпус
                .setText(AddressField.APARTMENT, "48")               //Квартира/Офис
                .setText(AddressField.INDEX, "735700")               //Индекс
                .setText(AddressField.REG_DATE, "01.01.2020")        //Дата регистрации
        ;

        ui.contractPage
                .clickButtonByNameCheck("Сохранить");

    }
}
