package core.base.common.address;

/**
 * AddressPageV2 fields.
 * Используются ТОЛЬКО data-item-marker (английские, не локализованные).
 */
public enum AddressField {

    COUNTRY("Country"),
    REGION("Region"),
    DISTRICT("City"),
    CITY("BnzSettlement"),
    STREET_TYPE("TsiStreetType"),

    STREET("Street"),
    HOUSE("Building1"),

    //нужно проверить остальные снизу
    BUILDING("Building"),
    APARTMENT("Apartment"),
    ADDRESS("Address"),

    INDEX("Index"),
    REG_DATE("RegDate");

    public final String marker;

    AddressField(String marker) {
        this.marker = marker;
    }


}


