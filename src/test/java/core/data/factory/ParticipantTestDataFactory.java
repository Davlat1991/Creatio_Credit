package core.data.factory;

import core.data.participants.ParticipantData;
import core.enums.ParticipantRole;

public class ParticipantTestDataFactory {


    public static ParticipantData borrower() {
        return new ParticipantData(ParticipantRole.BORROWER);
    }

    public static ParticipantData guarantor() {
        return new ParticipantData(ParticipantRole.GUARANTOR);
    }

    public static ParticipantData pledger() {
        return new ParticipantData(ParticipantRole.PLEDGER);
    }

}