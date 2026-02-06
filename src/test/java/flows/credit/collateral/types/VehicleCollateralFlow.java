package flows.credit.collateral.types;

import core.base.UiContext;
import core.context.CollateralTestContext;
import core.ui.components.collateral.CollateralFormComponent;
import core.ui.components.collateral.CollateralTabsComponent;
import flows.credit.collateral.base.BaseCollateralFlow;

public class VehicleCollateralFlow extends BaseCollateralFlow {

    private final CollateralFormComponent form;
    private final CollateralTabsComponent tabs;

    public VehicleCollateralFlow(UiContext ui) {
        super(ui);
        this.form = new CollateralFormComponent(ui);
        this.tabs = new CollateralTabsComponent(ui);
    }

    @Override
    public void fill() {
        tabs.openMainTab();
        form.setCollateralValue("150000");
    }
}

