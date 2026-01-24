package core.pages.ui;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import core.base.BasePage;
import core.base.common.components.ButtonsComponent;
import core.base.common.components.FieldComponent;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.CollectionCondition.*;

public class ContactCommunicationDetailPage extends BasePage {

    private final FieldComponent fieldComponent = new FieldComponent();
    private final ButtonsComponent buttonsComponent = new ButtonsComponent();

    private final SelenideElement detailWrapper =
            $("[data-item-marker='Средства связи']");

    private final SelenideElement detailCaption =
            detailWrapper.$(".ts-controlgroup-caption-wrap");

    private final SelenideElement addButton =
            $("[data-item-marker='AddTypedRecordButton']");

    private ElementsCollection communicationItems() {
        return $$("div.bnz-row-item-container");
    }

    // ===========================
    // PUBLIC API (РАЗДЕЛЕНО)
    // ===========================

    /**
     * ❗ ТОЛЬКО удаляет все записи
     * ❗ НИЧЕГО не добавляет
     */
    @Step("Удалить все средства связи")
    public void removeAllCommunications() {
        openDetailIfCollapsed();
        deleteAllCommunicationsSafely();
        communicationItems().shouldHave(size(0));
    }

    /**
     * ❗ ТОЛЬКО добавляет мобильный телефон
     * ❗ НЕ проверяет и НЕ удаляет старые
     */
    @Step("Добавить мобильный телефон")
    public void addSingleMobilePhone(String countryCode,
                                     String operatorCode,
                                     String number) {

        activateCommunicationDetail(); // 🔥 КЛЮЧ

        addButton.click();

        selectMobilePhoneType();

        fieldComponent
                .setValue("Код страны", countryCode)
                .setValue("Код оператора", operatorCode)
                .setValue("Номер", number);

        buttonsComponent.clickByName("Сохранить");
    }


    // ===========================
    // DETAIL STATE
    // ===========================

    private void openDetailIfCollapsed() {

        detailWrapper.shouldBe(visible);

        if (detailWrapper.has(cssClass("ts-controlgroup-collapsed"))) {
            jsClick(detailCaption);
            detailWrapper.shouldNotHave(cssClass("ts-controlgroup-collapsed"));
        }

        addButton.shouldBe(visible);
    }

    // ===========================
    // DELETE LOGIC (ТВОЯ, РАБОЧАЯ)
    // ===========================

    @Step("Удалить все существующие средства связи (без добавления)")
    private void deleteAllCommunicationsSafely() {

        int guard = 0;

        while (communicationItems().size() > 0 && guard < 10) {

            int before = communicationItems().size();

            deleteFirstCommunication();

            communicationItems().shouldHave(sizeLessThan(before));

            guard++;
        }
    }

    @Step("Удалить первую запись средства связи")
    private void deleteFirstCommunication() {

        ElementsCollection items = communicationItems();
        items.shouldHave(sizeGreaterThan(0));

        SelenideElement item = items.first()
                .scrollIntoView(true)
                .shouldBe(visible);

        SelenideElement typeButton =
                item.$(".detail-type-btn-user-class")
                        .shouldBe(visible);

        jsClick(typeButton);

        SelenideElement menu =
                $("ul.menu-wrap.menu")
                        .shouldBe(visible);

        menu.$("li.menu-item[data-tag='delete']")
                .shouldBe(visible)
                .click();
    }

    // ===========================
    // ADD TYPE
    // ===========================

    @Step("Выбрать тип 'Телефон → Мобильный телефон'")
    private void selectMobilePhoneType() {

        SelenideElement phone =
                $$("ul.menu-wrap.menu li.menu-item")
                        .findBy(text("Телефон"))
                        .shouldBe(visible);

        phone.hover();

        $$("ul.menu-wrap.menu li.menu-item")
                .findBy(text("Мобильный телефон"))
                .shouldBe(visible)
                .click();
    }


    @Step("Активировать деталь 'Средства связи'")
    private void activateCommunicationDetail() {

        detailWrapper.shouldBe(visible);

        // 1️⃣ Жёстко кликаем по маркеру детали
        detailWrapper
                .$("[id$='DetailControlGroup-marker']")
                .scrollIntoView(true)
                .click();

        // 2️⃣ Контроль: кнопка + должна быть внутри этой детали
        detailWrapper
                .$("[data-item-marker='AddTypedRecordButton']")
                .shouldBe(visible);
    }

}
