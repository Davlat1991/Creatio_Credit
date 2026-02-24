package flows.credit.collateral.types;


import core.base.UiContext;
import core.ui.components.collateral.*;
import flows.credit.collateral.base.BaseCollateralFlow;
import io.qameta.allure.Step;

public class GoodsCollateralFlow extends BaseCollateralFlow {

    private final CollateralTabsComponent tabs;
    private final CollateralGridComponent grid;
    private final CollateralFormComponent form;
    private final CollateralAddressComponent address;
    private final CollateralValuationComponent valuation;

    public GoodsCollateralFlow(UiContext ui) {
        super(ui);
        this.tabs = new CollateralTabsComponent(ui);
        this.grid = new CollateralGridComponent(ui);
        this.form = new CollateralFormComponent(ui);
        this.address = new CollateralAddressComponent(ui);
        this.valuation = new CollateralValuationComponent(ui);
    }

    @Override
    @Step("Заполнение залога: Товары в обороте и переработке")
    public void fill() {
        tabs.openCollateralTab();

        grid.addCollateral();
        grid.openPropertyLookup();

        form.selectType("Движимое имущество");
        form.selectSubType("Гарави амволи дар муомилотбуда");
        form.selectOwnership("Собственный");

        valuation.fillValuationGoods("Кухонный набор", "600000", "Сомони Чумхурии Точикистон","4");

        address.fillPropertyAddress();


        form.close();
    }


}
