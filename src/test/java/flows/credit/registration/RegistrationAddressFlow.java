package flows.credit.registration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import core.base.TestContext;
import core.pages.ui.DetailPage;
import core.utils.AllureAttachments;
import core.utils.LogStep;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static core.base.common.components.LookupComponent.log;

public class RegistrationAddressFlow {

    private final TestContext ctx;


    public RegistrationAddressFlow(TestContext ctx) {
        this.ctx = ctx;



    }

    @Step("Заполнение адресов")
    public void fillAddresses() {

        // Регистрация

        ctx.buttonsComponent
                .doubleclickButtonByName("Регистрация");
        ctx.contactAddressPage
                .waitForAddressPageLoaded();

        ctx.dateFieldComponent
                .setDateFieldByMarker("BnzRegistrationDate", "01.01.2020");
        ctx.contractPage
                .clickButtonByNameCheck("Сохранить");

        ctx.basePage
                .waitForPage();

        //Средства связи
        ctx.lookupComponent
                .selectDropdownValueWithCheck("BnzAffiliation", "Мобильный");

        //ctx.contactCommunicationDetailPage.removeAllCommunications(); //Удаление всех записей детали "Средства связи"



        /*ctx.contactCommunicationDetailPage.addSingleMobilePhone(
                "+992",
                "92",
                "9292929"
        );*/ //Добавление новыз записей в детали "Средства связи" - нужно доработать метод!!!

        // Фактический
        ctx.lookupComponent
                .setHandBookFieldByValueCheck("Тип клиента", "Такрори");
        ctx.buttonsComponent
                .doubleclickButtonByName("Фактический");
        ctx.contactAddressPage
                .waitForAddressPageLoaded();

        ctx.dateFieldComponent
                .setDateFieldByMarker("BnzRegistrationDate", "01.01.2020");
        ctx.contractPage
                .clickButtonByNameCheck("Сохранить");
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
    public void fillWorkAddress() {

        // 0️⃣ Открываем detail и нажимаем "+"
        ctx.detailPage.clickAddRecordInDetail("Адрес места работы");

        //Страна
        ctx.lookupComponent
                .selectAddressLookup(
                        "Country",
                        "Точикистон"
                );

        //Регион
        ctx.lookupComponent
                .selectAddressLookup(
                        "Region",
                        "Вилояти Сугд"
                );

        //Район
        ctx.lookupComponent
                .selectAddressLookup(
                        "City",
                        "Хучанд"
                );

        //Населенный пункт
        ctx.lookupComponent
                .selectAddressLookup(
                        "BnzSettlement",
                        "ш. Хучанд"
                );

        //Тип улицы
        ctx.lookupComponent
                .selectAddressLookup(
                        "TsiStreetType",
                        "Проспект"
                );

        //Street
        ctx.lookupComponent
                .setFieldByValueCheck(
                        "Улица",
                        "Исмоили Сомони 330"
                );

        //Building1
        ctx.lookupComponent
                .setFieldByValueCheck(
                        "Дом",
                        "19"
                );


        //BnzHousing
        ctx.lookupComponent
                .setFieldByValueCheck(
                        "Корпус",
                        "2/7"
                );

        ctx.lookupComponent
                .setFieldByValueCheck(
                        "Квартира/Офис",
                        "48"
                );

        ctx.lookupComponent
                .setFieldByValueCheck(
                        "Индекс",
                        "735700"
                );

        //Дата регистрации
        ctx.dateFieldComponent
                .setDateFieldByMarker("BnzRegistrationDate", "01.01.2020");
        ctx.contractPage
                .clickButtonByNameCheck("Сохранить");

    }
}
