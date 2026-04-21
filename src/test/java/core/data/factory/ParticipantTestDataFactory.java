package core.data.factory;

import core.data.participants.ParticipantData;
import core.enums.ParticipantRole;

public class ParticipantTestDataFactory {

    public static ParticipantData borrower(boolean pzl) {
        return new ParticipantData(
                ParticipantRole.BORROWER,
                "Ali",
                "Test",
                "Testov",
                pzl
        );
    }

    public static ParticipantData guarantor(boolean pzl) {
        return new ParticipantData(
                ParticipantRole.GUARANTOR,
                "Фарангис",
                "Ашурова",
                "Шарифовна",
                pzl
        );
    }

    public static ParticipantData pledger(boolean pzl) {
        return new ParticipantData(
                ParticipantRole.PLEDGER,
                "Азизходжа",
                "Хамидов",
                "Азамходжаевич",
                pzl
        );
    }
}