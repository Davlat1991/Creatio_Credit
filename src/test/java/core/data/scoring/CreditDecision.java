package core.data.scoring;

public enum CreditDecision {

    APPROVED("Одобрить"),
    REJECTED("Отказать");

    private final String uiText;

    CreditDecision(String uiText) {
        this.uiText = uiText;
    }

    public String getUiText() {
        return uiText;
    }
}
