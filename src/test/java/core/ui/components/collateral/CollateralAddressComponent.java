package core.ui.components.collateral;

import core.base.UiContext;
import io.qameta.allure.Step;

public class CollateralAddressComponent {

    private final UiContext ui;

    public CollateralAddressComponent(UiContext ui) {
        this.ui = ui;
    }

    @Step("Заполнение адреса имущества")
    public void fillPropertyAddress() {

        ui.basePage
                .clickButtonById("EshPropertyDataAddressDetailAddRecordButtonButton-imageEl");

        ui.lookupComponent
                .setHandBookFieldByValueCheck("Тип адреса", "Регистрация")
                .setHandBookFieldByValueCheck("Страна", "Точикистон")
                .setHandBookFieldByValueCheck("Регион", "Вилояти Сугд")
                .setHandBookFieldByValueCheck("Район", "Хучанд")
                .setHandBookFieldByValueCheck("Населенный пункт", "ш. Хучанд")
                .setHandBookFieldByValueCheck("Тип улицы", "Улица")
                .setFieldByValueCheck("Улица", "Исмоили Сомони")
                .setFieldByValueCheck("Дом", "226");
        ui.basePage
                .clickButtonByName("Сохранить");
    }
}
