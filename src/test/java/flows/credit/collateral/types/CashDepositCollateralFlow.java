package flows.credit.collateral.types;

import core.base.UiContext;
import core.ui.components.collateral.CollateralFormComponent;
import core.ui.components.collateral.CollateralGridComponent;
import core.ui.components.collateral.CollateralTabsComponent;
import flows.credit.collateral.base.BaseCollateralFlow;
import io.qameta.allure.Step;

public class CashDepositCollateralFlow extends BaseCollateralFlow {

    private final CollateralTabsComponent tabs;
    private final CollateralGridComponent grid;
    private final CollateralFormComponent form;

    public CashDepositCollateralFlow(UiContext ui) {
        super(ui);
        this.tabs = new CollateralTabsComponent(ui);
        this.grid = new CollateralGridComponent(ui);
        this.form = new CollateralFormComponent(ui);
    }


    @Override
    @Step("Заполнение залога: Денежный вклад")
    public void fill() {

        // ПОКА ПУСТО — ЭТО НОРМАЛЬНО
        // Реальную логику добавим в TASK 2.4 (UI Components)

        System.out.println("Filling CASH DEPOSIT collateral");
    }

}