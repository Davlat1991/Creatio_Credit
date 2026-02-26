package core.ui.components.collateral;

import core.base.UiContext;
import core.data.collateral.types.CottonData;
import io.qameta.allure.Step;

public class CottonCharacteristicsComponent {

    private final UiContext ui;

    public CottonCharacteristicsComponent(UiContext ui) {
        this.ui = ui;
    }

    @Step("Добавление характеристики хлопка")
    public void fillTechnicalData() {

        ui.lookupComponent
                .setFieldByValueCheck("Описание","Хороший урожай")
                .setFieldByValueCheck("Наименование","Урожай 2026")
                .setFieldByValueCheck("Количество","500");

    }
}