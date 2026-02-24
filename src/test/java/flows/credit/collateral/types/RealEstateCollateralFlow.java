package flows.credit.collateral.types;

import core.base.UiContext;
import core.ui.components.collateral.*;
import flows.credit.collateral.base.BaseCollateralFlow;
import io.qameta.allure.Step;

public class RealEstateCollateralFlow extends BaseCollateralFlow {

    private final CollateralTabsComponent tabs;
    private final CollateralGridComponent grid;
    private final CollateralFormComponent form;
    private final RealEstateCharacteristicsComponent characteristics;
    private final CollateralAddressComponent address;
    private final CollateralValuationComponent valuation;

    public RealEstateCollateralFlow(UiContext ui) {
        super(ui);
        this.tabs = new CollateralTabsComponent(ui);
        this.grid = new CollateralGridComponent(ui);
        this.form = new CollateralFormComponent(ui);
        this.characteristics = new RealEstateCharacteristicsComponent(ui);
        this.address = new CollateralAddressComponent(ui);
        this.valuation = new CollateralValuationComponent(ui);
    }

    @Override
    @Step("Заполнение залога: Недвижимое имущество")
    public void fill() {

        tabs.openCollateralTab();
        grid.addCollateral();

        form.selectType("Недвижимое имущество");
        form.selectSubType("Гарави амволи гайриманкул");
        form.selectCondition("Новое");

        characteristics.fillCharacteristics(
                "80",
                "2015",
                "3",
                "Квартира в центре города",
                "Квартира",
                "100"
        );

        address.fillPropertyAddress();

        valuation.fillValuation(
                "1000000",
                "1200000",
                "Сомони Чумхурии Точикистон"
        );

        form.close();
    }
}