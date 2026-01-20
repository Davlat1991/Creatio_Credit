package flows.credit.registration;

import core.base.TestContext;
import core.data.registration.RegistrationIncomeExpensesData;
import io.qameta.allure.Step;

public class RegistrationStageFlow {

    private final RegistrationGeneralInfoFlow generalInfoFlow;
    private final RegistrationParticipantsFlow participantsFlow;
    private final RegistrationDocumentsFlow documentsFlow;
    private final RegistrationAddressFlow addressFlow;
    private final RegistrationIncomeExpensesFlow incomeExpensesFlow;
    private final RegistrationScoringFlow scoringFlow;
    private final RegistrationPrintFlow printFlow;

    public RegistrationStageFlow(TestContext ctx) {
        this.generalInfoFlow = new RegistrationGeneralInfoFlow(ctx);
        this.participantsFlow = new RegistrationParticipantsFlow(ctx);
        this.documentsFlow = new RegistrationDocumentsFlow(ctx);
        this.addressFlow = new RegistrationAddressFlow(ctx);
        this.incomeExpensesFlow = new RegistrationIncomeExpensesFlow(ctx);
        this.scoringFlow = new RegistrationScoringFlow(ctx);
        this.printFlow = new RegistrationPrintFlow(ctx);
    }

    @Step("Этап регистрации заявки")
    public void completeRegistrationStage(
            RegistrationIncomeExpensesData incomeExpensesData
    ) {

        generalInfoFlow.fillGeneralInfo();
        participantsFlow.addBorrower();
        documentsFlow.fillBorrowerDocuments();
        addressFlow.fillAddresses();

        incomeExpensesFlow.fillIncomeAndExpenses(incomeExpensesData);

        scoringFlow.fillScoringInfo();
        printFlow.printApplication();
    }
}
