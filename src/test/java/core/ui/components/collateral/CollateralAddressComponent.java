package core.ui.components.collateral;

import core.base.UiContext;
import core.base.common.address.AddressField;
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

        ui.addressComponent
                .selectLookup(AddressField. ADDRESS_TYPE,"Регистрация")  //Тип адреса
                .selectLookup(AddressField.COUNTRY, "Точикистон")        //Страна
                .selectLookup(AddressField.REGION, "Вилояти Сугд")       //Регион
                .selectLookup(AddressField.DISTRICT, "Хучанд")           //Район
                .selectLookup(AddressField.CITY, "ш. Хучанд")            //Населенный пункт
                .selectLookup(AddressField.STREET_TYPE, "Проспект")      //Тип улицы
                .setText(AddressField.STREET, "Исмоили Сомони")          //Улица
                .setText(AddressField.HOUSE, "19")                       //Дом
                .setText(AddressField.BUILDING, "2/7")                   //Корпус
                .setText(AddressField.APARTMENT, "48")                   //Квартира/Офис
                .setText(AddressField.REG_DATE, "01.01.2020");           //Дата регистрации

        ui.basePage
                .clickButtonByName("Сохранить");
    }

}
