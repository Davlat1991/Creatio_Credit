// core/data/scenarios/RolesScenario.java
package core.data.scenarios;

import core.data.users.LoginData;

public class RolesScenario {

    public final LoginData operator;
    public final LoginData retailManager;
    public final LoginData underwriter;
    public final LoginData cashier;
    public final LoginData ikok;

    public RolesScenario(
            LoginData operator,
            LoginData retailManager,
            LoginData underwriter,
            LoginData cashier,
            LoginData ikok
    ) {
        this.operator = operator;
        this.retailManager = retailManager;
        this.underwriter = underwriter;
        this.cashier = cashier;
        this.ikok = ikok;
    }
}
