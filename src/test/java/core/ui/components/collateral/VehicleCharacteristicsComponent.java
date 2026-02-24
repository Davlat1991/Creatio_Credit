package core.ui.components.collateral;

import core.base.UiContext;
import io.qameta.allure.Step;

public class VehicleCharacteristicsComponent {

    private final UiContext ui;

    public VehicleCharacteristicsComponent(UiContext ui) {
        this.ui = ui;
    }

    @Step("Заполнение характеристик транспортного средства")
    public void fillTechnicalData() {

        ui.lookupComponent
                .setFieldByValueCheck("Техпаспорт","123456789")
                .setFieldByValueCheck("Техпаспорт","987654321")
                .setFieldByValueCheck("Орган выдавший техпаспорт","ШВКД дар ш.Хучанд")
                .setFieldByValueCheck("Дата регистрации","01.01.2020")
                .setFieldByValueCheck("Регистрационный номер","777")
                .setFieldByValueCheck("VIN номер","123")
                .setHandBookFieldByValueCheck("Марка ТС","BMW")
                .setFieldByValueCheck("Модель","М5")
                .setHandBookFieldByValueCheck("Класс транспортного средства","Сабукрав")
                .setFieldByValueCheck("Год изготовления","01.01.2020")
                .setFieldByValueCheck("Номер кузова","777")
                .setFieldByValueCheck("Номер шасси","555")
                .setHandBookFieldByValueCheck("Цвет","Белый")
                .setHandBookFieldByValueCheck("Тип кузова","Седан")
                .setHandBookFieldByValueCheck("Тип двигателя","Бензин+Газ")
                .setFieldByValueCheck("Объем двигателя, куб. см.","300")
                .setFieldByValueCheck("Мощность двигателя, кВт/л.с.","1000")
                .setFieldByValueCheck("Грузоподъёмность, кг","500")
                .setFieldByValueCheck("Вес, кг","1500")
                .setFieldByValueCheck("Описание","Классная машина");
    }
}
