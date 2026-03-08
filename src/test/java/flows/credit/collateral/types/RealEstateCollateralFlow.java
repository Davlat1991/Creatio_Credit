package flows.credit.collateral.types;

import core.base.UiContext;
import core.data.collateral.CollateralData;
import core.data.collateral.types.RealEstateData;
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
    public void fill(CollateralData data) {

        RealEstateData realEstateData =
                (RealEstateData) data.getSpecificData();

        tabs.openCollateralTab();
        grid.addCollateral();
        grid.openPropertyLookup();

        form.selectType("Недвижимое имущество");
        form.selectSubType("Гарави амволи гайриманкул");
        form.setName("Залог недвижимого имущества");
        form.selectCondition("Новое");
        form.selectOwnership("Собственный");
        grid.addCollateralValue();
        grid.waitForPageLoaded();
        form.setName("Залог недвижимого имущества");
        form.selectPledger("Чаборова Дилафруз Кобилчоновна");


        characteristics.fillCharacteristics(
                realEstateData.getLivingArea(),
                realEstateData.getBuildYear(),
                realEstateData.getRooms(),
                realEstateData.getDescription(),
                realEstateData.getPropertyType(),
                realEstateData.getTotalArea()
        );

        address.fillPropertyAddress();

        valuation.fillValuationRealEstate(
                data.getMarketValue(),
                data.getLiquidationValue(),
                data.getCurrency()
        );

        form.close();
        form.setName("Залог недвижимого имущества");
        form.save();
        form.close();

        ui.basePage
                .doubleClickByMarker("Обеспечение.Подтип");
    }


}