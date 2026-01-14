package tests.routes;

import com.codeborne.selenide.Selenide;
import core.assertions.FieldAssertions;
import core.assertions.GridAssertions;
import core.base.BaseTest;
import core.base.common.HeaderPage;
import core.base.common.components.*;
import core.base.common.helpers.DomActions;
import core.base.common.utils.FieldUtils;
import core.base.common.utils.FieldValueResolver;
import core.data.contacts.ContactData;
import core.pages.contacts.ContactAddressPage;
import core.pages.login.LoginPage;
import core.pages.ui.ProjectsPage;
import org.testng.annotations.Test;
import steps.credit.CreditApplicationAssertions;
import steps.login.LoginSteps;
import core.data.users.LoginData;

import java.time.LocalDate;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.refresh;
import static core.pages.credit.ContractCreditApplicationPage.CONTRACT_PAGE_MARKER;


/*public class StandartRouteTest extends BaseTest {
    private final LoginSteps login = new LoginSteps();
    private final LoginPage openUrl = new LoginPage();
    CreditApplicationAssertions creditAssert =
            new CreditApplicationAssertions(contractPage);
    MessageBoxComponent messageBoxComponent = new MessageBoxComponent();
    ButtonsComponent buttonsComponent = new ButtonsComponent();
    MenuComponent menuComponent = new MenuComponent();
    GridComponent gridComponent = new GridComponent();
    LookupComponent lookupComponent = new LookupComponent();
    ContactAddressPage contactAddressPage = new ContactAddressPage();
    CheckboxComponent checkboxComponent = new CheckboxComponent();
    FieldComponent fieldComponent = new FieldComponent();
    DetailComponent detailComponent = new DetailComponent();
    DashboardComponent dashboardComponent = new DashboardComponent();
    FileUploadComponent fileUploadComponent = new FileUploadComponent();
    FiltersComponent filtersComponent = new FiltersComponent();
    ProjectsPage projectsPage = new ProjectsPage();
    FieldUtils fieldUtils = new FieldUtils();
    FieldAssertions fieldAssertions = new FieldAssertions();
    PrintComponent printComponent = new PrintComponent();
    HeaderPage headerPage = new HeaderPage();
    FieldValueResolver fieldValueResolver = new FieldValueResolver();
    DateFieldComponent dateFieldComponent = new DateFieldComponent();
    DomActions domActions = new DomActions();
    GridAssertions gridAssertions = new GridAssertions();





    @Test
    public void simpleRouteTest() {

        // 1. Авторизация
        //1. AuthorizationAndClientSearchFlow

        openUrl
                .openUrl(BASE_ULR_1);//.openUrl("");
        login
                .enterUsername(retailManager.getLogin())
                .enterPassword(retailManager.getPassword())
                .clickLogin()
                .verifyLogin();

        // 2. Выбор рабочего места
        workspaceSteps
                .selectWorkAccess("Розничный менеджер");
        basePage
                .clickButtonById("view-button-OBSW-imageEl");

        // 3. Заполнение ФИО
        fieldPage
                .setFieldByValue("Фамилия", contact.getLastName(), true, false)
                .validateFieldValue("Фамилия", contact.getLastName())
                .setFieldByValue("Имя", contact.getFirstName(), true, false)
                .validateFieldValue("Имя", contact.getFirstName())
                .setFieldByValue("Отчество", contact.getMiddleName(), true, false)
                .validateFieldValue("Отчество", contact.getMiddleName());

        // 4. Поиск клиента
        contractPage
                .clickButtonByNameCheck("Поиск");
        basePage
                .clickButtonByDataItemMaker("Начать консультацию");
        detailPage
                .openDetailByName("Оформить заявку");
        consultationPanel.
                registerProductByDIM("consultation-theme-7a0f11cc-756d-474a-885f-1dd64eeca5b3");


        //2. ProductSelectionFlow
        // 4. Подбор продукта
        lookupComponent
                .setHandBookFieldByValueCheck ("Вид продукта", "Карзхои гуногунмаксад")
                .setHandBookFieldByValueCheck("Цель кредитования", "Барои эхтиёчоти оилави")
                .setFieldByValueCheck("Сумма", "50000")
                .setFieldByValueCheck("Срок, мес.", "36")
                .setHandBookFieldByValueCheck("Валюта", "Сомони Чумхурии Точикистон");
        contractPage
                .clickButtonByNameCheck("Подобрать");
        contractPage
                .clickFirstRowInGridAndWaitButton("grid-TsiOpportunityConditionSelectionDetailDataGridGrid-wrap","Выбрать");
        basePage.
                clickButtonByDataItemMakerCheck("Выбрать");

        //3.ApplicationCreationFlow

        lookupComponent
                .setFieldByValueCheck("Запрашиваемая дата погашения", "3");
        basePage
                .clickButtonById("KzParameterScheduleDetailAddRecordButtonButton-imageEl"); // Нажать на кнопку "+"

        contractPage
                .setfieldScheduleDetailByDIM("KzNumber","2")
                .setHandBookFieldByValue ("KzTypeScheduler", "Аннуитетный")
                .setfieldScheduleDetailByDIM("KzTermMonth","36");
        basePage
                .clickButtonByDataItemMaker("save")
                .clickButtonByNameCheck("Рассчитать")
                .clickButtonByNameCheck("Создать заявку");
        messageBoxComponent
                .shouldSeeModalWithText("Нет задолженности!"); // Проверка текста модального окна
        basePage
                .clickButtonByDataItemMaker("ОК");


        //4. RegistrationStageFlow

        lookupComponent
                .setHandBookFieldByValueCheck("Регион использования кредита", "ш. Хучанд")
                .setHandBookFieldByValueCheck("Источник информации", "Интернет/Сайт");
        buttonsComponent
                .clickButtonByContainName("Участники заявки");
        //.doubleclickButtonByName("Заемщик"); //Метод для продолжения заявки

        //Добавление "Заёмщика"
        basePage
                .clickButtonById("BnzVwFinApplicationAllParticipantDetailAddTypedRecordButtonButton-imageEl"); // Нажать на кнопку "+"
        menuComponent
                .clickButtonByLiName("Заемщик");

        // Заполнение чекбокса в анкете заёмщика "Согласие на обработку БКИ"

        lookupComponent
                .setHandBookFieldByValueCheck("Страна рождения", "Точикистон")
                .setFieldByValueCheck("Дата выдачи", "01.01.2020");
        fieldValueResolver
                .getNonEmptyValue("Дата выдачи");
        lookupComponent
                .setFieldByValueCheck("Действителен до", "01.01.2030");
        fieldValueResolver
                .getNonEmptyValue("Действителен до");
        gridComponent
                .DoubleclickByDIM("Шиносномаи ЧТ");
        basePage
                .checkCurrentPage("RegDocumentPageV2Container"); //Проверка текущей страницы
        lookupComponent
                .setFieldByValueCheck("Дата выдачи", "01.01.2020");
        fieldValueResolver
                .getNonEmptyValue("Дата выдачи");
        lookupComponent
                .setFieldByValueCheck("Действует до", "01.01.2030");
        fieldValueResolver
                .getNonEmptyValue("Действует до");
        contractPage
                .clickButtonByNameCheck("Сохранить");
        basePage
                .checkCurrentPage("EntityLoaded");
        lookupComponent
                .selectDropdownValueWithCheck("BnzAffiliation","Мобильный");
        buttonsComponent
                .doubleclickButtonByName("Регистрация"); //Деталь адреса - строка типа адреса "Регистрация"
        basePage
                .checkCurrentPage("ContactAddressPageV2Container"); //Проверка текущей страницы
        contactAddressPage
                .waitForAddressPageLoaded();
        dateFieldComponent
                .setDateFieldByMarker("BnzRegistrationDate", "01.01.2020");
        fieldValueResolver
                .getNonEmptyValue("Дата регистрации");
        contractPage
                .clickButtonByNameCheck("Сохранить");
        basePage
                .waitForPage();
        lookupComponent
                .setHandBookFieldByValueCheck("Тип клиента", "Нав");
        buttonsComponent
                .doubleclickButtonByName("Фактический");//Деталь адреса - строка типа адреса "Фактический"
        basePage
                .checkCurrentPage("ContactAddressPageV2Container"); //Проверка текущей страницы
        contactAddressPage
                .waitForAddressPageLoaded();
        dateFieldComponent
                .setDateFieldByMarker("BnzRegistrationDate", "01.01.2020");
        fieldValueResolver
                .getFieldValueSmart("Дата регистрации");
        contractPage
                .clickButtonByNameCheck("Сохранить");
        lookupComponent
                .setHandBookFieldByValueCheck("Уровень риска","Низкий");
        buttonsComponent
                .clickButtonByContainNameCheck("ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ");
        lookupComponent
                .setHandBookFieldByValueCheck("Клиент предоставил подтверждение дохода","Да")
                .setHandBookFieldByValueCheck("Тип документа подтверждения дохода","Выписка с банковского счета");
        basePage
                .clickButtonById("BnzAppIncomesDetailAddRecordButtonButton-imageEl"); //Нажать на кнопку "+" в детали "Доходы"
        contractPage
                .setfieldScheduleDetailByDIM("TsiKind","Зарплата по основному месту работы");
        menuComponent
                .clickButtonByLiName("Зарплата по основному месту работы");
        contractPage
                .setfieldScheduleDetailByDIM("TsiFrequencyType","Ежемесячно");
        menuComponent
                .clickButtonByLiName("Ежемесячно");
        contractPage
                .setfieldScheduleDetailByDIM("TsiAmountBC","11000");
        basePage
                .clickButtonById("BnzAppExpensesDetailAddRecordButtonButton-imageEl"); // Нажать на кнопку "+" в детали "Расходы"
        checkboxComponent
                .CheckBoxValue("BnzAppExpensesDetailTsiKindComboBoxEdit-el");
        menuComponent
                .clickButtonByLiName("Коммунальные платежи");
        checkboxComponent
                .CheckBoxValue("BnzAppExpensesDetailTsiFrequencyTypeComboBoxEdit-el");
        menuComponent
                .clickButtonByLiName("Единоразово");
        domActions
                .clickDivbyId("BnzAppExpensesDetailTsiAmountBCFloatEdit-wrap","600");
        lookupComponent
                .setHandBookFieldByValueCheck("Семейное положение","Мучаррад (мард)")
                .setFieldByValueCheck("Количество иждивенцев (строка)","0")
                .setFieldByValueCheck("Общий стаж, мес","60")
                .setHandBookFieldByValueCheck("Тип занятости","Имеет другой источник дохода")
                .setHandBookFieldByValueCheck("Причина отсутствия работы","Получатель Д/П");
        basePage
                .scrollToTop();
        buttonsComponent
                .clickButtonByContainNameCheck("ОЦЕНКА ИНФОРМАЦИИ");
        lookupComponent
                .setHandBookFieldByValueCheck("Тип собственности на недвижимость","Более 2-ух квартир")
                .setHandBookFieldByValueCheck("Тип владения автомобилем","Есть автомобиль");
        checkboxComponent
                .ensureCheckboxChecked("IsConsentBKIProcessing");
        basePage
                .clickButtonByNameCheck("Сохранить")
                .clickButtonByNameCheck("Закрыть");
        buttonsComponent
                .clickButtonByContainName("Участники заявки");
        basePage
                .clickButtonByNameCheck("Заемщик");
        detailComponent
                .openDetailMenu("Участники заявки"); // кнопка "Действия" (Троеточие)
        menuComponent
                .clickButtonByLiName("Печать Анкеты-заявки");

        //5. PreliminaryCheckStageFlow

        dashboardComponent
                .clickElementDashboardCheck("Добавьте и заполните анкеты участников заявки","Execute",
                        "//*[@data-item-marker='MiniPage']"); //Нажать на кнопку "Завершить"

        contractPage
                .setfieldScheduleDetailByDIM("Result","Выполнена");
        menuComponent
                .clickButtonByLiName("Выполнена");
        basePage
                .clickButtonByDataItemMakerCheck("SaveEditButton"); //Кнопка "Сохранить"
        messageBoxComponent
                .shouldSeeModalWithText("Нет задолженности!"); // Проверка текста модального окна
        basePage
                .clickButtonByDataItemMaker("ОК");
        messageBoxComponent
                .shouldSeeModalWithText("У клиента нет просроченных дней"); // Проверка текста модального окна
        basePage
                .clickButtonByDataItemMaker("ОК");

        //2.Стадия "Предварительная проверка" Процесс заполнения заявки кредита Физ.Лица
        //Тест-кейс №2

        dashboardComponent.
                clickElementDashboardWait("Проверка клиента","Approve"); //Кнопка "Утвердить"
        basePage
                .clickButtonByDataItemMaker("SaveEditButton"); //Кнопка "Выполнить"
        dashboardComponent
                .clickElementDashboardCheck("Заполните данные обеспечения и поручительства","Execute",
                        "//*[@data-item-marker='MiniPage']"); //Нажать на кнопку "Завершить"

        contractPage
                .setfieldScheduleDetailByDIM("ProcessResult","Выполнена");
        menuComponent
                .clickButtonByLiName("Выполнена");
        basePage
                .clickButtonByDataItemMaker("SaveEditButton"); //Нажать на кнопку "Сохранить"


        //6.DocumentsStageFlow

        //4.Стадия "Сбор документов" Процесс заполнения заявки кредита Физ.Лица
        //Тест-кейс №3 - Загрузка документов заёмщика

        buttonsComponent
                .clickButtonByContainName("Документы"); //Переход на вкладку "Документы"
        detailPage
                .openDetailByName("Финансовое досье");

        fileUploadComponent
                .startUpload()
                .uploadFile("Registration (Example).xlsx",1);
        buttonsComponent
                .clickButtonByNameContains("Файлы", 1);
        fileUploadComponent
                .validateUploadFile("Registration (Example).xlsx");
        detailPage
                .openDetailByName("Досье клиента");
        fileUploadComponent
                .uploadFile("Registration (Example).xlsx",2);
        buttonsComponent
                .clickButtonByNameContains("Файлы", 2);
        fileUploadComponent
                .validateUploadFile("Registration (Example).xlsx");
        fileUploadComponent
                .uploadFile("Registration (Example).xlsx",3);
        buttonsComponent
                .clickButtonByNameContains("Файлы", 3);
        fileUploadComponent
                .validateUploadFile("Registration (Example).xlsx");


        //7. ReviewStageFlow


        dashboardComponent
                .clickElementDashboardCheck("Вложить документы и отправить на рассмотрение","Execute",
                        "//*[@data-item-marker='MiniPage']"); //Нажать на кнопку "Завершить"
        contractPage
                .setfieldScheduleDetailByDIM("Result","Выполнена");
        menuComponent
                .clickButtonByLiName("Выполнена");
        basePage
                .clickButtonByDataItemMaker("SaveEditButton"); //Нажать на кнопку "Сохранить"
        buttonsComponent
                .clickButtonByContainNameCheck("Проверки"); //Открыть вкладку "Проверки"

        //  ЯВНОЕ ОЖИДАНИЕ 20 СЕКУНДЫ ПЕРЕД ПРОВЕРКОЙ НОВОЙ СТРАНИЦЫ
        Selenide.sleep(20000);

        basePage
                .scrollDownSmall();

        gridAssertions
                .waitForCreditDecision("Решение по кредиту","Одобрить");

        refresh();

        contractPage
                .safeClick($("#FinApplicationPageTabsTabPanel-scroll-right")); //Скролл вправо (Вкладки)

        buttonsComponent
                .clickButtonByContainNameCheck("Решение по заявке"); //Открыть вкладку "Решение по заявке"
        gridAssertions
                .waitForValueInGridColumn("Комитет","КК4");
        contractPage
                .saveValueByMarker("Number");//Скопировать номер "Заявки"
        headerPage
                .logout(); //Выйти из системы

        openUrl
                .openUrl(BASE_ULR_1);//.openUrl("");
        login
                .enterUsername(retailManager.getLogin())
                .enterPassword(retailManager.getPassword())
                .clickLogin()
                .verifyLogin();

        basePage
                .clickByDataMarker("Заявки"); //Нажать в разделе на кнопку "Заявки"
        filtersComponent
                .removeFilterIfExists();//Закрыть предыдущий результат поиска
        basePage
                .clickButtonByNameCheck("Фильтры/группы"); // Нажать на "Фильтры/группы"
        menuComponent
                .clickButtonByLiName("Добавить условие");//Выбрать "Добавить условие"
        lookupComponent
                .setFieldScheduleDetailByDIMCheck("columnEdit","Номер"); //Первое поле выбрать "Номер"
        contractPage
                .applySavedValueIntoField("searchEdit","applyButton"); //Вставить скопированный номер заявки, нажать на кнопку галочки
        gridComponent
                .clickFirstRowInGridAndWaitButton("grid-FinApplicationSectionDataGridGrid-wrap","Открыть"); //Выделить найденный результат
        basePage
                .clickButtonByDataItemMakerCheck("Открыть") //Нажать на кнопку "Открыть"
                .safeClick($("#FinApplicationPageTabsTabPanel-scroll-right")); //Скролл вправо (Вкладки)

        buttonsComponent
                .clickButtonByContainNameCheck("Решение по заявке"); //Открыть вкладку "Решение по заявке"
        projectsPage
                .openProjectByName("КК4 по заявке");//Нажать на ссылку "КК4 по заявке" и открыть страницу "Проект решения"
        basePage
                .waitAndClickByDIM("TakeToWorkButton"); //Нажать на кнопку "Взять в работу"
        basePage
                .waitAndClickByMarkerNew("ApproveButton");  //Нажать кнопку "Утвердить"

        headerPage
                .logout(); //Выйти из системы


        //8 ClientNotificationStageFlow

        openUrl
                .openUrl(BASE_ULR_1);//.openUrl("");
        login
                .enterUsername(retailManager.getLogin())
                .enterPassword(retailManager.getPassword())
                .clickLogin()
                .verifyLogin();

        basePage
                .clickByDataMarker("Заявки"); //Нажать в разделе на кнопку "Заявки"
        filtersComponent
                .removeFilterIfExists();//Закрыть предыдущий результат поиска
        basePage
                .clickButtonByNameCheck("Фильтры/группы"); // Нажать на "Фильтры/группы"
        menuComponent
                .clickButtonByLiName("Добавить условие");//Выбрать "Добавить условие"
        lookupComponent
                .setFieldScheduleDetailByDIMNewCheck("columnEdit","Номер"); //Первое поле выбрать "Номер"
        contractPage
                .applySavedValueIntoField("searchEdit","applyButton"); //Вставить скопированный номер заявки, нажать на кнопку галочки
        gridComponent
                .clickFirstRowInGridAndWaitButton("grid-FinApplicationSectionDataGridGrid-wrap","Открыть"); //Выделить найденный результат
        basePage
                .clickButtonByDataItemMakerCheck("Открыть"); //Нажать на кнопку "Открыть"

        dashboardComponent
                .clickElementDashboardCheck("Информирование клиента","Execute",
                        "//*[@data-item-marker='MiniPage']"); //Нажать на кнопку "Завершить"
                //.clickElementDashboard("Укажите дату и время идентификации клиента","Execute") //Нажать на кнопку "Завершить" - Продолжение заявки

        contractPage
                .setfieldScheduleDetailByDIM("Result","Клиент согласен");
        menuComponent
                .clickButtonByLiName("Клиент согласен");
        basePage
                .clickButtonByDataItemMaker("SaveEditButton"); //Нажать на кнопку "Сохранить"
        lookupComponent
                .setHandBookFieldByValueCheck("Ответственный за подписание", "Рустамова Саодатчон Валиевна");
        basePage
                .clickButtonByNameCheck("Подтвердить");
        headerPage
                .logout(); //Выйти из системы

        //9. LoanIssuanceFlow

        openUrl
                .openUrl(BASE_ULR_1);//.openUrl("");
        login
                .enterUsername(retailManager.getLogin())
                .enterPassword(retailManager.getPassword())
                .clickLogin()
                .verifyLogin();

        basePage
                .clickByDataMarker("Заявки"); //Нажать в разделе на кнопку "Заявки"
        filtersComponent
                .removeFilterIfExists();//Закрыть предыдущий результат поиска
        basePage
                .clickButtonByNameCheck("Фильтры/группы"); // Нажать на "Фильтры/группы"
        menuComponent
                .clickButtonByLiName("Добавить условие"); //Выбрать "Добавить условие"
        lookupComponent
                .setFieldScheduleDetailByDIMNewCheck("columnEdit","Номер"); //Первое поле выбрать "Номер"
        contractPage
                .applySavedValueIntoField("searchEdit","applyButton"); //Вставить скопированный номер заявки, нажать на кнопку галочки
        gridComponent
                .clickFirstRowInGridAndWaitButton("grid-FinApplicationSectionDataGridGrid-wrap","Открыть"); //Выделить найденный результат
        basePage
                .clickButtonByDataItemMakerCheck("Открыть"); //Нажать на кнопку "Открыть"
        dashboardComponent
                .clickElementDashboardCheck("Проверка решения КК","Execute",
                        "//*[@data-item-marker='MiniPage']"); //Нажать на кнопку "Завершить"

        contractPage
                .setfieldScheduleDetailByDIM("Result","Выполнена");
        menuComponent
                .clickButtonByLiName("Выполнена");
        basePage
                .clickButtonByDataItemMaker("SaveEditButton"); //Нажать на кнопку "Сохранить"
        dashboardComponent
                .clickElementDashboardName("Создание договора в АБС (печать договоров для встречи)");
        contractPage
                .clickContractAutoWait(CONTRACT_PAGE_MARKER); //Нажать на номер договора
        basePage
                .clickButtonOnPageByName(CONTRACT_PAGE_MARKER,"Действия");  //Создание договора
        menuComponent
                .clickButtonByLiName("Создание договора");
        lookupComponent
                .setHandBookFieldByValueCheck("Вид планирования","Аннуитетный_005");
        fieldValueResolver
                .getFieldValueSmart("Вид планирования");
        contractPage
                .selectLoadCreditTypeNew("Получает сегодня")
                .fillLoadCreditTypeSafely("Получает сегодня");
        fieldValueResolver
                .getFieldValueSmart("Тип получения кредита");
        basePage
                .clickButtonByName("Выбрать");
        messageBoxComponent
                .shouldSeeModalWithText("Нет задолженности!"); // Проверка текста модального окна
        basePage
                .clickButtonByNameCheck("ОК");
        messageBoxComponent
                .shouldSeeModalWithText("Договор успешно создан"); // Проверка текста модального окна "Создание кредитного договора"
        basePage
                .clickButtonByNameCheck("ОК")
                .clickButtonById("BnzContractCreditPageBnzCreateSavingAcountContractButtonButton-imageEl"); // Нажать на кнопку "+"
        messageBoxComponent
                .shouldSeeModalWithText("Депозитный договор успешно создан в АБС");
        basePage
                .clickButtonByNameCheck("ОК");
        fieldUtils
                .saveValueDIMCheckWork("BnzDepositBankAccount");
        contractPage
                .clickButtonByNameCheck("Закрыть");

        refresh();

        lookupComponent
                .clickSearchIconID("BnzContractCreditPageBnzCreditBankAccountLookupEdit")
                .selectValueInLookupWork("searchEdit");
        basePage
                .clickButtonByNameCheck("Сохранить")
                .clickButtonByNameCheck("Действия"); //Получение графика платежей
        messageBoxComponent
                .clickAndCheckModal("Получение графика платежей")
                .shouldSeeModalWithText("График платежей успешно получен"); // Проверка текста модального окна
        basePage
                .clickButtonByNameCheck("ОК")
                .clickButtonByNameCheck("Действия"); //Привязка счета к договору
        messageBoxComponent
                .clickAndCheckModal("Привязка счета к договору")
                .shouldSeeModalWithText("Счет успешно привязан к кредитному договору"); // Проверка текста модального окна
        basePage
                .clickButtonByNameCheck("ОК");

        dashboardComponent
                .clickElementDashboardCheck("Создание договора в АБС (печать договоров для встречи)","Execute",
                        "//*[@data-item-marker='MiniPage']"); //Нажать на кнопку "Завершить"

        contractPage
                .setfieldScheduleDetailByDIM("Result","Выполнена");
        menuComponent
                .clickButtonByLiName("Выполнена");
        basePage
                .clickButtonByDataItemMaker("SaveEditButton") //Нажать на кнопку "Сохранить"
                .clickButtonByNameCheck("Действия"); //Подписание договора
        messageBoxComponent
                .clickAndCheckModal("Подписание договора")
                .shouldSeeModalWithText("Резервирование счетов выполнено успешно"); // Проверка текста модального окна
        basePage
                .clickButtonByNameCheck("ОК")
                .clickButtonByNameCheck("Действия"); //Выдача кредита
        contractPage
                .issueCreditUniversal("Наличными");
        basePage
                .clickButtonByNameCheck("Подтвердить");
        messageBoxComponent
                .shouldBeModalOpened("Комиссия за выдачу кредита");
        basePage
                .clickButtonByNameCheck("Подтвердить");
        messageBoxComponent
                .shouldSeeModalWithText("Действие успешно выполнено. Необходимо оплатить комиссию за кредит");
        basePage
                .clickButtonByNameCheck("ОК");
        messageBoxComponent
                .shouldSeeModalWithText("Кассовый ордер успешно сформирован");
        basePage
                .clickButtonByNameCheck("ОК");
        messageBoxComponent
                .shouldSeeModalWithText("График платежей успешно получен");
        basePage
                .clickButtonByNameCheck("ОК");

        // Продолжение заявки
        /*contractCredit
                .safeClick($("#FinApplicationPageTabsTabPanel-scroll-right")); //Скролл вправо (Вкладки)
        contractCredit
                .clickButtonByContainNameCheck("История")
                .clickLookupLinkByLabel("Договор",CONTRACT_PAGE_MARKER);*/  //Нажать на кнопку договора через вкладку "История" // Продолжение заявки
        // Продолжение заявки */

        /*contractPage
                .saveValueByMarker("Number");//Копирование номера "Кредитного договора"
        buttonsComponent
                .clickButtonByContainNameCheck("Операции по договору"); //Перейти на вкладку "Операции по договору"
        buttonsComponent
                .doubleclickButtonByName("Приходный"); //Проверка наличия ордеров
        basePage
                .checkCurrentPage("BnzOrderPageContainer"); //Проверка текущей страницы
        creditAssert
                .assertOrderState("Новый");  //Проверить состояние ордеров
        fieldAssertions
                .checkFieldValueNormalized("Сумма документа","200,00");//Проверить поле суммы
        basePage
                .clickButtonByNameCheck("Закрыть");//Нажать кнопку закрыть
        buttonsComponent
                .doubleclickButtonByName("Расходный");  //Проверка наличия ордеров
        basePage
                .checkCurrentPage("BnzOrderPageContainer");//Проверка текущей страницы
        creditAssert
                .assertOrderState("Новый");  //Проверить состояние ордеров
        fieldAssertions
                .checkFieldValueNormalized("Сумма документа","50 000,00");//Проверить поле суммы
        basePage
                .clickButtonByNameCheck("Закрыть");//Нажать кнопку закрыть
        printComponent
                .selectPrintOption("Чек лист маълумотнома(оферта)");//нажать на кнопку "ПЕЧАТЬ" Скачать ПФ "Чек лист маълумотнома (Оферта)"

        headerPage
                .logout(); //Выйти из системы

        //10. SigningStageFlow

        openUrl
                .openUrl(BASE_ULR_1);//.openUrl("");
        login
                .enterUsername(retailManager.getLogin())
                .enterPassword(retailManager.getPassword())
                .clickLogin()
                .verifyLogin();

        basePage
                .clickByDataMarker("Договоры"); //Перейти в раздел "Договоры"
        filtersComponent
                .removeFilterIfExists();//Закрыть предыдущий результат поиска
        basePage
                .clickButtonByNameCheck("Фильтры/группы"); // Нажать на "Фильтры/группы"
        menuComponent
                .clickButtonByLiName("Добавить условие");//Выбрать "Добавить условие"
        lookupComponent
                .setFieldScheduleDetailByDIMNewCheck("columnEdit","Номер"); //Первое поле выбрать "Номер"
        contractPage
                .applySavedValueIntoField("searchEdit","applyButton"); //Вставить скопированный номер договора, нажать на кнопку галочки
        gridComponent
                .clickFirstRowInGridAndWaitButton("grid-ContractSectionV2DataGridGrid-wrap","Открыть"); //Выделить найденный результат
        basePage
                .clickButtonByDataItemMakerCheck("Открыть"); //Нажать на кнопку "Открыть"
        buttonsComponent
                .clickButtonByContainNameCheck("Операции по договору"); //Перейти на вкладку "Операции по договору"
        buttonsComponent
                .doubleclickButtonByName("Приходный"); //Открыть ордер у которого тип ордера "Приходный"
        basePage
                .checkCurrentPage("BnzOrderPageContainer"); //Проверка текущей страницы
        creditAssert
                .assertOrderState("Новый");  //Проверить состояние ордеров
        fieldAssertions
                .checkFieldValueNormalized("Сумма документа","200,00");//Проверить поле суммы
        basePage
                .clickButtonByNameCheck("Подтвердить");//Нажать на кнопку "Подтвердить"
        messageBoxComponent
                .shouldSeeModalWithText("Кассовый ордер успешно подтвержден");//Проверить текст модального окна
        basePage
                .clickButtonByNameCheck("ОК");//Нажать кнопку "ОК"
        creditAssert
                .assertOrderState("Подтвержден");  //Проверить состояние ордеров
        printComponent
                .selectPrintOption("Приходный кассовый ордер по комиссии");//нажать на кнопку "ПЕЧАТЬ" //Скачать ПФ "Взымание комиссии"
        basePage
                .clickButtonByNameCheck("Закрыть");//Нажать кнопку закрыть
        buttonsComponent
                .doubleclickButtonByName("Расходный"); //Открыть ордер у которого тип ордера "Расходный"
        basePage
                .checkCurrentPage("BnzOrderPageContainer"); //Проверка текущей страницы
        creditAssert
                .assertOrderState("Новый");  //Проверить состояние ордеров
        fieldAssertions
                .checkFieldValueNormalized("Сумма документа","50 000,00");//Проверить поле суммы
        basePage
                .clickButtonByNameCheck("Подтвердить");//Нажать на кнопку "Подтвердить"
        messageBoxComponent
                .shouldSeeModalWithText("Кассовый ордер успешно подтвержден");//Проверить текст модального окна
        basePage
                .clickButtonByNameCheck("ОК");//Нажать кнопку "ОК"
        creditAssert
                .assertOrderState("Подтвержден");  //Проверить состояние ордеров
        printComponent
                .selectPrintOption("Расходный кассовый ордер");//нажать на кнопку "ПЕЧАТЬ" Скачать ПФ "Выдача кредита"
        basePage
                .clickButtonByNameCheck("Закрыть");//Нажать кнопку закрыть

        headerPage
                .logout(); //Выйти из системы

        //11. ApplicationFinishFlow

        openUrl
                .openUrl(BASE_ULR_1);//.openUrl("");
        login
                .enterUsername(retailManager.getLogin())
                .enterPassword(retailManager.getPassword())
                .clickLogin()
                .verifyLogin();

        basePage
                .clickByDataMarker("Договоры"); //Перейти в раздел "Договоры"
        filtersComponent
                .removeFilterIfExists();//Закрыть предыдущий результат поиска
        basePage
                .clickButtonByNameCheck("Фильтры/группы"); // Нажать на "Фильтры/группы"
        menuComponent
                .clickButtonByLiName("Добавить условие");//Выбрать "Добавить условие"
        lookupComponent
                .setFieldScheduleDetailByDIMNewCheck("columnEdit","Номер"); //Первое поле выбрать "Номер"
        contractPage
                .applySavedValueIntoField("searchEdit","applyButton"); //Вставить скопированный номер договора, нажать на кнопку галочки
        gridComponent
                .clickFirstRowInGridAndWaitButton("grid-ContractSectionV2DataGridGrid-wrap","Открыть"); //Выделить найденный результат
        basePage
                .clickButtonByDataItemMakerCheck("Открыть"); //Нажать на кнопку "Открыть"
        buttonsComponent
                .clickButtonByContainNameCheck("Операции по договору") //Перейти на вкладку "Операции по договору"
                .doubleclickButtonByName("Приходный"); //Проверка наличия ордеров
        basePage
                .checkCurrentPage("BnzOrderPageContainer"); //Проверка текущей страницы
        creditAssert
                .assertOrderState("Подтвержден");  //Проверить состояние ордеров
        basePage
                .clickButtonByNameCheck("Закрыть");//Нажать кнопку закрыть
        buttonsComponent
                .doubleclickButtonByName("Расходный");  //Проверка наличия ордеров
        basePage
                .checkCurrentPage("BnzOrderPageContainer"); //Проверка текущей страницы
        creditAssert
                .assertOrderState("Подтвержден");  //Проверить состояние ордеров
        basePage
                .clickButtonByNameCheck("Закрыть")//Нажать кнопку закрыть
                .clickButtonById("view-button-OBSW-imageEl"); //Открыть "Консультационную панель"
        contractPage
                .completeConsultation();  //Завершить выдачу кредита

        headerPage
                .logout(); //Выйти из системы

    }

}*/





