package flows.credit.collateral.types;

import core.base.UiContext;
import core.data.collateral.CollateralData;
import flows.credit.collateral.base.BaseCollateralFlow;
import core.ui.components.collateral.*;
import io.qameta.allure.Step;

public class MovablePropertyCollateralFlow extends BaseCollateralFlow {

    private final CollateralTabsComponent tabs;
    private final CollateralGridComponent grid;
    private final CollateralFormComponent form;
    private final MovableObjectsDetailComponent objectsDetail;
    private final PropertyAddressDetailComponent addressDetail;
    private final CollateralAddressComponent address;
    private final CollateralValuationComponent valuation;

    public MovablePropertyCollateralFlow(UiContext ui) {
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
    @Step("Заполнение залога: Движимое имущество")
    public void fill(CollateralData data) {
        tabs.openCollateralTab();

        grid.addCollateral();
        grid.openPropertyLookup();

        form.selectType("Движимое имущество");
        form.selectSubType("Гарави амволи манкул");
        form.setName("Залог движимого имущества");
        form.selectOwnership("Собственный");

        valuation.fillValuationMovable(
                "Производственное оборудование",
                "70000",
                "Сомони Чумхурии Точикистон",
                "2",
                "Новое оборудование");

        address.fillPropertyAddress();


        form.close();

        ui.basePage
                .doubleClickByMarker("Обеспечение.Подтип");
    }

}