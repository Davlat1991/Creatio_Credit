package core.data.collateral.types;

public class MovablePropertyData {

    private final String objectName;
    private final String amount;
    private final String quantity;
    private final String description;

    public MovablePropertyData(
            String objectName,
            String amount,
            String quantity,
            String description
    ) {
        this.objectName = objectName;
        this.amount = amount;
        this.quantity = quantity;
        this.description = description;
    }

    public String getObjectName() { return objectName; }
    public String getAmount() { return amount; }
    public String getQuantity() { return quantity; }
    public String getDescription() { return description; }
}