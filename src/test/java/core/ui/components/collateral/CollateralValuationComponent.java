package core.ui.components.collateral;

import core.base.UiContext;
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


    @Step("Объекты обеспечения")
    public void fillValuationGoods(String value, String sum, String currency, String amount) {

        ui.basePage
                .clickButtonById("EshPropertyDataObjectsDetailAddRecordButtonButton-imageEl");
        ui.domActions
                .setGridInputValue("EshPropertyDataObjectsDetailNameTextEdit-el", value)
                .clickDivbyId("EshPropertyDataObjectsDetailAmountFloatEdit-wrap", sum);
        ui.lookupComponent
                .setfieldScheduleDetailByDIM("Currency", currency);
        ui.menuComponent
                .clickButtonByLiName(currency);
        ui.domActions
                .setGridInputValue("EshPropertyDataObjectsDetailCountIntegerEdit-el", amount);
        ui.basePage
                .clickButtonByDataItemMaker("save");
    }


    @Step("Оборудования")
    public void fillValuationEquipment(String value, String sum, String currency, String country, String year, String equipmentType   ) {

        ui.basePage
                .clickButtonById("EshPropertyDataEquipmentDetailAddRecordButtonButton-wrapperEl");
        ui.domActions
                .setGridInputValue("EshPropertyDataEquipmentDetailNameTextEdit-el", value)
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
    public void fillValuation(String assessed, String market, String currency) {

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


}
