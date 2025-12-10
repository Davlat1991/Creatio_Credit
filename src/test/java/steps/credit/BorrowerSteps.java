package steps.credit;


import core.base.common.components.*;
import io.qameta.allure.Step;
import core.pages.ui.DetailPage;

/**
 * Шаги для работы с анкетой Заёмщика.
 * Выделены из большого теста. Работают через новые компоненты.
 */
public class BorrowerSteps {

    private final FieldComponent fields = new FieldComponent();
    private final LookupComponent lookup = new LookupComponent();
    private final CheckboxComponent checkbox = new CheckboxComponent();
    private final MiniPageComponent miniPage = new MiniPageComponent();
    private final ButtonsComponent buttons = new ButtonsComponent();
    private final DashboardComponent dashboard = new DashboardComponent();
    private final DetailPage detail = new DetailPage();


    // ================================
    // 1. ДОБАВЛЕНИЕ ЗАЁМЩИКА В ЗАЯВКУ
    // ================================
    @Step("Добавить Заёмщика в заявку")
    public BorrowerSteps addBorrower() {

        buttons.clickById("BnzVwFinApplicationAllParticipantDetailAddTypedRecordButtonButton-imageEl");
        miniPage.clickListItem("Заемщик");

        return this;
    }


    // ================================
    // 2. ЗАПОЛНЕНИЕ ОСНОВНЫХ ДАННЫХ
    // ================================
    @Step("Заполнить основные данные Заёмщика")
    public BorrowerSteps fillMainInfo() {

        lookup.select("Тип клиента", "Нав");
        lookup.select("Страна рождения", "Точикистон");

        fields.setValue("Дата выдачи", "01.01.2020");
        fields.setValue("Действителен до", "01.01.2030");

        return this;
    }


    // ================================
    // 3. ПАСПОРТ: ОТКРЫТЬ → ЗАПОЛНИТЬ → СОХРАНИТЬ
    // ================================
    @Step("Открыть и заполнить паспортные данные")
    public BorrowerSteps fillPassport() {

        // открыть паспорт
        buttons.doubleClickByMarker("Шиносномаи ЧТ");
        miniPage.waitOpen("RegDocumentPageV2Container");

        // заполнение полей
        fields.setValue("Дата выдачи", "01.01.2020");
        fields.setValue("Действует до", "01.01.2030");

        buttons.clickByName("Сохранить");
        miniPage.waitClose();

        return this;
    }


    // ================================
    // 4. АДРЕСА: Регистрация + Фактический
    // ================================
    @Step("Заполнить адрес регистрации")
    public BorrowerSteps fillRegistrationAddress() {

        buttons.doubleClickByName("Регистрация");
        miniPage.waitOpen("ContactAddressPageV2Container");

        fields.setValue("Дата регистрации", "01.01.2020");

        buttons.clickByName("Сохранить");
        miniPage.waitClose();

        return this;
    }

    @Step("Заполнить фактический адрес")
    public BorrowerSteps fillActualAddress() {

        buttons.doubleClickByName("Фактический");
        miniPage.waitOpen("ContactAddressPageV2Container");

        fields.setValue("Дата регистрации", "01.01.2020");

        buttons.clickByName("Сохранить");
        miniPage.waitClose();

        return this;
    }


    // ================================
    // 5. ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ
    // ================================
    @Step("Заполнить дополнительную информацию")
    public BorrowerSteps fillAdditionalInfo() {

        lookup.select("Клиент предоставил подтверждение дохода", "Да");
        lookup.select("Тип документа подтверждения дохода", "Выписка с банковского счета");

        return this;
    }


    // ================================
    // 6. ДОХОДЫ
    // ================================
    @Step("Добавить доход Заёмщика")
    public BorrowerSteps addIncome() {

        buttons.clickById("BnzAppIncomesDetailAddRecordButtonButton-imageEl");

        lookup.select("TsiKind", "Зарплата по основному месту работы");
        lookup.select("TsiFrequencyType", "Ежемесячно");

        fields.setValue("TsiAmountBC", "11000");

        return this;
    }


    // ================================
    // 7. РАСХОДЫ
    // ================================
    @Step("Добавить расход Заёмщика")
    public BorrowerSteps addExpense() {

        buttons.clickById("BnzAppExpensesDetailAddRecordButtonButton-imageEl");

        lookup.select("TsiKind", "Коммунальные платежи");
        lookup.select("TsiFrequencyType", "Единоразово");

        fields.setValue("TsiAmountBC", "600");

        return this;
    }


    // ================================
    // 8. СОЦИАЛЬНЫЕ ДАННЫЕ + ОЦЕНКА РИСКОВ
    // ================================
    @Step("Заполнить социальные данные")
    public BorrowerSteps fillSocialData() {

        lookup.select("Семейное положение", "Мучаррад (мард)");
        fields.setValue("Количество иждивенцев (строка)", "0");
        fields.setValue("Общий стаж, мес", "60");

        lookup.select("Тип занятости", "Имеет другой источник дохода");
        lookup.select("Причина отсутствия работы", "Получатель Д/П");

        return this;
    }

    @Step("Заполнить оценку информации")
    public BorrowerSteps fillRiskAssessment() {

        lookup.select("Тип собственности на недвижимость", "Более 2-ух квартир");
        lookup.select("Тип владения автомобилем", "Есть автомобиль");
        lookup.select("Уровень риска", "Низкий");

        checkbox.setChecked("IsConsentBKIProcessing");

        buttons.clickByName("Сохранить");
        buttons.clickByName("Закрыть");

        return this;
    }
}
