package flows.credit.collateral.types;

import com.codeborne.selenide.Selenide;
import core.base.UiContext;
import core.data.collateral.CollateralData;
import core.data.collateral.types.GoldData;
import flows.credit.collateral.base.BaseCollateralFlow;
import core.ui.components.collateral.*;
import io.qameta.allure.Step;

public class GoldCollateralFlow extends BaseCollateralFlow {

    private final CollateralTabsComponent tabs;
    private final CollateralGridComponent grid;
    private final CollateralFormComponent form;
    private final GoldCharacteristicsComponent goldCharacteristics;
    private final CollateralValuationComponent valuation;

    public GoldCollateralFlow(UiContext ui) {
        super(ui);
        this.tabs = new CollateralTabsComponent(ui);
        this.grid = new CollateralGridComponent(ui);
        this.form = new CollateralFormComponent(ui);
        this.goldCharacteristics = new GoldCharacteristicsComponent(ui);
        this.valuation = new CollateralValuationComponent(ui);
    }

    @Override
    @Step("Заполнение залога: Золотые изделия")
    public void fill(CollateralData data) {

        Selenide.sleep (3000);
        tabs.openCollateralTab();
        grid.addCollateral();
        grid.openPropertyLookup();

        form.selectType("Движимое имущество");
        form.selectSubType("Гарави дорои");
        form.setName("Золотые изделия");
        form.selectOwnership("Собственный");

        valuation.fillValuationGold(
                "Золотое украшение",
                "999",
                "50",
                "60",
                "50000",
                "Сомони Чумхурии Точикистон");

        form.close();

        ui.basePage
                .doubleClickByMarker("Обеспечение.Подтип");
    }
}