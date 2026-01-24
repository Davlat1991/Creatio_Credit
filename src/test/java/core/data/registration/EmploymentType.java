package core.data.registration;


/**
 * Тип занятости заемщика.
 * Используется в процессе регистрации кредитной заявки.
 */
public enum EmploymentType {

    SELF_EMPLOYED("Самозанятый"),
    EMPLOYED("Работает в организации"),
    OTHER_INCOME("Имеет другой источник дохода");

    private final String uiName;

    EmploymentType(String uiName) {
        this.uiName = uiName;
    }

    /**
     * UI-значение для выбора в lookup-поле Creatio.
     */
    public String getUiName() {
        return uiName;
    }
}
