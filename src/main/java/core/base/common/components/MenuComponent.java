package core.base.common.components;

import static com.codeborne.selenide.Selenide.$x;

public class MenuComponent {

    public MenuComponent clickButtonByLiName(String value) {
        $x("//li[contains(text(), '" + value + "')]").click();
        return this;
    }

}
