package core.base.common.helpers;

import core.base.common.components.FieldComponent;

import static com.codeborne.selenide.Selenide.$x;

public class DomActions {

    public DomActions clickDivbyId(String nameField , String value) {
        $x("//div[@id='" + nameField + "']/input").setValue(value).click();
        return this;
    }
}
