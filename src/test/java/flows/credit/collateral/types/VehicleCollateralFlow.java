package flows.credit.collateral.types;

import core.base.UiContext;
import core.data.collateral.CollateralData;
import flows.credit.collateral.base.BaseCollateralFlow;
import core.ui.components.collateral.*;
import io.qameta.allure.Step;

public class VehicleCollateralFlow extends BaseCollateralFlow {

    private final CollateralTabsComponent tabs;
    private final CollateralGridComponent grid;
    private final CollateralFormComponent form;
    private final VehicleCharacteristicsComponent vehicle;
    private final CollateralAddressComponent address;
    private final CollateralValuationComponent valuation;

    public VehicleCollateralFlow(UiContext ui) {
        super(ui);
        this.tabs = new CollateralTabsComponent(ui);
        this.grid = new CollateralGridComponent(ui);
        this.form = new CollateralFormComponent(ui);
        this.vehicle = new VehicleCharacteristicsComponent(ui);
        this.address = new CollateralAddressComponent(ui);
        this.valuation = new CollateralValuationComponent(ui);
    }

    @Override
    @Step("Заполнение залога: Транспорт (движимое имущество)")
    public void fill(CollateralData data) {

        tabs.openCollateralTab();

        grid.addCollateral();
        grid.openPropertyLookup();

        form.selectType("Движимое имущество");
        form.selectSubType("Гарави наклиёт");
        form.setName("Залог транспорта");
        form.selectCondition("Новое");
        form.selectOwnership("Собственный");
        //form.selectPledger("Рачабов Бахром Назаралиевич");

        grid.addCollateralValue();

        form.setName("Залог транспорта");
        form.selectPledger("Кудусов Фатхулло Абдуфатоевич");

        vehicle.fillTechnicalData();
        address.fillPropertyAddress();
        valuation.fillValuationVehicle("500000", "600000", "Сомони Чумхурии Точикистон");

        form.close();
        form.close();

        ui.basePage
                .doubleClickByMarker("Обеспечение.Подтип");
    }
}
