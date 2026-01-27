package flows.credit.registration.client;

import core.base.UiContext;
import core.data.registration.EmploymentType;
import flows.credit.registration.*;

public class EmployedClientFlow extends BaseClientFlow {

    public EmployedClientFlow(UiContext ctx) {
        super(ctx);
    }

    @Override
    public void fill() {

        new RegistrationAdditionalInfoFlow(ctx)
                .selectEmploymentTypeEmployed(EmploymentType.EMPLOYED);

        new RegistrationAddressFlow(ctx)
                .fillWorkAddressEmployed();

        new RegistrationParticipantsFlow(ctx)
                .fillCareerDetailsEmployed();

        new RegistrationIncomeExpensesFlow(ctx)
                .fillIndividualActivityEmployed();
    }
}
