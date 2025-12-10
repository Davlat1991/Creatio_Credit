package core.pages.contacts;

import com.codeborne.selenide.Condition;
import core.base.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class ContactCardPage extends BasePage {

    public ContactCardPage openDetail(String detailName) {
        $x("//span[text()='" + detailName + "']")
                .scrollIntoView(true)
                .click();
        return this;
    }

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

    public ContactCardPage openDetailTitle(String detailName) {
        $x("//span[@title='" + detailName + "']")
                .scrollIntoView(true)
                .click();
        return this;
    }

    public ContactCardPage openDetailRow(String rowName) {
        $x("//div[contains(@class,'grid')]//div[text()='" + rowName + "']")
                .shouldBe(visible)
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

    public ContactCardPage openDetailContain(String detailName) {
        $x("//span[contains(@class,'detail-title')][text()='" + detailName + "']")
                .scrollIntoView(true)
                .click();
        return this;
    }

    public ContactCardPage clickAddRecordInDetail(String detailName) {
        $x("//span[text()='" + detailName + "']/ancestor::div[contains(@class,'detail')]//span[@data-item-marker='AddRecordButton']")
                .click();
        return this;
    }

    public ContactCardPage waitForDetailLoaded(String detailName) {
        $x("//span[text()='" + detailName + "']")
                .shouldBe(visible);
        return this;
    }

    public ContactCardPage assertRowExistsInDetail(String detail, String value) {
        $x("//span[text()='" + detail + "']/ancestor::div[contains(@class,'detail')]//div[text()='" + value + "']")
                .shouldBe(visible);
        return this;
    }

    public ContactCardPage waitForMiniPage() {
        $x("//*[@data-item-marker='MiniPage']")
                .shouldBe(Condition.visible);
        return this;
    }





}

