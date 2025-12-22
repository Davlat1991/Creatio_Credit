package tests.routes;


import com.codeborne.selenide.Selenide;
import core.base.BaseTest;
import core.data.contacts.ContactData;
import core.pages.login.LoginPage;
import org.testng.annotations.Test;
import steps.credit.CreditApplicationAssertions;
import steps.login.LoginSteps;
import core.data.users.LoginData;

import java.time.LocalDate;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.refresh;
import static core.pages.credit.ContractCreditApplicationPage.CONTRACT_PAGE_MARKER;


public class SimpleRouteTest extends BaseTest {
    private final LoginSteps login = new LoginSteps();
    private final LoginPage openUrl = new LoginPage();
    CreditApplicationAssertions creditAssert =
            new CreditApplicationAssertions(contractPage);


    @Test
    public void simpleRouteTest() {

        // 1. Авторизация

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

        // 4. Подбор продукта
        fieldPage
                .setHandBookFieldByValueCheck ("Вид продукта", "Карзхои гуногунмаксад")
                .setHandBookFieldByValueCheck("Цель кредитования", "Барои эхтиёчоти оилави")
                .setFieldByValueCheck("Сумма", "50000")
                .setFieldByValueCheck("Срок, мес.", "36")
                .setHandBookFieldByValueCheck("Валюта", "Сомони Чумхурии Точикистон");
        contractPage
                .clickButtonByNameCheck("Подобрать")
                .clickFirstRowInGridAndWaitButton("grid-TsiOpportunityConditionSelectionDetailDataGridGrid-wrap","Выбрать");
        basePage.
                clickButtonByDataItemMakerCheck("Выбрать");
        fieldPage
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
        contractPage
                .shouldSeeModalWithText("Нет задолженности!"); // Проверка текста модального окна
        basePage
                .clickButtonByDataItemMaker("ОК");
        fieldPage
                .setHandBookFieldByValueCheck("Регион использования кредита", "ш. Хучанд")
                .setHandBookFieldByValueCheck("Источник информации", "Интернет/Сайт");
        contractPage
                .clickButtonByContainName("Участники заявки");
              //.doubleclickButtonByName("Заемщик"); //Метод для продолжения заявки

       //Добавление "Заёмщика"
        basePage
                .clickButtonById("BnzVwFinApplicationAllParticipantDetailAddTypedRecordButtonButton-imageEl"); // Нажать на кнопку "+"
        contractPage
                .clickButtonByLiName("Заемщик");

        // Заполнение чекбокса в анкете заёмщика "Согласие на обработку БКИ"

        fieldPage
                .setHandBookFieldByValueCheck("Страна рождения", "Точикистон")
                .setFieldByValueCheck("Дата выдачи", "01.01.2020")
                .getNonEmptyValue("Дата выдачи");
        fieldPage
                .setFieldByValueCheck("Действителен до", "01.01.2030")
                .getNonEmptyValue("Действителен до");
        contractPage
                .DoubleclickByDIM("Шиносномаи ЧТ")
                .checkCurrentPage("RegDocumentPageV2Container"); //Проверка текущей страницы
        fieldPage
                .setFieldByValueCheck("Дата выдачи", "01.01.2020")
                .getNonEmptyValue("Дата выдачи");
        fieldPage
                .setFieldByValueCheck("Действует до", "01.01.2030")
                .getNonEmptyValue("Действует до");
        contractPage
                .clickButtonByNameCheck("Сохранить")
                .checkCurrentPage("EntityLoaded")
                .selectDropdownValueWithCheckNew("BnzAffiliation","Мобильный")
                .doubleclickButtonByName("Регистрация") //Деталь адреса - строка типа адреса "Регистрация"
                .checkCurrentPage("ContactAddressPageV2Container") //Проверка текущей страницы
                .waitForAddressPageLoaded();
        fieldPage
                .setDateFieldByMarker("BnzRegistrationDate", "01.01.2020")
                .getNonEmptyValue("Дата регистрации");
        contractPage
                .clickButtonByNameCheck("Сохранить");
        basePage
                .waitForPage();
        fieldPage
                .setHandBookFieldByValueCheck("Тип клиента", "Нав");
        contractPage
                .doubleclickButtonByName("Фактический") //Деталь адреса - строка типа адреса "Фактический"
                .checkCurrentPage("ContactAddressPageV2Container") //Проверка текущей страницы
                .waitForAddressPageLoaded();
        fieldPage
                .setDateFieldByMarker("BnzRegistrationDate", "01.01.2020")
                .getFieldValueSmart("Дата регистрации");
        contractPage
                .clickButtonByNameCheck("Сохранить");
        fieldPage
                .setHandBookFieldByValueCheck("Уровень риска","Низкий");
        contractPage
                .clickButtonByContainNameCheck("ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ");
        fieldPage
                .setHandBookFieldByValueCheck("Клиент предоставил подтверждение дохода","Да")
                .setHandBookFieldByValueCheck("Тип документа подтверждения дохода","Выписка с банковского счета");
        basePage
                .clickButtonById("BnzAppIncomesDetailAddRecordButtonButton-imageEl"); //Нажать на кнопку "+" в детали "Доходы"
        contractPage
                .setfieldScheduleDetailByDIM("TsiKind","Зарплата по основному месту работы")
                .clickButtonByLiName("Зарплата по основному месту работы")
                .setfieldScheduleDetailByDIM("TsiFrequencyType","Ежемесячно")
                .clickButtonByLiName("Ежемесячно")
                .setfieldScheduleDetailByDIM("TsiAmountBC","11000");
        basePage
                .clickButtonById("BnzAppExpensesDetailAddRecordButtonButton-imageEl"); // Нажать на кнопку "+" в детали "Расходы"
        contractPage
                .CheckBoxValue("BnzAppExpensesDetailTsiKindComboBoxEdit-el")
                .clickButtonByLiName("Коммунальные платежи")
                .CheckBoxValue("BnzAppExpensesDetailTsiFrequencyTypeComboBoxEdit-el")
                .clickButtonByLiName("Единоразово")
                .clickDivbyId("BnzAppExpensesDetailTsiAmountBCFloatEdit-wrap","600");
        fieldPage
                .setHandBookFieldByValueCheck("Семейное положение","Мучаррад (мард)")
                .setFieldByValueCheck("Количество иждивенцев (строка)","0")
                .setFieldByValueCheck("Общий стаж, мес","60")
                .setHandBookFieldByValueCheck("Тип занятости","Имеет другой источник дохода")
                .setHandBookFieldByValueCheck("Причина отсутствия работы","Получатель Д/П");
        basePage
                .scrollToTop();
        contractPage
                .clickButtonByContainNameCheck("ОЦЕНКА ИНФОРМАЦИИ");
        fieldPage
                .setHandBookFieldByValueCheck("Тип собственности на недвижимость","Более 2-ух квартир")
                .setHandBookFieldByValueCheck("Тип владения автомобилем","Есть автомобиль");
        contractPage
                .ensureCheckboxChecked("IsConsentBKIProcessing")
                .clickButtonByNameCheck("Сохранить")
                .clickButtonByNameCheck("Закрыть")
                .clickButtonByContainName("Участники заявки")
                .clickButtonByNameCheck("Заемщик")
                .openDetailMenu("Участники заявки") // кнопка "Действия" (Троеточие)
                .clickButtonByLiName("Печать Анкеты-заявки")
                .clickElementDashboardCheck("Добавьте и заполните анкеты участников заявки","Execute",
                        "//*[@data-item-marker='MiniPage']") //Нажать на кнопку "Завершить"

                .setfieldScheduleDetailByDIM("Result","Выполнена")
                .clickButtonByLiName("Выполнена");
        basePage
                .clickButtonByDataItemMakerCheck("SaveEditButton"); //Кнопка "Сохранить"
        contractPage
                .shouldSeeModalWithText("Нет задолженности!"); // Проверка текста модального окна
        basePage
                .clickButtonByDataItemMaker("ОК");
        contractPage
                .shouldSeeModalWithText("У клиента нет просроченных дней"); // Проверка текста модального окна
        basePage
                .clickButtonByDataItemMaker("ОК");

        //2.Стадия "Предварительная проверка" Процесс заполнения заявки кредита Физ.Лица
        //Тест-кейс №2

        contractPage.
                clickElementDashboardWait("Проверка клиента","Approve"); //Кнопка "Утвердить"
        basePage
                .clickButtonByDataItemMaker("SaveEditButton"); //Кнопка "Выполнить"
        contractPage
                .clickElementDashboardCheck("Заполните данные обеспечения и поручительства","Execute",
                        "//*[@data-item-marker='MiniPage']") //Нажать на кнопку "Завершить"
                .setfieldScheduleDetailByDIM("ProcessResult","Выполнена")
                .clickButtonByLiName("Выполнена");
        basePage
                .clickButtonByDataItemMaker("SaveEditButton"); //Нажать на кнопку "Сохранить"

        //4.Стадия "Сбор документов" Процесс заполнения заявки кредита Физ.Лица
        //Тест-кейс №3 - Загрузка документов заёмщика

        contractPage
                .clickButtonByContainName("Документы"); //Переход на вкладку "Документы"
        detailPage
                .openDetailByName("Финансовое досье");

        contractPage
                .startUpload()
                .uploadFile("Registration (Example).xlsx",1);
        contractPage
                .clickButtonByNameContains("Файлы", 1)
                .validateUploadFile("Registration (Example).xlsx");
        detailPage
                .openDetailByName("Досье клиента");
        contractPage
                .uploadFile("Registration (Example).xlsx",2);
        contractPage
                .clickButtonByNameContains("Файлы", 2)
                .validateUploadFile("Registration (Example).xlsx");
        contractPage
                .uploadFile("Registration (Example).xlsx",3);
        contractPage
                .clickButtonByNameContains("Файлы", 3)
                .validateUploadFile("Registration (Example).xlsx");
        contractPage
                .clickElementDashboardCheck("Вложить документы и отправить на рассмотрение","Execute",
                        "//*[@data-item-marker='MiniPage']") //Нажать на кнопку "Завершить"

                .setfieldScheduleDetailByDIM("Result","Выполнена")
                .clickButtonByLiName("Выполнена");
        basePage
                .clickButtonByDataItemMaker("SaveEditButton"); //Нажать на кнопку "Сохранить"
        contractPage
                .clickButtonByContainNameCheck("Проверки"); //Открыть вкладку "Проверки"

        //  ЯВНОЕ ОЖИДАНИЕ 10 СЕКУНДЫ ПЕРЕД ПРОВЕРКОЙ НОВОЙ СТРАНИЦЫ
        Selenide.sleep(10000);

        contractPage
                .scrollDownSmall();
        contractPage
                .waitForCreditDecision("Решение по кредиту","Одобрить");
        contractPage
                .scrollToTop();
        contractPage
                .clickElementDashboardCheck("Информирование клиента","Execute",
                        "//*[@data-item-marker='MiniPage']") //Нажать на кнопку "Завершить"
                //.clickElementDashboard("Укажите дату и время идентификации клиента","Execute") //Нажать на кнопку "Завершить" - Продолжение заявки
                .setfieldScheduleDetailByDIM("Result","Клиент согласен")
                .clickButtonByLiName("Клиент согласен");
        basePage
                .clickButtonByDataItemMaker("SaveEditButton"); //Нажать на кнопку "Сохранить"
        fieldPage
                .setHandBookFieldByValueCheck("Ответственный за подписание", "Рустамова Саодатчон Валиевна");
        basePage
                .clickButtonByNameCheck("Подтвердить");
        basePage
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
        contractPage
                .removeFilterIfExists();//Закрыть предыдущий результат поиска
        basePage
                .clickButtonByNameCheck("Фильтры/группы"); // Нажать на "Фильтры/группы"
        contractPage
                .clickButtonByLiName("Добавить условие")//Выбрать "Добавить условие"
                .setFieldScheduleDetailByDIMNewCheck("columnEdit","Номер") //Первое поле выбрать "Номер"
                .applySavedValueIntoField("searchEdit","applyButton"); //Вставить скопированный номер заявки, нажать на кнопку галочки
        contractPage
                .clickFirstRowInGridAndWaitButtonNew("grid-FinApplicationSectionDataGridGrid-wrap","Открыть"); //Выделить найденный результат
        basePage
                .clickButtonByDataItemMakerCheck("Открыть"); //Нажать на кнопку "Открыть"
        contractPage
                .clickElementDashboardCheck("Проверка решения КК","Execute",
                        "//*[@data-item-marker='MiniPage']") //Нажать на кнопку "Завершить"

                .setfieldScheduleDetailByDIM("Result","Выполнена")
                .clickButtonByLiName("Выполнена");
        basePage
                .clickButtonByDataItemMaker("SaveEditButton"); //Нажать на кнопку "Сохранить"
        contractPage
                .clickElementDashboardName("Создание договора в АБС (печать договоров для встречи)")
                .clickContractAutoWait(CONTRACT_PAGE_MARKER); //Нажать на номер договора
        basePage
                .clickButtonOnPageByName(CONTRACT_PAGE_MARKER,"Действия");  //Создание договора
        contractPage
                .clickButtonByLiName("Создание договора");
        fieldPage
                .setHandBookFieldByValueCheck("Вид планирования","Аннуитетный_005")
                .getFieldValueSmart("Вид планирования");
        contractPage
                .selectLoadCreditTypeNew("Получает сегодня");
        fieldPage
                .fillLoadCreditTypeSafely("Получает сегодня")
                .getFieldValueSmart("Тип получения кредита");
        basePage
                .clickButtonByName("Выбрать");
        contractPage
                .shouldSeeModalWithText("Нет задолженности!"); // Проверка текста модального окна
        basePage
                .clickButtonByNameCheck("ОК");
        contractPage
                .shouldSeeModalWithText("Договор успешно создан"); // Проверка текста модального окна "Создание кредитного договора"
        basePage
                .clickButtonByNameCheck("ОК");
        basePage
                .clickButtonById("BnzContractCreditPageBnzCreateSavingAcountContractButtonButton-imageEl"); // Нажать на кнопку "+"
        contractPage
                .shouldSeeModalWithText("Депозитный договор успешно создан в АБС");
        basePage
                .clickButtonByNameCheck("ОК");
        contractPage
                .saveValueDIMCheckWork("BnzDepositBankAccount")
                .clickButtonByNameCheck("Закрыть");

        refresh();

        contractPage
                .clickSearchIconID("BnzContractCreditPageBnzCreditBankAccountLookupEdit")
                .selectValueInLookupWork("searchEdit");
        basePage
                .clickButtonByNameCheck("Сохранить");
        basePage
                .clickButtonByNameCheck("Действия"); //Получение графика платежей
        contractPage
                .clickAndCheckModal("Получение графика платежей","График платежей успешно получен");
        basePage
                .clickButtonByNameCheck("ОК");
        basePage
                .clickButtonByNameCheck("Действия"); //Привязка счета к договору
        contractPage
                .clickAndCheckModal("Привязка счета к договору","Счет успешно привязан к кредитному договору");
        basePage
                .clickButtonByNameCheck("ОК");

        contractPage
                .clickElementDashboardCheck("Создание договора в АБС (печать договоров для встречи)","Execute",
                        "//*[@data-item-marker='MiniPage']") //Нажать на кнопку "Завершить"

                .setfieldScheduleDetailByDIM("Result","Выполнена")
                .clickButtonByLiName("Выполнена");
        basePage
                .clickButtonByDataItemMaker("SaveEditButton"); //Нажать на кнопку "Сохранить"
        basePage
                .clickButtonByNameCheck("Действия"); //Подписание договора
        contractPage
                .clickAndCheckModal("Подписание договора","Резервирование счетов выполнено успешно");
        basePage
                .clickButtonByNameCheck("ОК");
        basePage
                .clickButtonByNameCheck("Действия"); //Выдача кредита
        contractPage
                .issueCreditUniversal("Наличными");
        basePage
                .clickButtonByNameCheck("Подтвердить");
        contractPage
                .shouldBeModalOpened("Комиссия за выдачу кредита");
        basePage
                .clickButtonByNameCheck("Подтвердить");
        contractPage
                .shouldSeeModalWithText("Действие успешно выполнено. Необходимо оплатить комиссию за кредит");
        basePage
                .clickButtonByNameCheck("ОК");
        contractPage
                .shouldSeeModalWithText("Кассовый ордер успешно сформирован");
        basePage
                .clickButtonByNameCheck("ОК");
        contractPage
                .shouldSeeModalWithText("График платежей успешно получен");
        basePage
                .clickButtonByNameCheck("ОК");

        // Продолжение заявки
        /*contractCredit
                .safeClick($("#FinApplicationPageTabsTabPanel-scroll-right")); //Скролл вправо (Вкладки)
        contractCredit
                .clickButtonByContainNameCheck("История")
                .clickLookupLinkByLabel("Договор",CONTRACT_PAGE_MARKER);*/  //Нажать на кнопку договора через вкладку "История" // Продолжение заявки
        // Продолжение заявки

        contractPage
                .saveValueByMarker("Number")//Копирование номера "Кредитного договора"
                .clickButtonByContainNameCheck("Операции по договору") //Перейти на вкладку "Операции по договору"
                .doubleclickButtonByName("Приходный") //Проверка наличия ордеров
                .checkCurrentPage("BnzOrderPageContainer"); //Проверка текущей страницы
        creditAssert
                .assertOrderState("Новый");  //Проверить состояние ордеров
        contractPage
                .checkFieldValueNormalized("Сумма документа","200,00");//Проверить поле суммы
        basePage
                .clickButtonByNameCheck("Закрыть");//Нажать кнопку закрыть
        contractPage
                .doubleclickButtonByName("Расходный")  //Проверка наличия ордеров
                .checkCurrentPage("BnzOrderPageContainer");//Проверка текущей страницы
        creditAssert
                .assertOrderState("Новый");  //Проверить состояние ордеров
        contractPage
                .checkFieldValueNormalized("Сумма документа","50 000,00");//Проверить поле суммы
        basePage
                .clickButtonByNameCheck("Закрыть");//Нажать кнопку закрыть
        contractPage
                .selectPrintOptionUniversal("Чек лист маълумотнома(оферта)");//нажать на кнопку "ПЕЧАТЬ" Скачать ПФ "Чек лист маълумотнома (Оферта)"

        basePage
                .logout(); //Выйти из системы

        openUrl
                .openUrl(BASE_ULR_1);//.openUrl("");
        login
                .enterUsername(retailManager.getLogin())
                .enterPassword(retailManager.getPassword())
                .clickLogin()
                .verifyLogin();

        basePage
                .clickByDataMarker("Договоры"); //Перейти в раздел "Договоры"
        contractPage
                .removeFilterIfExists();//Закрыть предыдущий результат поиска
        basePage
                .clickButtonByNameCheck("Фильтры/группы"); // Нажать на "Фильтры/группы"
        contractPage
                .clickButtonByLiName("Добавить условие")//Выбрать "Добавить условие"
                .setFieldScheduleDetailByDIMNewCheck("columnEdit","Номер") //Первое поле выбрать "Номер"
                .applySavedValueIntoField("searchEdit","applyButton"); //Вставить скопированный номер договора, нажать на кнопку галочки
        contractPage
                .clickFirstRowInGridAndWaitButtonNew("grid-ContractSectionV2DataGridGrid-wrap","Открыть"); //Выделить найденный результат
        basePage
                .clickButtonByDataItemMakerCheck("Открыть"); //Нажать на кнопку "Открыть"
        contractPage
                .clickButtonByContainNameCheck("Операции по договору"); //Перейти на вкладку "Операции по договору"
        contractPage
                .doubleclickButtonByName("Приходный") //Открыть ордер у которого тип ордера "Приходный"
                .checkCurrentPage("BnzOrderPageContainer"); //Проверка текущей страницы
        creditAssert
                .assertOrderState("Новый");  //Проверить состояние ордеров
        contractPage
                .checkFieldValueNormalized("Сумма документа","200,00");//Проверить поле суммы
        basePage
                .clickButtonByNameCheck("Подтвердить");//Нажать на кнопку "Подтвердить"
        contractPage
                .shouldSeeModalWithText("Кассовый ордер успешно подтвержден");//Проверить текст модального окна
        basePage
                .clickButtonByNameCheck("ОК");//Нажать кнопку "ОК"
        creditAssert
                .assertOrderState("Подтвержден");  //Проверить состояние ордеров
        contractPage
                .selectPrintOptionUniversal("Приходный кассовый ордер по комиссии");//нажать на кнопку "ПЕЧАТЬ" //Скачать ПФ "Взымание комиссии"
        basePage
                .clickButtonByNameCheck("Закрыть");//Нажать кнопку закрыть
        contractPage
                .doubleclickButtonByName("Расходный") //Открыть ордер у которого тип ордера "Расходный"
                .checkCurrentPage("BnzOrderPageContainer"); //Проверка текущей страницы
        creditAssert
                .assertOrderState("Новый");  //Проверить состояние ордеров
        contractPage
                .checkFieldValueNormalized("Сумма документа","50 000,00");//Проверить поле суммы
        basePage
                .clickButtonByNameCheck("Подтвердить");//Нажать на кнопку "Подтвердить"
        contractPage
                .shouldSeeModalWithText("Кассовый ордер успешно подтвержден");//Проверить текст модального окна
        basePage
                .clickButtonByNameCheck("ОК");//Нажать кнопку "ОК"
        creditAssert
                .assertOrderState("Подтвержден");  //Проверить состояние ордеров
        contractPage
                .selectPrintOptionUniversal("Расходный кассовый ордер");//нажать на кнопку "ПЕЧАТЬ" Скачать ПФ "Выдача кредита"
        basePage
                .clickButtonByNameCheck("Закрыть");//Нажать кнопку закрыть

        basePage
                .logout(); //Выйти из системы

        openUrl
                .openUrl(BASE_ULR_1);//.openUrl("");
        login
                .enterUsername(retailManager.getLogin())
                .enterPassword(retailManager.getPassword())
                .clickLogin()
                .verifyLogin();

        basePage
                .clickByDataMarker("Договоры"); //Перейти в раздел "Договоры"
        contractPage
                .removeFilterIfExists();//Закрыть предыдущий результат поиска
        basePage
                .clickButtonByNameCheck("Фильтры/группы"); // Нажать на "Фильтры/группы"
        contractPage
                .clickButtonByLiName("Добавить условие")//Выбрать "Добавить условие"
                .setFieldScheduleDetailByDIMNewCheck("columnEdit","Номер") //Первое поле выбрать "Номер"
                .applySavedValueIntoField("searchEdit","applyButton"); //Вставить скопированный номер договора, нажать на кнопку галочки
        contractPage
                .clickFirstRowInGridAndWaitButtonNew("grid-ContractSectionV2DataGridGrid-wrap","Открыть"); //Выделить найденный результат
        basePage
                .clickButtonByDataItemMakerCheck("Открыть"); //Нажать на кнопку "Открыть"
        contractPage
                .clickButtonByContainNameCheck("Операции по договору"); //Перейти на вкладку "Операции по договору"
        contractPage
                .doubleclickButtonByName("Приходный") //Проверка наличия ордеров
                .checkCurrentPage("BnzOrderPageContainer"); //Проверка текущей страницы
        creditAssert
                .assertOrderState("Подтвержден");  //Проверить состояние ордеров
        basePage
                .clickButtonByNameCheck("Закрыть");//Нажать кнопку закрыть
        contractPage
                .doubleclickButtonByName("Расходный")  //Проверка наличия ордеров
                .checkCurrentPage("BnzOrderPageContainer"); //Проверка текущей страницы
        creditAssert
                .assertOrderState("Подтвержден");  //Проверить состояние ордеров
        basePage
                .clickButtonByNameCheck("Закрыть");//Нажать кнопку закрыть
        basePage
                .clickButtonById("view-button-OBSW-imageEl"); //Открыть "Консультационную панель"
        basePage
                .completeConsultation();  //Завершить выдачу кредита

        basePage
                .logout(); //Выйти из системы

    }

}




