package flows.credit.registration;

import core.base.TestContext;
import core.data.registration.EmploymentType;
import core.data.registration.RegistrationIncomeExpensesData;
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



    public RegistrationStageFlow(TestContext ctx) {
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
            EmploymentType employmentType
    ) {

        // 1. Образование и карьера
        // generalInfoFlow.fillGeneralInfo();

        // 2. Участники
        participantsFlow.addBorrower();

        // 3. Документы
        /*documentsFlow.fillBorrowerDocuments();

        // 4. Адреса (другая страница)
        addressFlow.fillAddresses();
        //Состояние ПЗЛ
        addressFlow.setPzlRelation(false);*/

        // 5. Доходы и расходы
        incomeExpensesFlow.fillIncomeAndExpenses(incomeExpensesData);


        // 6. Дополнительная информация
        // ⚠️ ТОЛЬКО выбор типа занятости (без fill'ов)
        //additionalInfoDataFlow.fillBaseScoringData();
        //additionalInfoDataFlow.selectEmploymentType(employmentType);

        //addressFlow.fillWorkAddress();
        //participantsFlow.fillCareerDetails();
        incomeExpensesFlow.fillIndividualActivity();




        // 8. Подтверждение
        scoringConfirmationFlow.confirmScoring();

        // 9. Печать
        //printFlow.printApplication();
    }

}
