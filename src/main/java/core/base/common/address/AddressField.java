package core.base.common.address;

/**
 * AddressPageV2 fields.
 * Используются ТОЛЬКО data-item-marker (английские, не локализованные).
 */
public enum AddressField {

    // Страна
    COUNTRY("Country"),
    //Регион
    REGION("Region"),
    //Район
    DISTRICT("City"),
    //Населенный пункт
    CITY("BnzSettlement"),
    //Тип улицы
    STREET_TYPE("TsiStreetType"),

    //Улица
    STREET("Street"),
    //Building1
    HOUSE("Building1"),
    //Корпус
    BUILDING("BnzHousing"),
    //Квартира/Офис
    APARTMENT("AptOffice"),
    //Адрес
    ADDRESS("Address"),
    //Индекс
    INDEX("Zip"),
    //Дата регистрации
    REG_DATE("BnzRegistrationDate");

    public final String marker;

    AddressField(String marker) {
        this.marker = marker;
    }


}


