package core.data.participants;

import core.enums.ParticipantRole;

public class ParticipantData {


    private final ParticipantRole role;

    public ParticipantData(ParticipantRole role) {
        this.role = role;
    }

    public ParticipantRole getRole() { return role; }
}