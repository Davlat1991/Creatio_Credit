package core.base.common.address;


import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import core.base.BasePage;
import core.data.address.AddressData;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

/**
 * UI component for Creatio AddressPageV2.
 * Работает с lookup + soft-validation + server rebuild.
 */
public class AddressComponent extends BasePage {

    @Step("Выбрать значение '{value}' в адресном поле '{field}'")
    public AddressComponent selectLookup(AddressField field, String value) {

        SelenideElement input = $x(
                "//div[@data-item-marker='" + field.marker + "']//input[@type='text']"
        ).shouldBe(Condition.visible, Condition.enabled);

        // ввод
        input.click();
        input.clear();
        input.sendKeys(value);

        // listview
        SelenideElement listView = $$x("//div[contains(@class,'listview')]")
                .findBy(Condition.visible);

        SelenideElement item = listView
                .$x(".//li[normalize-space(.)='" + value + "']")
                .shouldBe(Condition.visible);

        item.click();

        // soft-validation (confirm может быть hidden)
        SelenideElement confirmBtn = input.closest(".base-edit")
                .$x(".//div[contains(@id,'soft-validation-confirm')]");

        if (confirmBtn.exists() && confirmBtn.isDisplayed()) {
            jsClick(confirmBtn);
        }

        // контроль
        input.shouldHave(Condition.exactValue(value));

        waitAddressRebuild();

        return this;
    }

    @Step("Заполнить текстовое адресное поле '{field}' значением '{value}'")
    public AddressComponent setText(AddressField field, String value) {

        SelenideElement input = $x(
                "//div[@data-item-marker='" + field.marker + "']//input"
        ).shouldBe(Condition.visible, Condition.enabled);

        input.click();
        input.clear();
        input.setValue(value);

        return this;
    }

    /**
     * Ждём, пока AddressPageV2 пересоберёт DOM после выбора lookup.
     */
    private void waitAddressRebuild() {
        $$x("//*[contains(@class,'loading') or contains(@class,'mask')]")
                .shouldBe(CollectionCondition.size(0));

        // осознанная пауза под Creatio
        Selenide.sleep(300);
    }


    @Step("Заполнить адрес из AddressData")
    public AddressComponent fill(AddressData data) {

        if (data.getCountry() != null) {
            selectLookup(AddressField.COUNTRY, data.getCountry());
        }
        if (data.getRegion() != null) {
            selectLookup(AddressField.REGION, data.getRegion());
        }
        if (data.getDistrict() != null) {
            selectLookup(AddressField.DISTRICT, data.getDistrict());
        }
        if (data.getCity() != null) {
            selectLookup(AddressField.CITY, data.getCity());
        }
        if (data.getStreetType() != null) {
            selectLookup(AddressField.STREET_TYPE, data.getStreetType());
        }

        if (data.getStreet() != null) {
            setText(AddressField.STREET, data.getStreet());
        }
        if (data.getHouse() != null) {
            setText(AddressField.HOUSE, data.getHouse());
        }
        if (data.getBuilding() != null) {
            setText(AddressField.BUILDING, data.getBuilding());
        }
        if (data.getApartment() != null) {
            setText(AddressField.APARTMENT, data.getApartment());
        }
        if (data.getPostalCode() != null) {
            setText(AddressField.INDEX, data.getPostalCode());
        }
        if (data.getRegistrationDate() != null) {
            setText(AddressField.REG_DATE, data.getRegistrationDate());
        }

        return this;
    }

}
