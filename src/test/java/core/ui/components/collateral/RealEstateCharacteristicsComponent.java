package core.ui.components.collateral;

import core.base.UiContext;
import io.qameta.allure.Step;

public class RealEstateCharacteristicsComponent {

    private final UiContext ui;

    public RealEstateCharacteristicsComponent(UiContext ui) {
        this.ui = ui;
    }

    @Step("Заполнение характеристик недвижимости")
    public void fillCharacteristics(
            String livingArea,
            String buildYear,
            String rooms,
            String description,
            String propertyType,
            String totalArea
    ) {

        ui.lookupComponent
                .setFieldByValueCheck("Жилая площадь, кв. метров", livingArea)
                .setFieldByValueCheck("Год строительства", buildYear)
                .setFieldByValueCheck("Количество комнат", rooms)
                .setFieldByValueCheck("Описание", description)
                .setHandBookFieldByValueCheck("Тип недвижимости", propertyType)
                .setFieldByValueCheck("Общая площадь, кв. метров", totalArea);
    }
}
