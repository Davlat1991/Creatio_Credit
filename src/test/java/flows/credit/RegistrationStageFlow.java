package flows.credit;

import core.base.TestContext;
import io.qameta.allure.Step;

public class RegistrationStageFlow {

    private final TestContext ctx;

    public RegistrationStageFlow(TestContext ctx) {
        this.ctx = ctx;
    }

    @Step("Этап регистрации заявки")
    public void completeRegistrationStage() {

        fillGeneralRegistrationInfo();
        openParticipants();
        addBorrower();
        fillBorrowerDocuments();
        fillAddresses();
        fillIncomeAndExpenses();
        fillAdditionalInformation();
        saveAndPrintApplication();
    }

    // ----------------------------------------------------------------
    // GENERAL INFO
    // ----------------------------------------------------------------

    private void fillGeneralRegistrationInfo() {
        ctx.lookupComponent
                .setHandBookFieldByValueCheck("Регион использования кредита", "ш. Хучанд")
                .setHandBookFieldByValueCheck("Источник информации", "Интернет/Сайт");
    }

    // ----------------------------------------------------------------
    // PARTICIPANTS
    // ----------------------------------------------------------------

    private void openParticipants() {
        ctx.buttonsComponent
                .clickButtonByContainName("Участники заявки");
    }

    private void addBorrower() {
        ctx.basePage
                .clickButtonById("BnzVwFinApplicationAllParticipantDetailAddTypedRecordButtonButton-imageEl");

        ctx.menuComponent
                .clickButtonByLiName("Заемщик");
    }

    // ----------------------------------------------------------------
    // DOCUMENTS
    // ----------------------------------------------------------------

    private void fillBorrowerDocuments() {

        ctx.lookupComponent
                .setHandBookFieldByValueCheck("Страна рождения", "Точикистон")
                .setFieldByValueCheck("Дата выдачи", "01.01.2020");

        ctx.lookupComponent
                .setFieldByValueCheck("Действителен до", "01.01.2030");

        ctx.gridComponent
                .DoubleclickByDIM("Шиносномаи ЧТ");

        ctx.basePage
                .checkCurrentPage("RegDocumentPageV2Container");

        ctx.lookupComponent
                .setFieldByValueCheck("Дата выдачи", "01.01.2020")
                .setFieldByValueCheck("Действует до", "01.01.2030");

        ctx.contractPage
                .clickButtonByNameCheck("Сохранить");

        ctx.basePage
                .checkCurrentPage("EntityLoaded");
    }

    // ----------------------------------------------------------------
    // ADDRESSES
    // ----------------------------------------------------------------

    private void fillAddresses() {

        // Регистрация
        ctx.lookupComponent
                .selectDropdownValueWithCheck("BnzAffiliation", "Мобильный");

        ctx.buttonsComponent
                .doubleclickButtonByName("Регистрация");

        ctx.basePage
                .checkCurrentPage("ContactAddressPageV2Container");

        ctx.contactAddressPage
                .waitForAddressPageLoaded();

        ctx.dateFieldComponent
                .setDateFieldByMarker("BnzRegistrationDate", "01.01.2020");

        ctx.contractPage
                .clickButtonByNameCheck("Сохранить");

        ctx.basePage.waitForPage();

        // Фактический
        ctx.lookupComponent
                .setHandBookFieldByValueCheck("Тип клиента", "Нав");

        ctx.buttonsComponent
                .doubleclickButtonByName("Фактический");

        ctx.basePage
                .checkCurrentPage("ContactAddressPageV2Container");

        ctx.contactAddressPage
                .waitForAddressPageLoaded();

        ctx.dateFieldComponent
                .setDateFieldByMarker("BnzRegistrationDate", "01.01.2020");

        ctx.contractPage
                .clickButtonByNameCheck("Сохранить");
    }

    // ----------------------------------------------------------------
    // INCOME / EXPENSES
    // ----------------------------------------------------------------

    private void fillIncomeAndExpenses() {

        ctx.lookupComponent
                .setHandBookFieldByValueCheck("Уровень риска", "Низкий");

        ctx.buttonsComponent
                .clickButtonByContainNameCheck("ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ");

        ctx.lookupComponent
                .setHandBookFieldByValueCheck("Клиент предоставил подтверждение дохода", "Да")
                .setHandBookFieldByValueCheck("Тип документа подтверждения дохода", "Выписка с банковского счета");

        // Доходы
        ctx.basePage
                .clickButtonById("BnzAppIncomesDetailAddRecordButtonButton-imageEl");

        ctx.contractPage
                .setfieldScheduleDetailByDIM("TsiKind", "Зарплата по основному месту работы");

        ctx.menuComponent
                .clickButtonByLiName("Зарплата по основному месту работы");

        ctx.contractPage
                .setfieldScheduleDetailByDIM("TsiFrequencyType", "Ежемесячно");

        ctx.menuComponent
                .clickButtonByLiName("Ежемесячно");

        ctx.contractPage
                .setfieldScheduleDetailByDIM("TsiAmountBC", "11000");

        // Расходы
        ctx.basePage
                .clickButtonById("BnzAppExpensesDetailAddRecordButtonButton-imageEl");

        ctx.checkboxComponent
                .CheckBoxValue("BnzAppExpensesDetailTsiKindComboBoxEdit-el");

        ctx.menuComponent
                .clickButtonByLiName("Коммунальные платежи");

        ctx.checkboxComponent
                .CheckBoxValue("BnzAppExpensesDetailTsiFrequencyTypeComboBoxEdit-el");

        ctx.menuComponent
                .clickButtonByLiName("Единоразово");

        ctx.domActions
                .clickDivbyId("BnzAppExpensesDetailTsiAmountBCFloatEdit-wrap", "600");
    }

    // ----------------------------------------------------------------
    // FINAL INFO
    // ----------------------------------------------------------------

    private void fillAdditionalInformation() {

        ctx.lookupComponent
                .setHandBookFieldByValueCheck("Семейное положение", "Мучаррад (мард)")
                .setFieldByValueCheck("Количество иждивенцев (строка)", "0")
                .setFieldByValueCheck("Общий стаж, мес", "60")
                .setHandBookFieldByValueCheck("Тип занятости", "Имеет другой источник дохода")
                .setHandBookFieldByValueCheck("Причина отсутствия работы", "Получатель Д/П");

        ctx.basePage.scrollToTop();

        ctx.buttonsComponent
                .clickButtonByContainNameCheck("ОЦЕНКА ИНФОРМАЦИИ");

        ctx.lookupComponent
                .setHandBookFieldByValueCheck("Тип собственности на недвижимость", "Более 2-ух квартир")
                .setHandBookFieldByValueCheck("Тип владения автомобилем", "Есть автомобиль");

        ctx.checkboxComponent
                .ensureCheckboxChecked("IsConsentBKIProcessing");
    }

    // ----------------------------------------------------------------
    // SAVE & PRINT
    // ----------------------------------------------------------------

    private void saveAndPrintApplication() {

        ctx.basePage
                .clickButtonByNameCheck("Сохранить")
                .clickButtonByNameCheck("Закрыть");

        ctx.buttonsComponent
                .clickButtonByContainName("Участники заявки");

        ctx.basePage
                .clickButtonByNameCheck("Заемщик");

        ctx.detailComponent
                .openDetailMenu("Участники заявки");

        ctx.menuComponent
                .clickButtonByLiName("Печать Анкеты-заявки");
    }
}
