package core.ui.components.collateral;

import core.base.UiContext;
import io.qameta.allure.Step;

public class PropertyAddressDetailComponent {

    private final UiContext ui;

    public PropertyAddressDetailComponent(UiContext ui) {
        this.ui = ui;
    }

    @Step("Заполнение адреса имущества")
    public void AcquiredPropertyAddress() {

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

    @Step("Заполнение адреса имущества")
    public void VehiclePropertyAddress() {

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

    @Step("Заполнение адреса имущества")
    public void RealEstatePropertyAddress() {

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

    @Step("Заполнение адреса имущества")
    public void MovablePropertyAddress() {

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