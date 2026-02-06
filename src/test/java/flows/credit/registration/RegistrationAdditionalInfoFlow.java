package flows.credit.registration;

import core.base.UiContext;
import core.base.common.educationcareer.EducationCareerField;
import core.data.registration.BaseScoringData;
import core.data.registration.EmploymentType;
import io.qameta.allure.Step;

/**
 * Flow отвечает ТОЛЬКО за заполнение скоринговых данных.
 * Не содержит навигации, кнопок подтверждения и переходов этапов.
 */
public class RegistrationAdditionalInfoFlow {

    private final UiContext ui;

    public RegistrationAdditionalInfoFlow(UiContext ui) {
        this.ui = ui;

    }


    /*@Step("Заполнение базовых скоринговых данных заемщика")
    public void fillBaseScoringData() {



        ui.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Семейное положение",
                        "Мучаррад (мард)"
                );

        ui.lookupComponent
                .setFieldByValueCheck("Количество иждивенцев (строка)","0")
                .setFieldByValueCheck("Общий стаж","36")
                .setFieldByValueCheck("Общий стаж, лет","3")
                .setFieldByValueCheck("Общий стаж, мес","36")
                .setFieldByValueCheck("Стаж на последнем месте, лет","2")
                .setFieldByValueCheck("Стаж на последнем месте, мес","24");


    }*/

    @Step("Заполнение базовых скоринговых данных заемщика")
    public void fillBaseScoringData(BaseScoringData data) {

        ui.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Семейное положение",
                        data.getMaritalStatus()
                );

        ui.lookupComponent
                .setFieldByValueCheck("Количество иждивенцев (строка)", data.getDependentsCount())
                .setFieldByValueCheck("Общий стаж", data.getTotalExperience())
                .setFieldByValueCheck("Общий стаж, лет", data.getTotalExperienceYears())
                .setFieldByValueCheck("Общий стаж, мес", data.getTotalExperienceMonths())
                .setFieldByValueCheck("Стаж на последнем месте, лет", data.getLastJobExperienceYears())
                .setFieldByValueCheck("Стаж на последнем месте, мес", data.getLastJobExperienceMonths());
    }


    @Step("Подготовка поля 'Причина отсутствия работы'")
    public void prepareReasonOfNoWork() {
        ui.additionalInfoPage.clearReasonOfNoWorkIfPresent();
    }



    @Step("Выбор типа занятости: {employmentType}")
    public void selectEmploymentTypeOtherIncome(EmploymentType employmentType) {

        ui.educationCareerComponent
                .setLookup(
                        EducationCareerField.EMPLOYMENT_TYPE,
                        employmentType.getUiName()
                )
                .setLookup(EducationCareerField.REASON_FOR_NOT_WORKING, "Получатель Д/П");
    }


    @Step("Выбор типа занятости: {employmentType}")
    public void selectEmploymentTypeSelfEmployed(EmploymentType employmentType) {

        ui.educationCareerComponent
                .setLookup(
                        EducationCareerField.EMPLOYMENT_TYPE,
                        employmentType.getUiName()
                )
                .clearFieldIfPresent(EducationCareerField.REASON_FOR_NOT_WORKING)
                .setLookup(EducationCareerField.POSITION, "Агроном")
                .setText(EducationCareerField.ORGANIZATION_NAME, "Агропром")
                .setLookup(EducationCareerField.ROLE, "Руководитель")
                .setLookup(EducationCareerField.EDUCATION, "2 и более высших")
                .setLookup(EducationCareerField.LEGAL_FORM, "Дехканские (фермерские) хозяйства")
                .setLookup(EducationCareerField.BUSINESS_REPUTATION, "Управленческая должность");

    }


    @Step("Выбор типа занятости: {employmentType}")
    public void selectEmploymentTypeEmployee(EmploymentType employmentType) {

        ui.educationCareerComponent
                .setLookup(
                        EducationCareerField.EMPLOYMENT_TYPE,
                        employmentType.getUiName()
                )
                .clearFieldIfPresent(EducationCareerField.REASON_FOR_NOT_WORKING)
                .setLookup(EducationCareerField.POSITION, "Бухгалтер")
                .setText(EducationCareerField.ORGANIZATION_NAME, "Агро-Холдинг")
                .setLookup(EducationCareerField.ROLE, "Исполнитель")
                .setLookup(EducationCareerField.SOCIAL_STATUS, "Рабочий в частной фирме/организации")
                .setLookup(EducationCareerField.EDUCATION, "2 и более высших")
                .setText(EducationCareerField.POSITION_FULL_NAME, "Бухгалтер")
                //.setLookup(EducationCareerField.EMPLOYER, "ЧДММ \"АГРОПРОМ ХУЧАНД\"")
                .setLookup(EducationCareerField.LEGAL_FORM, "Дехканские (фермерские) хозяйства")
                .setLookup(EducationCareerField.BUSINESS_REPUTATION, "Рабочий");

    }



    @Step("Заполнение данных клиента с иным источником дохода")
    public void fillOtherIncomeData() {
        setReasonOfNoWork("Получатель Д/П");
    }



    private void setReasonOfNoWork(String reason) {
        ui.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Причина отсутствия работы",
                        reason
                );


    }

}
