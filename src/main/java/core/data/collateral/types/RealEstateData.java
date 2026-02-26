package core.data.collateral.types;

public class RealEstateData {

    private final String livingArea;
    private final String buildYear;
    private final String rooms;
    private final String description;
    private final String propertyType;
    private final String totalArea;

    public RealEstateData(
            String livingArea,
            String buildYear,
            String rooms,
            String description,
            String propertyType,
            String totalArea
    ) {
        this.livingArea = livingArea;
        this.buildYear = buildYear;
        this.rooms = rooms;
        this.description = description;
        this.propertyType = propertyType;
        this.totalArea = totalArea;
    }

    public String getLivingArea() { return livingArea; }
    public String getBuildYear() { return buildYear; }
    public String getRooms() { return rooms; }
    public String getDescription() { return description; }
    public String getPropertyType() { return propertyType; }
    public String getTotalArea() { return totalArea; }
}