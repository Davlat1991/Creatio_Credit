package flows.credit.registration;

import core.base.TestContext;
import io.qameta.allure.Step;

public class RegistrationParticipantsFlow {

    private final TestContext ctx;

    public RegistrationParticipantsFlow(TestContext ctx) {
        this.ctx = ctx;
    }

    @Step("Добавление заемщика")
    public void addBorrower() {

        ctx.buttonsComponent
                .clickButtonByContainName("Участники заявки");

        ctx.basePage
                .clickButtonById("BnzVwFinApplicationAllParticipantDetailAddTypedRecordButtonButton-imageEl");

        ctx.menuComponent
                .clickButtonByLiName("Заемщик");
    }
}
