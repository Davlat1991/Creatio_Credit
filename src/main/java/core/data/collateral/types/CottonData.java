package core.data.collateral.types;

public class CottonData {

    private final String name;
    private final String quantity;
    private final String description;

    public CottonData(String name, String quantity, String description) {
        this.name = name;
        this.quantity = quantity;
        this.description = description;
    }

    public String getName() { return name; }
    public String getQuantity() { return quantity; }
    public String getDescription() { return description; }
}