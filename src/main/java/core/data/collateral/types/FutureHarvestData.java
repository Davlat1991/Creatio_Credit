package core.data.collateral.types;

public class FutureHarvestData {

    private final String cropName;
    private final String amount;
    private final String quantity;
    private final String description;

    public FutureHarvestData(
            String cropName,
            String amount,
            String quantity,
            String description
    ) {
        this.cropName = cropName;
        this.amount = amount;
        this.quantity = quantity;
        this.description = description;
    }

    public String getCropName() { return cropName; }
    public String getAmount() { return amount; }
    public String getQuantity() { return quantity; }
    public String getDescription() { return description; }
}