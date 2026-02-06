package flows.credit.registration.client;

import core.base.UiContext;
import core.data.registration.EmploymentType;
import flows.credit.registration.RegistrationAdditionalInfoFlow;
import flows.credit.registration.RegistrationAddressFlow;


public class OtherIncomeClientFlow extends BaseClientFlow {

    public OtherIncomeClientFlow(UiContext ctx) {
        super(ctx);
    }

    @Override
    public void fill() {

        new RegistrationAdditionalInfoFlow(ctx)
                .selectEmploymentTypeOtherIncome(EmploymentType.OTHER_INCOME);

    }
}
