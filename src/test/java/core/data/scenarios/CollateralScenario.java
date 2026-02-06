// core/data/scenarios/CollateralScenario.java
package core.data.scenarios;

public class CollateralScenario {

    public final String type;        // AUTO, REAL_ESTATE, etc.
    public final String description;

    public CollateralScenario(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public boolean hasCollateral() {
        return type != null;
    }
}
