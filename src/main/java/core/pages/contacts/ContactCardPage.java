package core.pages.contacts;

import com.codeborne.selenide.Condition;
import core.base.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class ContactCardPage extends BasePage {


    public ContactCardPage openAddressRow(String addressType) {
        $x("//div[contains(@class,'grid')]//div[text()='" + addressType + "']")
                .click();
        return this;
    }

    public ContactCardPage clickAddAddressButton() {
        $x("//span[@data-item-marker='AddRecordButton']").click();
        return this;
    }

    public ContactCardPage selectAddressType(String type) {
        $x("//li[normalize-space()='" + type + "']").click();
        return this;
    }

    public ContactCardPage waitAddressSaved() {
        $x("//*[@data-item-marker='EntityLoaded']").shouldBe(visible);
        return this;
    }

    public ContactCardPage openTab(String tabName) {
        $x("//span[contains(@class,'tab-label')][.='" + tabName + "']")
                .scrollIntoView(true)
                .click();
        return this;
    }


    public ContactCardPage waitForCardLoaded() {
        $x("//*[@data-item-marker='EntityLoaded']")
                .shouldBe(visible);
        return this;
    }

    public ContactCardPage waitForEditMode() {
        $x("//textarea | //input")
                .shouldBe(visible);
        return this;
    }







}

