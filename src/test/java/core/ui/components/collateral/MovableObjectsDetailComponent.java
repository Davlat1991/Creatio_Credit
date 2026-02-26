package core.ui.components.collateral;

import core.base.UiContext;
import core.data.collateral.types.MovablePropertyData;
import io.qameta.allure.Step;

public class MovableObjectsDetailComponent {

    private final UiContext ui;

    public MovableObjectsDetailComponent(UiContext ui) {
        this.ui = ui;
    }

    @Step("Добавление объекта обеспечения")
    public void addObject(MovablePropertyData data, String currency) {

        ui.basePage.clickButtonById("EshPropertyDataObjectsDetailAddRecordButtonButton-imageEl");

        ui.domActions.setGridInputValue(
                "EshPropertyDataObjectsDetailNameTextEdit-el",
                data.getObjectName()
        );

        ui.domActions.setGridInputValue(
                "EshPropertyDataObjectsDetailAmountFloatEdit-el",
                data.getAmount()
        );

        ui.lookupComponent.select("Валюта", currency);

        ui.domActions.setGridInputValue(
                "EshPropertyDataObjectsDetailCountIntegerEdit-el",
                data.getQuantity()
        );

        ui.domActions.setGridInputValue(
                "EshPropertyDataObjectsDetailDescriptionTextEdit-el",
                data.getDescription()
        );

        ui.basePage.clickButtonByDataItemMaker("save");
    }
}