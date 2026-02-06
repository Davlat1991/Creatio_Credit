// core/data/scenarios/RolesScenarios.java
package core.data.scenarios;

import core.config.Environment;

public class RolesScenarios {

    private RolesScenarios() {}

    public static RolesScenario standardRetailFlow() {
        return new RolesScenario(
                Environment.USER_DAVLAT, // operator
                Environment.USER_DAVLAT,
                Environment.USER_ADMIN,  // underwriter
                Environment.USER_ADMIN,  // cashier
                Environment.USER_ADMIN   // ikok
        );
    }
}
