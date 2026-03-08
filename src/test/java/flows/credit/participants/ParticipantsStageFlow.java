package flows.credit.participants;

import core.base.UiContext;
import core.data.participants.ParticipantData;
import core.enums.ParticipantRole;
import io.qameta.allure.Step;

import java.util.List;

public class ParticipantsStageFlow {

    private final ParticipantsAddFlow addFlow;
    private final PledgerQuestionnaireFlow pledgerFlow;
    private final UiContext ui;

    public ParticipantsStageFlow(UiContext ui) {
        this.ui = ui;
        this.addFlow = new ParticipantsAddFlow(ui);
        this.pledgerFlow = new PledgerQuestionnaireFlow(ui);
    }

    @Step("Этап: Участники заявки")
    public void completeParticipantsStage(List<ParticipantData> participants) {

        openParticipantsTab();

        for (ParticipantData participant : participants) {

            addFlow.addParticipant(participant);

            if (participant.getRole() == ParticipantRole.PLEDGER) {
                pledgerFlow.completePledgerQuestionnaire(false);
            }
        }
    }

    private void openParticipantsTab() {
        ui.buttonsComponent
                .clickButtonByContainName("Участники заявки");
    }
}