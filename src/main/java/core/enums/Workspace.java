package core.enums;

public enum Workspace {

    RETAIL_MANAGER("Розничный менеджер"),
    UNDERWRITER("Верификатор"),
    IKOK("ИКОК"),
    CASHIER("Касса");

    private final String uiName;

    Workspace(String uiName) {
        this.uiName = uiName;
    }

    public String getUiName() {
        return uiName;
    }
}
