package core.base.common.career;

/**
 * Поля блока "Карьера / Занятость заемщика".
 * marker = data-item-marker из DOM (НЕ локализованный).
 */
public enum CareerField {

    EMPLOYMENT_TYPE("EmploymentType"),             // Тип занятости
    ORGANIZATION_NAME("BnzOrganizationName"),      // Наименование организации
    POSITION("Job"),                               // Должность
    START_DATE("StartDate"),                       // Дата начала
    DESCRIPTION("Description"),                    // Описание
    EMPLOYER("Account"),                           // Работодатель
    POSITION_FULL_NAME("JobTitle"),                // Полное название должности
    DUE_DATE("DueDate");                           // Дата завершение



    public final String marker;

    CareerField(String marker) {
        this.marker = marker;
    }
}


