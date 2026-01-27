package flows.credit.registration.client;

import core.base.UiContext;
import core.data.registration.EmploymentType;
import flows.credit.registration.RegistrationAdditionalInfoFlow;
import flows.credit.registration.RegistrationAddressFlow;
import flows.credit.registration.RegistrationIncomeExpensesFlow;
import flows.credit.registration.RegistrationParticipantsFlow;


public class SelfEmployedClientFlow extends BaseClientFlow {

    public SelfEmployedClientFlow(UiContext ctx) {
        super(ctx);
    }

    @Override
    public void fill() {

        new RegistrationAdditionalInfoFlow(ctx)
                .selectEmploymentTypeSelfEmployed(EmploymentType.SELF_EMPLOYED);

        new RegistrationAddressFlow(ctx)
                .fillWorkAddressSelfEmployed();

        new RegistrationParticipantsFlow(ctx)
                .fillCareerDetailsSelfEmployed();

        new RegistrationIncomeExpensesFlow(ctx)
                .fillIndividualActivitySelfEmployed();
    }


}