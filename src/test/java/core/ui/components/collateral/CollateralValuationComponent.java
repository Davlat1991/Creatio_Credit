package core.ui.components.collateral;

import core.base.UiContext;
import core.data.collateral.types.GoldData;
import io.qameta.allure.Step;

public class CollateralValuationComponent {

    private final UiContext ui;

    public CollateralValuationComponent(UiContext ui) {
        this.ui = ui;
    }

    @Step("Оценка залога")
    public void fillValuationVehicle(String assessed, String market, String currency) {

        ui.buttonsComponent
                .clickButtonByContainName("Оценка");

        ui.basePage
                .clickButtonById("PledgeRatingDetailAddRecordButtonButton-imageEl");

        ui.domActions
                .clickDivbyId("PledgeRatingDetailRatingSumFloatEdit-wrap", assessed)
                .clickDivbyId("PledgeRatingDetailMarketSumFloatEdit-wrap", market);
        ui.lookupComponent
                .setfieldScheduleDetailByDIM("EshCurrency", currency);
        ui.menuComponent
                .clickButtonByLiName(currency);
        ui.basePage
                .clickButtonByDataItemMaker("save");
    }


    @Step("Оценка залога")
    public void fillValuationCotton(String assessed, String market, String currency) {

        ui.buttonsComponent
                .clickButtonByContainName("Оценка");

        ui.basePage
                .clickButtonById("PledgeRatingDetailAddRecordButtonButton-imageEl");

        ui.domActions
                .clickDivbyId("PledgeRatingDetailRatingSumFloatEdit-wrap", assessed)
                .clickDivbyId("PledgeRatingDetailMarketSumFloatEdit-wrap", market);
        ui.lookupComponent
                .setfieldScheduleDetailByDIM("EshCurrency", currency);
        ui.menuComponent
                .clickButtonByLiName(currency);
        ui.basePage
                .clickButtonByDataItemMaker("save");
    }


    @Step("Объекты обеспечения")
    public void fillValuationGoods(String name, String sum, String currency, String amount, String description) {

        ui.basePage
                .clickButtonById("EshPropertyDataObjectsDetailAddRecordButtonButton-imageEl");
        ui.domActions
                .setGridInputValue("EshPropertyDataObjectsDetailNameTextEdit-el", name)
                .clickDivbyId("EshPropertyDataObjectsDetailAmountFloatEdit-wrap", sum);
        ui.lookupComponent
                .setfieldScheduleDetailByDIM("Currency", currency);
        ui.menuComponent
                .clickButtonByLiName(currency);
        ui.domActions
                .setGridInputValue("EshPropertyDataObjectsDetailCountIntegerEdit-el", amount);
        ui.domActions
                .setGridInputValue("EshPropertyDataObjectsDetailDescriptionTextEdit-el", description);
        ui.basePage
                .clickButtonByDataItemMaker("save");
    }


    @Step("Оборудования")
    public void fillValuationEquipment
            (String name, String sum, String currency, String country, String year, String equipmentType, String description) {

        ui.basePage
                .clickButtonById("EshPropertyDataEquipmentDetailAddRecordButtonButton-wrapperEl");
        ui.domActions
                .setGridInputValue("EshPropertyDataEquipmentDetailNameTextEdit-el", name)
                .setGridInputValue("EshPropertyDataEquipmentDetailAmountFloatEdit-el", sum);
        ui.lookupComponent
                .setfieldScheduleDetailByDIM("Currency", currency);
        ui.menuComponent
                .clickButtonByLiName(currency);
        ui.lookupComponent
                .setfieldScheduleDetailByDIM("Country", country);
        ui.menuComponent
                .clickButtonByLiName(country);
        ui.domActions
                .setGridInputValue("EshPropertyDataEquipmentDetailStartYearIntegerEdit-el", year);
        ui.lookupComponent
                .setfieldScheduleDetailByDIM("Type", equipmentType);
        ui.menuComponent
                .clickButtonByLiName(equipmentType);
        ui.domActions
                .setGridInputValue("EshPropertyDataEquipmentDetailDescriptionTextEdit-el", description);
        ui.basePage
                .clickButtonByDataItemMaker("save");
    }

    @Step("Оценка залога")
    public void fillValuationCashDeposit(String assessed, String market, String currency) {

        ui.buttonsComponent
                .clickButtonByContainName("Оценка");

        ui.basePage
                .clickButtonById("PledgeRatingDetailAddRecordButtonButton-imageEl");

        ui.domActions
                .clickDivbyId("PledgeRatingDetailRatingSumFloatEdit-wrap", assessed)
                .clickDivbyId("PledgeRatingDetailMarketSumFloatEdit-wrap", market);
        ui.lookupComponent
                .setfieldScheduleDetailByDIM("EshCurrency", currency);
        ui.menuComponent
                .clickButtonByLiName(currency);
        ui.basePage
                .clickButtonByDataItemMaker("save");
    }

