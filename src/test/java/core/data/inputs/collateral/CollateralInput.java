package core.data.inputs.collateral;

public class CollateralInput {

    public final String type;
    public final String description;
    public final String estimatedValue;

    public CollateralInput(
            String type,
            String description,
            String estimatedValue
    ) {
        this.type = type;
        this.description = description;
        this.estimatedValue = estimatedValue;
    }
}
