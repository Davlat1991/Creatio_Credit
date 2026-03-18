package core.ui.components.collateral;

import core.base.UiContext;
import io.qameta.allure.Step;

public class CollateralTabsComponent {

    private final UiContext ui;

    public CollateralTabsComponent(UiContext ui) {
        this.ui = ui;
    }

    @Step("Открыть вкладку «Обеспечение»")
    public void openCollateralTab() {
        ui.buttonsComponent.clickButtonByContainName1("Обеспечение");

    }
}
