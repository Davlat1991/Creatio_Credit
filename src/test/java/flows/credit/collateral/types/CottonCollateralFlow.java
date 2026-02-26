package flows.credit.collateral.types;

import core.base.UiContext;
import core.data.collateral.CollateralData;
import core.data.collateral.types.CottonData;
import flows.credit.collateral.base.BaseCollateralFlow;
import core.ui.components.collateral.*;
import io.qameta.allure.Step;

public class CottonCollateralFlow extends BaseCollateralFlow {

    private final CollateralTabsComponent tabs;
    private final CollateralGridComponent grid;
    private final CollateralFormComponent form;
    private final CottonCharacteristicsComponent characteristics;
    private final CollateralAddressComponent address;
    private final CollateralValuationComponent valuation;

    public CottonCollateralFlow(UiContext ui) {
        super(ui);
        this.tabs = new CollateralTabsComponent(ui);
        this.grid = new CollateralGridComponent(ui);
        this.form = new CollateralFormComponent(ui);
        this.characteristics = new CottonCharacteristicsComponent(ui);
        this.address = new CollateralAddressComponent(ui);
        this.valuation = new CollateralValuationComponent(ui);
    }

    @Override
    @Step("Заполнение залога: Хлопок")
    public void fill(CollateralData data) {


        tabs.openCollateralTab();
        grid.addCollateral();
        grid.openPropertyLookup();

        form.selectType("Движимое имущество");
        form.selectSubType("Гарави пахта");
        form.setName("Залог хлопка");
        form.selectOwnership("Собственный");

        characteristics.fillTechnicalData();
        address.fillPropertyAddress();
        valuation.fillValuationCotton("555000", "777000", "Сомони Чумхурии Точикистон");

        form.save();
        form.close();

        ui.basePage
                .doubleClickByMarker("Обеспечение.Подтип");

    }
}