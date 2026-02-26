package flows.credit.collateral.types;

import core.base.UiContext;
import core.data.collateral.CollateralData;
import core.data.collateral.types.MovablePropertyData;
import flows.credit.collateral.base.BaseCollateralFlow;
import core.ui.components.collateral.*;
import io.qameta.allure.Step;

public class AcquiredPropertyCollateralFlow extends BaseCollateralFlow {

    private final CollateralTabsComponent tabs;
    private final CollateralGridComponent grid;
    private final CollateralFormComponent form;
    private final MovableObjectsDetailComponent objectsDetail;
    private final PropertyAddressDetailComponent addressDetail;
    private final CollateralAddressComponent address;
    private final CollateralValuationComponent valuation;

    public AcquiredPropertyCollateralFlow(UiContext ui) {
        super(ui);
        this.tabs = new CollateralTabsComponent(ui);
        this.grid = new CollateralGridComponent(ui);
        this.form = new CollateralFormComponent(ui);
        this.objectsDetail = new MovableObjectsDetailComponent(ui);
        this.addressDetail = new PropertyAddressDetailComponent(ui);
        this.address = new CollateralAddressComponent(ui);
        this.valuation = new CollateralValuationComponent(ui);
    }

    @Override
    @Step("Заполнение залога: Приобретённое имущество")
    public void fill(CollateralData data) {


        tabs.openCollateralTab();
        grid.addCollateral();
        grid.openPropertyLookup();

        form.selectType("Движимое имущество");
        form.selectSubType("Моли бадастомада");
        form.setName("Приобретённое имущество");
        form.selectOwnership("Собственный");

        valuation.fillValuationAcquiredProperty(
                "Новое оборудование",
                "200000",
                "Сомони Чумхурии Точикистон",
                "6",
                "Оборудование для тканей");


        address.fillPropertyAddress();

        form.close();

        ui.basePage
                .doubleClickByMarker("Обеспечение.Подтип");
    }
}