package flows.credit.registration.client;

import core.base.UiContext;
import core.data.registration.EmploymentType;
import flows.credit.registration.*;

public class EmployeeClientFlow extends BaseClientFlow {

    public EmployeeClientFlow(UiContext ctx) {
        super(ctx);
    }

    @Override
    public void fill() {

        new RegistrationAdditionalInfoFlow(ctx)
                .selectEmploymentTypeEmployee(EmploymentType.EMPLOYEE);

        new RegistrationAddressFlow(ctx)
                .fillWorkAddressEmployee();

        new RegistrationParticipantsFlow(ctx)
                .fillCareerDetailsEmployee();

        new RegistrationIncomeExpensesFlow(ctx)
                .fillIndividualActivityEmployed();
    }
}
