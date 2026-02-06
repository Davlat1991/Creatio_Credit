package core.context;

import core.enums.CollateralType;

public class CollateralTestContext {

    public CollateralType type;

    // common
    public String value;
    public String currency;
    public boolean primary;

    // vehicle
    public String vin;
    public String brand;
    public String model;
    public String year;
    public String plateNumber;

    // goods
    public String goodsName;
    public String quantity;
    public String storagePlace;

    // equipment
    public String equipmentType;
    public String serialNumber;
    public String manufacturer;

    // cash deposit
    public String bank;
    public String accountNumber;
    public String depositAmount;
    public String depositTerm;
}

