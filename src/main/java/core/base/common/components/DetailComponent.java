package core.base.common.components;

import core.pages.contacts.ContactCardPage;
import core.pages.credit.ContractCreditApplicationPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class DetailComponent {

    public DetailComponent openDetailMenu(String detailName) {
        $x("//span[.='" + detailName + "']/../..//span[@data-item-marker='ToolsButton']//span[contains(@class,\"menuWrap\")]").click();
        return this;
    }

    public DetailComponent openDetail(String detailName) {
        $x("//span[text()='" + detailName + "']")
                .scrollIntoView(true)
                .click();
        return this;
    }

    public DetailComponent openDetailTitle(String detailName) {
        $x("//span[@title='" + detailName + "']")
                .scrollIntoView(true)
                .click();
        return this;
    }

    public DetailComponent openDetailContain(String detailName) {
        $x("//span[contains(@class,'detail-title')][text()='" + detailName + "']")
                .scrollIntoView(true)
                .click();
        return this;
    }

    public DetailComponent clickAddRecordInDetail(String detailName) {
        $x("//span[text()='" + detailName + "']/ancestor::div[contains(@class,'detail')]//span[@data-item-marker='AddRecordButton']")
                .click();
        return this;
    }


    public DetailComponent waitForDetailLoaded(String detailName) {
        $x("//span[text()='" + detailName + "']")
                .shouldBe(visible);
        return this;
    }

    public DetailComponent assertRowExistsInDetail(String detail, String value) {
        $x("//span[text()='" + detail + "']/ancestor::div[contains(@class,'detail')]//div[text()='" + value + "']")
                .shouldBe(visible);
        return this;
    }

}
