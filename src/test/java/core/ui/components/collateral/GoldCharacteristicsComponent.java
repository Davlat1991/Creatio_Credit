package core.ui.components.collateral;

import core.base.UiContext;
import core.data.collateral.types.GoldData;
import io.qameta.allure.Step;

public class GoldCharacteristicsComponent {

    private final UiContext ui;

    public GoldCharacteristicsComponent(UiContext ui) {
        this.ui = ui;
    }

    @Step("Заполнение характеристик золота")
    public void fill(GoldData data) {

        ui.lookupComponent.setFieldByValueCheck("Вес, грамм", data.getWeight());
        ui.lookupComponent.setFieldByValueCheck("Проба", data.getPurity());
        ui.lookupComponent.setFieldByValueCheck("Описание", data.getDescription());
    }
}