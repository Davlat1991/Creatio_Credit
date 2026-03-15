package core.data.registration.scenarios;

import core.data.registration.BaseScoringData;

public class BaseScoringScenarios {

    public static BaseScoringData STANDARD_EMPLOYED() {
        return new BaseScoringData.Builder()
                .maritalStatus("Мучаррад (мард)")
                .dependentsCount("0")
                .totalExperience("36")
                .totalExperienceYears("3")
                .totalExperienceMonths("36")
                .lastJobExperienceYears("2")
                .lastJobExperienceMonths("24")
                .build();
    }

    public static BaseScoringData WITH_DEPENDENTS() {
        return new BaseScoringData.Builder()
                .maritalStatus("Оиладор (зан)")
                .dependentsCount("2")
                .getChildrensCount("1")
                .getFamilyCount("3")
                .totalExperience("60")
                .totalExperienceYears("5")
                .totalExperienceMonths("60")
                .lastJobExperienceYears("3")
                .lastJobExperienceMonths("36")
                .build();
    }
}
