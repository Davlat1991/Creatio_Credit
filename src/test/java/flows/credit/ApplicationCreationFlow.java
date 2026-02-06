package flows.credit;

import core.base.UiContext;
import io.qameta.allure.Step;

public class ApplicationCreationFlow {

    private final UiContext ui;

    public ApplicationCreationFlow(UiContext ui) {
        this.ui = ui;
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
        ui.lookupComponent
                .setFieldByValueCheck("Запрашиваемая дата погашения", day);
    }

    private void addScheduleRow() {
        ui.basePage.clickButtonById(
                "KzParameterScheduleDetailAddRecordButtonButton-imageEl"
        );
    }

    private void fillScheduleDetails(
            String number,
            String type,
            String term
    ) {
        ui.contractPage
                .setfieldScheduleDetailByDIM("KzNumber", number)
                .setHandBookFieldByValue("KzTypeScheduler", type)
                .setfieldScheduleDetailByDIM("KzTermMonth", term);
        ui.basePage
                .clickButtonByDataItemMaker("save");
    }

    private void calculateAndCreateApplication() {
        ui.basePage
                .clickButtonByName("Рассчитать")
                .clickButtonByName("Создать заявку");
    }

    private void verifyNoDebtMessage() {
        ui.messageBoxComponent
                .shouldSeeModalWithText("Нет задолженности!");

        ui.basePage.clickButtonByDataItemMaker("ОК");
    }
}
