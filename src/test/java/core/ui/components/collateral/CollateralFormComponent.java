package core.ui.components.collateral;

import core.base.UiContext;
import io.qameta.allure.Step;

public class CollateralFormComponent {

    private final UiContext ui;

    public CollateralFormComponent(UiContext ui) {
        this.ui = ui;
    }

    @Step("Тип залога: {value}")
    public void selectType(String value) {
        ui.lookupComponent.setHandBookFieldByValueCheck("Тип", value);
    }

    @Step("Подтип залога: {value}")
    public void selectSubType(String value) {
        ui.lookupComponent.setHandBookFieldByValueCheck("Подтип", value);
    }

    @Step("Состояние: {value}")
    public void selectCondition(String value) {
        ui.lookupComponent.setHandBookFieldByValueCheck("Состояние", value);
    }

    @Step("Право владения: {value}")
    public void selectOwnership(String value) {
        ui.lookupComponent.setHandBookFieldByValueCheck("Право владения", value);
    }

    @Step("Залогодатель: {value}")
    public void selectPledger(String value) {
        ui.lookupComponent.setHandBookFieldByValueCheck("Залогодатель", value);
    }

    @Step("Название залога: {value}")
    public void setName(String value) {
        ui.lookupComponent.setFieldByValueCheck("Название", value);
    }

    @Step("Закрыть залог")
    public void close() {
        ui.basePage.clickButtonByName("Закрыть");
    }

    @Step("Сохранить залог")
    public void save() {
        ui.basePage.clickButtonByName("Сохранить");
    }

}
