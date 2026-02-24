package flows.credit.collateral.types;


import core.base.UiContext;
import core.ui.components.collateral.*;
import flows.credit.collateral.base.BaseCollateralFlow;
import io.qameta.allure.Step;

public class EquipmentCollateralFlow extends BaseCollateralFlow {

    private final CollateralTabsComponent tabs;
    private final CollateralGridComponent grid;
    private final CollateralFormComponent form;
    private final CollateralAddressComponent address;
    private final CollateralValuationComponent valuation;

    public EquipmentCollateralFlow(UiContext ui) {
        super(ui);
        this.tabs = new CollateralTabsComponent(ui);
        this.grid = new CollateralGridComponent(ui);
        this.form = new CollateralFormComponent(ui);
        this.address = new CollateralAddressComponent(ui);
        this.valuation = new CollateralValuationComponent(ui);
    }

    @Override
    @Step("Заполнение залога: Оборудование")
    public void fill() {

        tabs.openCollateralTab();

        grid.addCollateral();
        grid.openPropertyLookup();

        form.selectType("Движимое имущество");
        form.selectSubType("Гарави тачхизот");
        form.selectOwnership("Собственный");

        valuation.fillValuationEquipment(
                "Прицеп",
                "60000",
                "Сомони Чумхурии Точикистон",
                "БЕЛОРУССИЯ","2025",
                "Промышленное оборудование");

        address.fillPropertyAddress();


        form.close();
    }

}

