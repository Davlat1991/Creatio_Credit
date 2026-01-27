package flows.credit.registration.client;

/**
 * Базовый флоу для клиентов, которые РАБОТАЮТ
 * (EMPLOYED / SELF_EMPLOYED).
 */
/*public abstract class AbstractWorkingClientFlow extends BaseClientFlow {

    protected final TestContext ctx;

    protected AbstractWorkingClientFlow(TestContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public final void fill() {

        // 1. Базовые скоринговые данные (общие)
        fillBaseScoring();

        // 2. Тип занятости + базовые поля
        fillEmploymentSpecificData();

        // 3. Рабочий адрес
        fillWorkAddress();

        // 4. Карьера
        fillCareer();

        // 5. Доходы по работе
        fillIncome();
    }

    /* ====== Общие шаги ====== */

    /*protected void fillBaseScoring() {
        new RegistrationAdditionalInfoFlow(ctx)
                .fillBaseScoringData();
    }

    protected void fillWorkAddress() {
        new RegistrationAddressFlow(ctx)
                .fillWorkAddressEmployed();
    }

    protected void fillCareer() {
        new RegistrationParticipantsFlow(ctx)
                .fillCareerDetailsEmployed();
    }

    protected void fillIncome() {
        new RegistrationIncomeExpensesFlow(ctx)
                .fillIndividualActivityEmployed();
    }

    /* ====== Абстрактные (отличия) ====== */

    /*protected abstract void fillEmploymentSpecificData();
}
*/