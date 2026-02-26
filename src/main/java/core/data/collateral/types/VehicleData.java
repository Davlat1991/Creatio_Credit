package core.data.collateral.types;

public class VehicleData {

    private final String brand;
    private final String model;
    private final String year;

    public VehicleData(String brand, String model, String year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public String getYear() { return year; }
}