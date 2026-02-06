// core/data/scenarios/CollateralScenarios.java
package core.data.scenarios;

public class CollateralScenarios {

    private CollateralScenarios() {}

    public static CollateralScenario none() {
        return new CollateralScenario(null, null);
    }

    public static CollateralScenario car() {
        return new CollateralScenario(
                "AUTO",
                "Toyota Camry 2018"
        );
    }

    public static CollateralScenario apartment() {
        return new CollateralScenario(
                "REAL_ESTATE",
                "Apartment, Dushanbe"
        );
    }
}
