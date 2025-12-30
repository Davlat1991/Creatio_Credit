package core.base.common.components;

import core.pages.credit.ContractCreditApplicationPage;

import static com.codeborne.selenide.Selenide.$x;

public class DetailComponent {

    public DetailComponent openDetailMenu(String detailName) {
        $x("//span[.='" + detailName + "']/../..//span[@data-item-marker='ToolsButton']//span[contains(@class,\"menuWrap\")]").click();
        return this;
    }

}
