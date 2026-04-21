package flows.credit.participants;

import core.base.UiContext;
import core.base.common.components.LookupComponent;
import core.data.participants.ParticipantData;
import core.data.registration.ExpenseData;
import core.data.registration.IncomeData;
import core.data.registration.RegistrationIncomeExpensesData;
import flows.credit.registration.*;
import io.qameta.allure.Step;



public class GuarantorQuestionnaireFlow {


    private final RegistrationIncomeExpensesFlow incomeExpensesFlow;
    private final UiContext ui;



    public GuarantorQuestionnaireFlow(UiContext ui) {
        this.ui = ui;
        this.incomeExpensesFlow = new RegistrationIncomeExpensesFlow(ui);

    }

    @Step("Заполнение анкеты поручителя")
    public void fill(ParticipantData participant, RegistrationIncomeExpensesData incomeExpensesData) {

        fillBorrowerDocuments();
        fillAddresses();
        fillIncomeAndExpenses(incomeExpensesData);
        fillOtherIncomeData();
        confirmScoring();


    }

    @Step("Заполнение документов заемщика")
    public void fillBorrowerDocuments() {
        /*ui.lookupComponent
                .setHandBookFieldByValueCheck("Вид связи", "Партнер");*/

        //Средства связи
        ui.lookupComponent
                .selectDropdownValueWithCheckNew("BnzAffiliation", "Мобильный");

        ui.lookupComponent
                .setHandBookFieldByValueCheck("Страна рождения", "Точикистон")
                .setFieldByValueCheck("Дата выдачи", "01.01.2020");

        ui.lookupComponent
                .setFieldByValueCheck("Действителен до", "01.01.2030");

        ui.gridComponent
                .DoubleclickByDIM("Шиносномаи ЧТ");

        ui.basePage
                .checkCurrentPage("RegDocumentPageV2Container");

        ui.lookupComponent
                .setFieldByValueCheck("Дата выдачи", "01.01.2020")
                .setFieldByValueCheck("Действует до", "01.01.2030");

        ui.contractPage
                .clickButtonByNameCheck("Сохранить");

        ui.basePage
                .checkCurrentPage("EntityLoaded");
    }


    @Step("Заполнение адресов")
    public void fillAddresses() {

        // Регистрация


        ui.buttonsComponent
                .doubleclickButtonByName("Регистрация");
        ui.contactAddressPage
                .waitForAddressPageLoaded();

        ui.dateFieldComponent
                .setDateFieldByMarker("BnzRegistrationDate", "01.01.2020");
        ui.contractPage
                .clickButtonByNameCheck("Сохранить");

        ui.basePage
                .waitForPage();

        // Фактический
        ui.lookupComponent
                .setHandBookFieldByValueCheck("Тип клиента", "Такрори");

        ui.buttonsComponent
                .doubleclickButtonByName("Фактический");
        ui.contactAddressPage
                .waitForAddressPageLoaded();
        ui.dateFieldComponent
                .setDateFieldByMarker("BnzRegistrationDate", "01.01.2020");
        ui.contractPage
                .clickButtonByNameCheck("Сохранить");
        ui.basePage
                .waitForPage();

        ui.lookupComponent
                .selectDropdownValue("Вид связи", "Партнер");
    }



    @Step("Заполнение доходов и расходов")
    public void fillIncomeAndExpenses(RegistrationIncomeExpensesData data) {

        setRiskAndAdditionalInfo();
        addIncome(data.getIncome());
        addExpense(data.getExpense());
        fillBaseScoringData();

    }


    // ================= PRIVATE =================

    private void setRiskAndAdditionalInfo() {

        ui.lookupComponent
                .setHandBookFieldByValueCheck("Уровень риска", "Низкий");

        ui.buttonsComponent
                .clickButtonByContainNameCheck("ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ");

        ui.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Клиент предоставил подтверждение дохода","Да")
                .setHandBookFieldByValueCheck(
                        "Тип документа подтверждения дохода",
                        "Выписка с банковского счета");
    }

    private void addIncome(IncomeData income) {

        ui.basePage
                .clickButtonById("BnzAppIncomesDetailAddRecordButtonButton-imageEl");

        ui.contractPage
                .setfieldScheduleDetailByDIM("TsiKind", income.getType());
        ui.menuComponent
                .clickButtonByLiName(income.getType());

        ui.contractPage
                .setfieldScheduleDetailByDIM("TsiFrequencyType", income.getFrequency());
        ui.menuComponent
                .clickButtonByLiName(income.getFrequency());

        ui.contractPage
                .setfieldScheduleDetailByDIM("TsiAmountBC", income.getAmount());
        ui.basePage
                .clickButtonByDataItemMaker("save");
    }


    private void addExpense(ExpenseData expense) {

        ui.basePage
                .clickButtonById("BnzAppExpensesDetailAddRecordButtonButton-imageEl");

        ui.checkboxComponent
                .CheckBoxValue("BnzAppExpensesDetailTsiKindComboBoxEdit-el");
        ui.menuComponent
                .clickButtonByLiName(expense.getType());

        ui.checkboxComponent
                .CheckBoxValue("BnzAppExpensesDetailTsiFrequencyTypeComboBoxEdit-el");
        ui.menuComponent
                .clickButtonByLiName(expense.getFrequency());

        ui.domActions
                .clickDivbyId(
                        "BnzAppExpensesDetailTsiAmountBCFloatEdit-wrap",
                        expense.getAmount()
                );
        ui.basePage
                .clickButtonByDataItemMaker("save");
    }


    @Step("Заполнение базовых скоринговых данных заемщика")
    public void fillBaseScoringData() {



        ui.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Семейное положение",
                        "Оиладор (зан)"
                );

        ui.lookupComponent
                .setFieldByValueCheck("Количество иждивенцев (строка)","3")
                .setFieldByValueCheck("Количество детей","3")
                .setFieldByValueCheck("Количество членов семьи","5")
                .setFieldByValueCheck("Общий стаж","36")
                .setFieldByValueCheck("Общий стаж, лет","3")
                .setFieldByValueCheck("Общий стаж, мес","36")
                .setFieldByValueCheck("Стаж на последнем месте, лет","2")
                .setFieldByValueCheck("Стаж на последнем месте, мес","24");


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

    @Step("Подтверждение этапа оценки информации заемщика")
    public void confirmScoring() {

        // Возврат в начало страницы перед подтверждением этапа
        ui.basePage.scrollToTop();

        // Подтверждение этапа скоринга
        ui.buttonsComponent
                .clickButtonByContainNameCheck("ОЦЕНКА ИНФОРМАЦИИ");

        // Дополнительные параметры после подтверждения
        ui.lookupComponent
                .setHandBookFieldByValueCheck("Тип собственности на недвижимость","Более 2-ух квартир")
                .setHandBookFieldByValueCheck("Тип владения автомобилем","Есть автомобиль");

        // Обязательное согласие на обработку данных БКИ
        ui.checkboxComponent
                .ensureCheckboxBKI("IsConsentBKIProcessing");

        ui.contractPage.clickButtonByNameCheck("Сохранить");
        ui.contractPage.clickButtonByNameCheck("Закрыть");
    }


}