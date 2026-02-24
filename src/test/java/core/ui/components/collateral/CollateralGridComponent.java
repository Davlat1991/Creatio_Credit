package core.ui.components.collateral;

import core.base.UiContext;
import io.qameta.allure.Step;

public class CollateralGridComponent {

    private final UiContext ui;

    public CollateralGridComponent(UiContext ui) {
        this.ui = ui;
    }

    @Step("Добавить запись залога")
    public void addCollateral() {
        ui.basePage
                .clickButtonById("FinAppPledgeDetailAddRecordButtonButton-imageEl")
                .clickElementByTagAndDIM("div", "PropertyData");
    }

    @Step("Открыть лупу выбора имущества")
    public void openPropertyLookup() {
        ui.basePage
                .clickLookupIconByInputId("FinAppPledgeDetailPropertyDataLookupEdit-el");
    }

    @Step("Добавить залоговую ценность")
    public void addCollateralValue() {
        ui.basePage
                .clickButtonById("EshCollateralValuesDetailAddRecordButtonButton-imageEl");
    }
}
