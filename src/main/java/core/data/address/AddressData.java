package core.data.address;


/**
 * Business model of Address for Creatio 7.18 (AddressPageV2).
 * Используется в UI / API / DB тестах.
 */
public class AddressData {

    /* ===== Lookup fields ===== */
    private String country;
    private String region;
    private String district;
    private String city;
    private String streetType;

    /* ===== Text fields ===== */
    private String street;
    private String house;
    private String building;
    private String apartment;
    private String addressLine;

    /* ===== Service fields ===== */
    private String postalCode;
    private String registrationDate;

    /* ===== Constructors ===== */

    public AddressData() {
    }

    /* ===== Getters / Setters ===== */

    public String getCountry() {
        return country;
    }

    public AddressData setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public AddressData setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getDistrict() {
        return district;
    }

    public AddressData setDistrict(String district) {
        this.district = district;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AddressData setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreetType() {
        return streetType;
    }

    public AddressData setStreetType(String streetType) {
        this.streetType = streetType;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public AddressData setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getHouse() {
        return house;
    }

    public AddressData setHouse(String house) {
        this.house = house;
        return this;
    }

    public String getBuilding() {
        return building;
    }

    public AddressData setBuilding(String building) {
        this.building = building;
        return this;
    }

    public String getApartment() {
        return apartment;
    }

    public AddressData setApartment(String apartment) {
        this.apartment = apartment;
        return this;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public AddressData setAddressLine(String addressLine) {
        this.addressLine = addressLine;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public AddressData setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public AddressData setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }
}

