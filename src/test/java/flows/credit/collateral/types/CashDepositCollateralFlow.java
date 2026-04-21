package flows.credit.collateral.types;

import core.base.UiContext;
import core.data.collateral.CollateralData;
import core.ui.components.collateral.CollateralFormComponent;
import core.ui.components.collateral.CollateralGridComponent;
import core.ui.components.collateral.CollateralTabsComponent;
import core.ui.components.collateral.CollateralValuationComponent;
import flows.credit.collateral.base.BaseCollateralFlow;
import io.qameta.allure.Step;

public class CashDepositCollateralFlow extends BaseCollateralFlow {

    private final CollateralTabsComponent tabs;
    private final CollateralGridComponent grid;
    private final CollateralFormComponent form;
    private final CollateralValuationComponent valuation;

    public CashDepositCollateralFlow(UiContext ui) {
        super(ui);
        this.tabs = new CollateralTabsComponent(ui);
        this.grid = new CollateralGridComponent(ui);
        this.form = new CollateralFormComponent(ui);
        this.valuation = new CollateralValuationComponent(ui);
    }


    @Override
    @Step("Заполнение залога: Денежный вклад")
    public void fill(CollateralData data) {

        tabs.openCollateralTab();

        grid.addCollateral();
        grid.openPropertyLookup();

        form.selectType("Денежные средства (депозитные счета)");
        form.selectSubType("Гарави пасандоз");
        form.setName("Денежный вклад");
        form.selectOwnership("Третьего лица"); // Собственный
        form.selectPledger("Хамидов Азизходжа Азамходжаевич");


        valuation.fillValuationCashDeposit(
                "1000",
                "Сомони Чумхурии Точикистон");


        form.save();
        ui.basePage
                .doubleClickByMarker("Залоговая стоимость");
        form.close();

        ui.basePage
                .doubleClickByMarker("Обеспечение.Подтип");
    }


}