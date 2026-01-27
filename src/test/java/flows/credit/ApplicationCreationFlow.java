package flows.credit;

import core.base.UiContext;
import io.qameta.allure.Step;

public class ApplicationCreationFlow {

    private final UiContext ctx;

    public ApplicationCreationFlow(UiContext ctx) {
        this.ctx = ctx;
    }

    @Step("Создание кредитной заявки")
    public void createApplication(
            String repaymentDay,
            String scheduleNumber,
            String scheduleType,
            String termMonths
    ) {

        setRepaymentDate(repaymentDay);
        addScheduleRow();
        fillScheduleDetails(scheduleNumber, scheduleType, termMonths);
        calculateAndCreateApplication();
        verifyNoDebtMessage();
    }

    // ---------------- PRIVATE STEPS ----------------

    private void setRepaymentDate(String day) {
        ctx.lookupComponent
                .setFieldByValueCheck("Запрашиваемая дата погашения", day);
    }

    private void addScheduleRow() {
        ctx.basePage.clickButtonById(
                "KzParameterScheduleDetailAddRecordButtonButton-imageEl"
        );
    }

    private void fillScheduleDetails(
            String number,
            String type,
            String term
    ) {
        ctx.contractPage
                .setfieldScheduleDetailByDIM("KzNumber", number)
                .setHandBookFieldByValue("KzTypeScheduler", type)
                .setfieldScheduleDetailByDIM("KzTermMonth", term);
        ctx.basePage
                .clickButtonByDataItemMaker("save");
    }

    private void calculateAndCreateApplication() {
        ctx.basePage
                .clickButtonByNameCheck("Рассчитать")
                .clickButtonByNameCheck("Создать заявку");
    }

    private void verifyNoDebtMessage() {
        ctx.messageBoxComponent
                .shouldSeeModalWithText("Нет задолженности!");

        ctx.basePage.clickButtonByDataItemMaker("ОК");
    }
}
