package core.data.participants;

import core.enums.ParticipantRole;

public class ParticipantData {

    private final ParticipantRole role;
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final boolean pzlRelation;

    public ParticipantData(ParticipantRole role,
                           String firstName,
                           String lastName,
                           String middleName,
                           boolean pzlRelation) {
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.pzlRelation = pzlRelation;
    }

    public ParticipantRole getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public boolean isPzlRelation() {
        return pzlRelation;
    }

    // 🔥 фабрики (очень удобно в тестах)
    public static ParticipantData guarantor() {
        return new ParticipantData(
                ParticipantRole.GUARANTOR,
                "Фарида",
                "Тест",
                "Тестовна",
                false
        );
    }

    public static ParticipantData pledger(boolean pzl) {
        return new ParticipantData(
                ParticipantRole.PLEDGER,
                "Фарида",
                "Тест",
                "Тестовна",
                pzl
        );
    }
}