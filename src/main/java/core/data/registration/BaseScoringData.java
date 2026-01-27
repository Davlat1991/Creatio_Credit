package core.data.registration;

public class BaseScoringData {

    private final String maritalStatus;
    private final String dependentsCount;
    private final String totalExperience;
    private final String totalExperienceYears;
    private final String totalExperienceMonths;
    private final String lastJobExperienceYears;
    private final String lastJobExperienceMonths;

    private BaseScoringData(Builder builder) {
        this.maritalStatus = builder.maritalStatus;
        this.dependentsCount = builder.dependentsCount;
        this.totalExperience = builder.totalExperience;
        this.totalExperienceYears = builder.totalExperienceYears;
        this.totalExperienceMonths = builder.totalExperienceMonths;
        this.lastJobExperienceYears = builder.lastJobExperienceYears;
        this.lastJobExperienceMonths = builder.lastJobExperienceMonths;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getDependentsCount() {
        return dependentsCount;
    }

    public String getTotalExperience() {
        return totalExperience;
    }

    public String getTotalExperienceYears() {
        return totalExperienceYears;
    }

    public String getTotalExperienceMonths() {
        return totalExperienceMonths;
    }

    public String getLastJobExperienceYears() {
        return lastJobExperienceYears;
    }

    public String getLastJobExperienceMonths() {
        return lastJobExperienceMonths;
    }

    /* ========= Builder ========= */

    public static class Builder {
        private String maritalStatus;
        private String dependentsCount;
        private String totalExperience;
        private String totalExperienceYears;
        private String totalExperienceMonths;
        private String lastJobExperienceYears;
        private String lastJobExperienceMonths;

        public Builder maritalStatus(String value) {
            this.maritalStatus = value;
            return this;
        }

        public Builder dependentsCount(String value) {
            this.dependentsCount = value;
            return this;
        }

        public Builder totalExperience(String value) {
            this.totalExperience = value;
            return this;
        }

        public Builder totalExperienceYears(String value) {
            this.totalExperienceYears = value;
            return this;
        }

        public Builder totalExperienceMonths(String value) {
            this.totalExperienceMonths = value;
            return this;
        }

        public Builder lastJobExperienceYears(String value) {
            this.lastJobExperienceYears = value;
            return this;
        }

        public Builder lastJobExperienceMonths(String value) {
            this.lastJobExperienceMonths = value;
            return this;
        }

        public BaseScoringData build() {
            return new BaseScoringData(this);
        }
    }
}
