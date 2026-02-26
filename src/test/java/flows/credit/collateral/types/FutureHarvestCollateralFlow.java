package flows.credit.collateral.types;

import core.base.UiContext;
import core.data.collateral.CollateralData;
import core.data.collateral.types.FutureHarvestData;
import flows.credit.collateral.base.BaseCollateralFlow;
import core.ui.components.collateral.*;
import io.qameta.allure.Step;

public class FutureHarvestCollateralFlow extends BaseCollateralFlow {

    private final CollateralTabsComponent tabs;
    private final CollateralGridComponent grid;
    private final CollateralFormComponent form;
    private final MovableObjectsDetailComponent objectsDetail;
    private final CollateralAddressComponent addressComponent;
    private final CollateralAddressComponent address;
    private final CollateralValuationComponent valuation;

    public FutureHarvestCollateralFlow(UiContext ui) {
        super(ui);
        this.tabs = new CollateralTabsComponent(ui);
        this.grid = new CollateralGridComponent(ui);
        this.form = new CollateralFormComponent(ui);
        this.objectsDetail = new MovableObjectsDetailComponent(ui);
        this.addressComponent = new CollateralAddressComponent(ui);
        this.address = new CollateralAddressComponent(ui);
        this.valuation = new CollateralValuationComponent(ui);
    }

    @Override
    @Step("Заполнение залога: Будущий урожай")
    public void fill(CollateralData data) {

        tabs.openCollateralTab();
        grid.addCollateral();
        grid.openPropertyLookup();

        form.selectType("Движимое имущество");
        form.selectSubType("Гарави хосили оянда");
        form.setName("Будущий урожай");
        form.selectOwnership("Собственный");


        valuation.fillValuationFutureHarvest(
                "Хосили картошка",
                "110000",
                "Сомони Чумхурии Точикистон",
                "4",
                "Ранний сорт картошки");

        address.fillPropertyAddress();


        form.close();

        ui.basePage
                .doubleClickByMarker("Обеспечение.Подтип");
    }

}
