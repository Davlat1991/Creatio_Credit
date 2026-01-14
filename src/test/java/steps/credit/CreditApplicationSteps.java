package steps.credit;

import core.base.common.components.*;
import io.qameta.allure.Step;
import core.data.contacts.ContactData;
import core.pages.credit.ConsultationPanelPage;
import core.pages.credit.ContractCreditApplicationPage;
import core.pages.ui.DetailPage;

public class CreditApplicationSteps {

    private final ContractCreditApplicationPage page;
    private final FieldComponent fields;
    private final LookupComponent lookup;
    private final ButtonsComponent buttons;
    private final DashboardComponent dashboard;
    private final MiniPageComponent mini;
    private final FileUploadComponent files;
    private final GridComponent grid;
    private final DetailPage detail;
    private final ConsultationPanelPage consultation;

    private String savedApplicationNumber;

    public CreditApplicationSteps(
            ContractCreditApplicationPage page,
            FieldComponent fields,
            LookupComponent lookup,
            ButtonsComponent buttons,
            DashboardComponent dashboard,
            MiniPageComponent mini,
            FileUploadComponent files,
            GridComponent grid,
            DetailPage detail,
            ConsultationPanelPage consultation
    ) {
        this.page = page;
        this.fields = fields;
        this.lookup = lookup;
        this.buttons = buttons;
        this.dashboard = dashboard;
        this.mini = mini;
        this.files = files;
        this.grid = grid;
        this.detail = detail;
        this.consultation = consultation;
    }

    // -------------------------------------------------------------
    // 🔵 1. НАЧАЛО КОНСУЛЬТАЦИИ
    // -------------------------------------------------------------
    @Step("Начать консультацию и заполнить ФИО клиента")
    public CreditApplicationSteps startConsultation(ContactData c) {

        buttons.clickByName("Поиск");
        buttons.clickByDataItemMarker("Начать консультацию");

        detail.openDetailByName("Оформить заявку");

        consultation.registerProduct("consultation-theme-7a0f11cc-756d-474a-885f-1dd64eeca5b3");
        consultation.registerProductByDIM("consultation-theme-7a0f11cc-756d-474a-885f-1dd64eeca5b3");


        fields.setValue("Фамилия", c.getLastName())
                .setValue("Имя", c.getFirstName())
                .setValue("Отчество", c.getMiddleName());

        return this;
    }

    // -------------------------------------------------------------
    // 🔵 2. ПОДБОР ПРОДУКТА
    // -------------------------------------------------------------
    @Step("Подобрать продукт")
    public CreditApplicationSteps selectProduct() {

        lookup.select("Вид продукта", "Карзхои гуногунмаксад");
        lookup.select("Цель кредитования", "Барои эхтиёчоти оилави");
        fields.setValue("Сумма", "30000");
        fields.setValue("Срок, мес.", "24");
        lookup.select("Валюта", "Сомони Чумхурии Точикистон");

        buttons.clickByName("Подобрать");

        grid.doubleClickFirstRow("TsiOpportunityConditionSelectionDetailDataGrid");
        buttons.clickByName("Выбрать");

        return this;
    }

    // -------------------------------------------------------------
    // 🔵 3. СОЗДАНИЕ ЗАЯВКИ
    // -------------------------------------------------------------
    @Step("Создать кредитную заявку")
    public CreditApplicationSteps createApplication() {

        fields.setValue("Запрашиваемая дата погашения", "3");
        buttons.clickById("KzParameterScheduleDetailAddRecordButtonButton-imageEl");

        fields.setValue("KzNumber", "2");
        lookup.select("KzTypeScheduler", "Аннуитетный");
        fields.setValue("KzTermMonth", "24");

        buttons.clickByDataItemMarker("save");
        buttons.clickByName("Рассчитать");
        buttons.clickByName("Создать заявку");

        return this;
    }

    // -------------------------------------------------------------
    // 🔵 4. СОХРАНИТЬ НОМЕР ЗАЯВКИ
    // -------------------------------------------------------------
    @Step("Сохранить номер созданной заявки")
    public CreditApplicationSteps saveApplicationNumber() {
        this.savedApplicationNumber = fields.getValue("Number");
        return this;
    }

    @Step("Получить номер заявки")
    public String getSavedApplicationNumber() {
        return savedApplicationNumber;
    }

    // -------------------------------------------------------------
    // 🔵 5. ЗАГРУЗКА ДОКУМЕНТОВ
    // -------------------------------------------------------------
   /* @Step("Загрузить документы клиента")
    public CreditApplicationSteps uploadDocuments() {

        buttons.clickByName("Документы");

        detail.openDetailByName("Финансовое досье");
        files.upload("Registration (Example).xlsx");

        detail.openDetailByName("Досье клиента");
        files.upload("Registration (Example).xlsx");

        return this;
    }*/

    // -------------------------------------------------------------
    // 🔵 6. ПРОХОЖДЕНИЕ СТАДИЙ DASHBOARD
    // -------------------------------------------------------------
    @Step("Пройти стадию: {stage}")
    public CreditApplicationSteps passStage(String stage) {

        dashboard.waitProcessBlock(stage);
        dashboard.clickActionWaitMiniPage(stage, "Execute");

        lookup.select("Result", "Выполнена");
        buttons.clickByName("Сохранить");

        return this;
    }

    // -------------------------------------------------------------
    // 🔵 7. ОТКРЫТИЕ ЗАЯВКИ ПО НОМЕРУ
    // -------------------------------------------------------------
    @Step("Найти созданную заявку по номеру")
    public CreditApplicationSteps findApplication() {

        buttons.clickByName("Фильтры/группы");
        buttons.clickByName("Добавить условие");

        lookup.select("columnEdit", "Номер");
        fields.setValue("searchEdit", savedApplicationNumber);

        buttons.clickByDataItemMarker("applyButton");

        grid.selectFirstRow("FinApplicationSectionDataGrid");
        buttons.clickByName("Открыть");

        return this;
    }

    // -------------------------------------------------------------
    // 🔵 8. СОЗДАНИЕ ДОГОВОРА
    // -------------------------------------------------------------
    @Step("Создать договор")
    public CreditApplicationSteps createContract() {

        dashboard.clickAction("Создание договора в АБС (печать договоров для встречи)", "Execute");

        return this;
    }

    // -------------------------------------------------------------
    // 🔵 9. ВЫДАЧА КРЕДИТА
    // -------------------------------------------------------------
    @Step("Выдать кредит способом: {method}")
    public CreditApplicationSteps issueCredit(String method) {

        lookup.selectValue("BnzCreditIssueMethod", method);
        buttons.clickByName("Подтвердить");

        return this;
    }
}