    @Step("Оценка залога")
    public void fillValuationRealEstate(String assessed, String market, String currency) {

        ui.buttonsComponent
                .clickButtonByContainName("Оценка");

        ui.basePage
                .clickButtonById("PledgeRatingDetailAddRecordButtonButton-imageEl");

        ui.domActions
                .clickDivbyId("PledgeRatingDetailRatingSumFloatEdit-wrap", assessed)
                .clickDivbyId("PledgeRatingDetailMarketSumFloatEdit-wrap", market);
        ui.lookupComponent
                .setfieldScheduleDetailByDIM("EshCurrency", currency);
        ui.menuComponent
                .clickButtonByLiName(currency);
        ui.basePage
                .clickButtonByDataItemMaker("save");
    }


    @Step("Золотые изделия")
    public void fillValuationGold(String name, String purity, String weight, String weightWithoutStones, String amount, String currency) {

        ui.basePage
                .clickButtonById("EshGoldCollateralValuesDetailAddRecordButtonButton-imageEl");

        ui.domActions
                .setGridInputValue("EshGoldCollateralValuesDetailNameTextEdit-el", name)
                .clickDivbyId("EshGoldCollateralValuesDetailAssayIntegerEdit-wrap", purity);
        ui.domActions
                .setGridInputValue("EshGoldCollateralValuesDetailWeightFloatEdit-el", weight);
        ui.domActions
                .setGridInputValue("EshGoldCollateralValuesDetailWeightWithoutStonesFloatEdit-el", weightWithoutStones);
        ui.domActions
                .setGridInputValue("EshGoldCollateralValuesDetailAmountFloatEdit-el", amount);
        ui.lookupComponent
                .setfieldScheduleDetailByDIM("Currency", currency);
        ui.menuComponent
                .clickButtonByLiName(currency);

        ui.basePage
                .clickButtonByDataItemMaker("save");
    }



    @Step("Объекты обеспечения")
    public void fillValuationMovable(String name, String sum, String currency, String amount, String description) {

        ui.basePage
                .clickButtonById("EshPropertyDataObjectsDetailAddRecordButtonButton-imageEl");
        ui.domActions
                .setGridInputValue("EshPropertyDataObjectsDetailNameTextEdit-el", name)
                .clickDivbyId("EshPropertyDataObjectsDetailAmountFloatEdit-wrap", sum);
        ui.lookupComponent
                .setfieldScheduleDetailByDIM("Currency", currency);
        ui.menuComponent
                .clickButtonByLiName(currency);
        ui.domActions
                .setGridInputValue("EshPropertyDataObjectsDetailCountIntegerEdit-el", amount);
        ui.domActions
                .setGridInputValue("EshPropertyDataObjectsDetailDescriptionTextEdit-el", description);
        ui.basePage
                .clickButtonByDataItemMaker("save");
    }


    @Step("Объекты обеспечения")
    public void fillValuationAcquiredProperty(String name, String sum, String currency, String amount, String description) {

        ui.basePage
                .clickButtonById("EshPropertyDataObjectsDetailAddRecordButtonButton-imageEl");
        ui.domActions
                .setGridInputValue("EshPropertyDataObjectsDetailNameTextEdit-el", name)
                .clickDivbyId("EshPropertyDataObjectsDetailAmountFloatEdit-wrap", sum);
        ui.lookupComponent
                .setfieldScheduleDetailByDIM("Currency", currency);
        ui.menuComponent
                .clickButtonByLiName(currency);
        ui.domActions
                .setGridInputValue("EshPropertyDataObjectsDetailCountIntegerEdit-el", amount);
        ui.domActions
                .setGridInputValue("EshPropertyDataObjectsDetailDescriptionTextEdit-el", description);
        ui.basePage
                .clickButtonByDataItemMaker("save");
    }


    @Step("Объекты обеспечения")
    public void fillValuationFutureHarvest(String name, String sum, String currency, String amount, String description) {

        ui.basePage
                .clickButtonById("EshPropertyDataObjectsDetailAddRecordButtonButton-imageEl");
        ui.domActions
                .setGridInputValue("EshPropertyDataObjectsDetailNameTextEdit-el", name)
                .clickDivbyId("EshPropertyDataObjectsDetailAmountFloatEdit-wrap", sum);
        ui.lookupComponent
                .setfieldScheduleDetailByDIM("Currency", currency);
        ui.menuComponent
                .clickButtonByLiName(currency);
        ui.domActions
                .setGridInputValue("EshPropertyDataObjectsDetailCountIntegerEdit-el", amount);
        ui.domActions
                .setGridInputValue("EshPropertyDataObjectsDetailDescriptionTextEdit-el", description);
        ui.basePage
                .clickButtonByDataItemMaker("save");
    }


}
