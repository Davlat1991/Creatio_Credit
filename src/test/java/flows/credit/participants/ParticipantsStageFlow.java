package flows.credit.participants;

import core.base.UiContext;
import core.data.participants.ParticipantData;
import core.data.registration.RegistrationIncomeExpensesData;
import io.qameta.allure.Step;

import java.util.List;

public class ParticipantsStageFlow {

    private final ParticipantsAddFlow addFlow;
    private final PledgerQuestionnaireFlow pledgerFlow;
    private final GuarantorQuestionnaireFlow guarantorFlow;
    private final UiContext ui;

    public ParticipantsStageFlow(UiContext ui) {
        this.ui = ui;
        this.addFlow = new ParticipantsAddFlow(ui);
        this.pledgerFlow = new PledgerQuestionnaireFlow(ui);
        this.guarantorFlow = new GuarantorQuestionnaireFlow(ui);
    }

    @Step("Этап: Участники заявки")
    public void completeParticipantsStage(List<ParticipantData> participants,
                                          RegistrationIncomeExpensesData incomeData) {

        openParticipantsTab();

        for (ParticipantData participant : participants) {
            processParticipant(participant, incomeData);
        }
    }

    private void processParticipant(ParticipantData participant,
                                    RegistrationIncomeExpensesData incomeData) {

        addFlow.addParticipant(participant);

        switch (participant.getRole()) {

            case PLEDGER -> pledgerFlow.fill(participant, incomeData);

            case GUARANTOR -> guarantorFlow.fill(participant, incomeData);

            case BORROWER -> {
                // позже
            }

        }

    }

    private void openParticipantsTab() {
        ui.buttonsComponent
                .clickButtonByContainName("Участники заявки");
    }
}