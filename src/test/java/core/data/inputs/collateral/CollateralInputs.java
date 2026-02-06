package core.data.inputs.collateral;

public class CollateralInputs {

    private CollateralInputs() {}

    public static CollateralInput car() {
        return new CollateralInput(
                "AUTO",
                "Toyota Camry 2018",
                "120000"
        );
    }
}
