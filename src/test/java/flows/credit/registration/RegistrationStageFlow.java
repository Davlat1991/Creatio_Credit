package flows.credit.registration;

import core.base.UiContext;
import core.data.registration.RegistrationIncomeExpensesData;
import core.data.registration.scenarios.BaseScoringScenarios;
import flows.credit.registration.client.BaseClientFlow;
import io.qameta.allure.Step;

/**
 * Orchestration Flow этапа регистрации заявки.
 *
 * Отвечает ТОЛЬКО за последовательность выполнения этапов регистрации,
 * не содержит логики заполнения отдельных полей.
 */
public class RegistrationStageFlow {

    private final RegistrationGeneralInfoFlow generalInfoFlow;
    private final RegistrationParticipantsFlow participantsFlow;
    private final RegistrationDocumentsFlow documentsFlow;
    private final RegistrationAddressFlow addressFlow;
    private final RegistrationIncomeExpensesFlow incomeExpensesFlow;
    private final RegistrationAdditionalInfoFlow additionalInfoDataFlow;
    private final RegistrationScoringConfirmationFlow scoringConfirmationFlow;
    private final RegistrationPrintFlow printFlow;






    public RegistrationStageFlow(UiContext ctx) {
        this.generalInfoFlow = new RegistrationGeneralInfoFlow(ctx);
        this.participantsFlow = new RegistrationParticipantsFlow(ctx);
        this.documentsFlow = new RegistrationDocumentsFlow(ctx);
        this.addressFlow = new RegistrationAddressFlow(ctx);
        this.incomeExpensesFlow = new RegistrationIncomeExpensesFlow(ctx);
        this.additionalInfoDataFlow = new RegistrationAdditionalInfoFlow(ctx);
        this.scoringConfirmationFlow = new RegistrationScoringConfirmationFlow(ctx);
        this.printFlow = new RegistrationPrintFlow(ctx);

        
        
        
        
    }

    @Step("Этап регистрации заявки")
    public void completeRegistrationStage(
            RegistrationIncomeExpensesData incomeExpensesData,
            BaseClientFlow clientFlow
    ) {

        // 1. Образование и карьера
        generalInfoFlow.fillGeneralInfo();

        // 2. Участники
        participantsFlow.addBorrower();

        // 3. Документы
        documentsFlow.fillBorrowerDocuments();

        // 4. Адреса (другая страница)
        addressFlow.fillAddresses();
        //Состояние ПЗЛ
        addressFlow.setPzlRelation(false);

        // 5. Доходы и расходы
        incomeExpensesFlow.fillIncomeAndExpenses(incomeExpensesData);

        // 6. Дополнительная информация

       additionalInfoDataFlow.fillBaseScoringData(
                BaseScoringScenarios.STANDARD_EMPLOYED()
        );
        clientFlow.fill();

        // 8. Подтверждение
        scoringConfirmationFlow.confirmScoring();

        // 9. Печать
        printFlow.printApplication();
    }

}
