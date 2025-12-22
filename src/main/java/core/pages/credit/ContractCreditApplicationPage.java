package core.pages.credit;


import com.codeborne.selenide.*;
import core.base.BasePage;
import core.base.common.components.*;

import core.base.common.utils.FieldUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;


/**
 * ContractCreditApplicationPage — PageObject высокого уровня.
 * В нём локально подключены только нужные компоненты (не в BasePage).
 *
 *
 */


public class ContractCreditApplicationPage extends BasePage {



    // Локальные компоненты страницы
    private final ButtonsComponent buttons = new ButtonsComponent();
    private final FieldComponent fields = new FieldComponent();
    private final LookupComponent lookup = new LookupComponent();
    private final GridComponent grid = new GridComponent();
    private final MiniPageComponent miniPage = new MiniPageComponent();
    private final DashboardComponent dashboard = new DashboardComponent();
    private final DashboardActionsComponent dashboardActions = new DashboardActionsComponent();
    private final FiltersComponent filters = new FiltersComponent();
    private final FileUploadComponent files = new FileUploadComponent();
    private final MessageBoxComponent messages = new MessageBoxComponent();
    private final CheckboxComponent checkbox = new CheckboxComponent();
    private static final Logger log =
            LoggerFactory.getLogger(ContractCreditApplicationPage.class);
    private String savedValue;


    @Step("Выбрать продукт '{product}'")
    public ContractCreditApplicationPage selectProduct(String product) {
        lookup.selectValue("Product", product);
        return this;
    }

    @Step("Заполнить сумму кредита '{amount}'")
    public ContractCreditApplicationPage fillLoanAmount(String amount) {
        fields.setValue("LoanAmount", amount);
        return this;
    }

    // ContractCreditApplicationPage
    public void startConsultation() {
        clickElementByTagAndDIM("span", "Начать консультацию");
    }

    public ContractCreditApplicationPage clickButtonByName(String name) {
        $x("//span[normalize-space()='" + name + "']")
                .shouldBe(visible, enabled)
                .scrollIntoView(true)
                .click();
        return this;
    }




    @Step("Получить и открыть график платежей")
    public ContractCreditApplicationPage openPaymentSchedule() {
        buttons.clickByName("Получить график платежей");
        grid.doubleClickFirstRow("PaymentScheduleDetail");
        return this;
    }

    @Step("Выдать кредит наличными")
    public ContractCreditApplicationPage issueCreditCash() {
        dashboardActions.issueCredit("Наличными");
        return this;
    }


    @Step("Утвердить решение по заявке")
    public ContractCreditApplicationPage approveDecision() {
        dashboardActions.approve();
        return this;
    }

    @Step("Сохранить значение поля по marker '{marker}'")
    public ContractCreditApplicationPage saveValue(String marker) {
        this.savedValue = fields.getValue(marker);
        return this;
    }

    @Step("Вставить сохранённое значение в поле '{marker}'")
    public ContractCreditApplicationPage pasteSavedValue(String marker) {
        fields.setValue(marker, savedValue);
        return this;
    }

    public String getSavedValue() {
        return this.savedValue;
    }
    public void clickButton(String name) {
        new ButtonsComponent().clickByName(name);
    }
    public void clickAddBorrower() {
        new ButtonsComponent().clickByDataItemMarker("AddBorrowerButton");
    }
    public String getApplicationNumber() {
        return $x("//*[@data-item-marker='Number']").getValue();
    }



    public ContractCreditApplicationPage clickButtonByNameCheck(String nameButton) {

        SelenideElement button = $x("//span[.='" + nameButton + "']")
                .shouldBe(visible)
                .shouldBe(enabled);

        button.click();

        return this;
    }


    @Step("Клик по первой строке грида '{gridWrapId}' и ожидание кнопки '{buttonText}'")
    public ContractCreditApplicationPage clickFirstRowInGridAndWaitButton(
            String gridWrapId,
            String buttonText) {

        for (int attempt = 1; attempt <= 5; attempt++) {
            try {

                SelenideElement gridWrap = $x("//div[@id='" + gridWrapId + "']")
                        .shouldBe(visible)
                        .scrollIntoView(true);

                SelenideElement row = gridWrap.$x(
                                ".//div[contains(@class,'grid-row') and contains(@class,'grid-active-selectable')]"
                        )
                        .shouldBe(visible)
                        .shouldBe(enabled);

                row.click();

                $x("//span[normalize-space()='" + buttonText + "']")
                        .shouldBe(visible, Duration.ofSeconds(5))
                        .shouldBe(enabled);

                return this;

            } catch (Exception e) {
                if (attempt == 5) {
                    throw new AssertionError(
                            "После клика по строке в гриде '" + gridWrapId +
                                    "' кнопка '" + buttonText + "' так и не появилась", e
                    );
                }
            }
        }
        return this;
    }


    public ContractCreditApplicationPage setfieldScheduleDetailByDIM(String name, String value) {
        $x("//div[@data-item-marker='" + name + "']/input").setValue(value);
        return this;
    }

    public ContractCreditApplicationPage setHandBookFieldByValue(String nameField, String value) {
        setfieldScheduleDetailByDIM(nameField, value);
        $x("//div[contains(@class,'listview')]//li[.='" + value + "']").click();
        return this;
    }

    public ContractCreditApplicationPage shouldSeeModalWithText(String expectedText) {
        $x("//div[contains(@class,'modal') or contains(@class,'dialog') or contains(@class,'message')]"
                + "[contains(., '" + expectedText + "')]")
                .shouldBe(visible, Duration.ofSeconds(50));

        return this;
    }

    public ContractCreditApplicationPage clickButtonByContainName(String NameNew) {
        $x("//span[contains(text(), '" + NameNew + "')]").click();
        return this;
    }

    public ContractCreditApplicationPage doubleclickButtonByName(String nameButton){
        $x("//span[.='" + nameButton + "']").doubleClick();

        return this;
    }


    public ContractCreditApplicationPage clickButtonByLiName(String value) {
        $x("//li[contains(text(), '" + value + "')]").click();
        return this;
    }


    public ContractCreditApplicationPage DoubleclickByDIM(String value) {

        SelenideElement element =
                $x("//div[@data-item-marker='" + value + "' and contains(@class, 'grid-listed-row')]");

        Actions actions = new Actions(getWebDriver());
        actions.doubleClick(element).perform();

        return this;
    }


    @Step("Проверить, что текущая страница имеет маркер '{expectedPageMarker}'")
    public ContractCreditApplicationPage checkCurrentPage(String expectedPageMarker) {

        for (int attempt = 1; attempt <= 5; attempt++) {
            try {
                $x("//*[@data-item-marker='" + expectedPageMarker + "']")
                        .should(appear);
                return this;

            } catch (Throwable e) {
                if (attempt == 5) {
                    throw e;
                }
            }
        }
        return this;
    }


    @Step("Выбрать значение '{value}' в выпадающем поле '{marker}'")
    public ContractCreditApplicationPage selectDropdownValueWithCheckNew(String marker, String value) {

        SelenideElement input = $x("//*[@data-item-marker='" + marker + "']//input");

        for (int attempt = 1; attempt <= 5; attempt++) {
            try {
                log.warn(
                        "Попытка №%s: выбор значения '%s' в поле '%s'",
                        attempt, value, marker
                );

                // 1️⃣ Кликаем по полю
                input.scrollIntoView(true)
                        .shouldBe(visible, enabled)
                        .click();

                // 2️⃣ ЖДЁМ появления списка
                SelenideElement dropdownContainer =
                        $x("//div[contains(@class,'listview-scroll')]")
                                .shouldBe(visible);

                // 3️⃣ ИЩЕМ ПУНКТ ПО ТЕКСТУ ИЛИ MARKER
                SelenideElement option = dropdownContainer
                        .$x(".//li[normalize-space(.)='" + value + "' or @data-item-marker='" + value + "']")
                        .shouldBe(visible);

                // 4️⃣ КЛИК ПО ЗНАЧЕНИЮ
                option.scrollIntoView(true).click();

                // 5️⃣ ПРОВЕРКА, ЧТО ЗНАЧЕНИЕ УСТАНОВИЛОСЬ
                input.shouldHave(Condition.value(value));

                log.info(String.format(
                        "Значение '%s' успешно выбрано в поле '%s'",
                        value, marker
                ));

                return this;

            } catch (Exception e) {

                log.warn("Ошибка на попытке " + attempt + ": " + e.getMessage());

                if (attempt == 5) {
                    throw new AssertionError(
                            "Не удалось выбрать значение '" + value +
                                    "' в поле '" + marker + "' за 5 попыток", e);
                }
            }
        }

        return this;
    }


    @Step("Ожидание загрузки страницы адреса")
    public void waitForAddressPageLoaded() {
        System.out.println("⏳ Ожидание загрузки страницы AddressPageV2...");
        $x("//*[@data-item-marker='ContactAddressPageV2Container']")
                .shouldBe(Condition.visible, Duration.ofSeconds(10));
        System.out.println("✔ Страница AddressPageV2 загружена");
    }


    public ContractCreditApplicationPage clickButtonByContainNameCheck(String Value) {
        SelenideElement element = $x("//span[contains(text(), '" + Value + "')]")
                .shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .shouldHave(Condition.text(Value));

        element.hover();                    // помогает "активировать" элемент
        element.shouldBe(Condition.interactable); // теперь interactable безопасен

        element.click();

        return this;
    }


    public ContractCreditApplicationPage CheckBoxValue(String value) {
        $x("//input[@id='" + value + "']").click();
        return this;

    }

    public ContractCreditApplicationPage clickDivbyId(String nameField ,String value) {
        $x("//div[@id='" + nameField + "']/input").setValue(value).click();
        return this;
    }


    @Step("Поставить чекбокс '{marker}', если он не установлен")
    public ContractCreditApplicationPage ensureCheckboxChecked(String marker) {

        SelenideElement checkboxWrap = $x(
                "//*[@data-item-marker='" + marker + "'][contains(@class,'t-checkboxedit-wrap')]"
        ).shouldBe(visible);

        boolean isChecked = checkboxWrap.has(cssClass("t-checkboxedit-checked"));

        // ✅ Если уже установлен — просто выходим
        if (isChecked) {
            log.info("Чекбокс '{}' уже установлен. Пропускаем клик.", marker);
            return this;
        }

        // ✅ Ставим галочку
        checkboxWrap.scrollIntoView(true).click();

        // ✅ Жёсткая проверка после клика
        checkboxWrap.shouldHave(cssClass("t-checkboxedit-checked"));

        log.info("Чекбокс '{}' успешно установлен.", marker);

        return this;
    }


    public ContractCreditApplicationPage openDetailMenu(String detailName) {
        $x("//span[.='" + detailName + "']/../..//span[@data-item-marker='ToolsButton']//span[contains(@class,\"menuWrap\")]").click();
        return this;
    }


    @Step("Кликнуть на дашборд '{nameDashboard}' с DIM '{DIMvalue}' и дождаться мини-пейджа")
    public ContractCreditApplicationPage clickElementDashboardCheck(
            String nameDashboard,
            String DIMvalue,
            String miniPageXpath   // ✅ СЮДА ты будешь передавать нужный XPath
    ) {

        int maxAttempts = 4;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {

            // ✅ ТВОЙ ИСХОДНЫЙ КЛИК — БЕЗ ИЗМЕНЕНИЙ
            $x("//div[.='" + nameDashboard + "']/..//span[@data-item-marker='" + DIMvalue + "']")
                    .scrollIntoView(false)
                    .hover()
                    .click();

            try {
                // ✅ ТЕПЕРЬ XPath мини-пейджа УНИВЕРСАЛЬНЫЙ
                $x(miniPageXpath)
                        .shouldBe(visible, Duration.ofSeconds(5));

                return this;

            } catch (AssertionError e) {
                if (attempt == maxAttempts) {
                    throw e;
                }
            }
        }

        return this;
    }

    public ContractCreditApplicationPage clickElementDashboardWait(String nameDashboard, String DIMvalue) {

        $x("//div[.='" + nameDashboard + "']").shouldBe(visible, Duration.ofSeconds(60));

        $x("//div[.='" + nameDashboard + "']/..//span[@data-item-marker='" + DIMvalue + "']")
                .scrollIntoView(false)
                .hover()
                .click();

        return this;
    }

    public ContractCreditApplicationPage startUpload(){
        executeJavaScript(
                "const originalOpen = XMLHttpRequest.prototype.open;" +
                        "XMLHttpRequest.prototype.open = function(method, url) {" +
                        "  if (url.includes('FileApiService')) {" +
                        "    console.log('Подмена URL на TsiFileApiService');" +
                        "    url = url.replace(/(?:Tsi)*FileApiService/, 'TsiFileApiService');" +
                        "  }" +
                        "  return originalOpen.apply(this, arguments);" +
                        "};"
        );
        return this;
    }

    /** Загрузка файла по названию и индексу поля */
    public ContractCreditApplicationPage uploadFile(String nameFile, int index) {
        $x("//input[@data-item-marker='AddRecordButton'][" + index + "]").uploadFile(
                new File("src/main/resources/resourcesFiles/" + nameFile));

        return this;
    }

    /** Клик кнопки по частичному совпадение имени и индекс */
    public ContractCreditApplicationPage clickButtonByNameContains(String nameButton, int index){
        $x("(//span[contains(.,'" + nameButton + " (')])[" + index + "]").click(); //span[contains(.,'Файлы (')][1]
        return this;
    }


    public ContractCreditApplicationPage validateUploadFile(String nameFile) {
        $x("//div[@data-item-marker='" + nameFile + "']")
                .shouldBe(visible);
        return this;
    }

    @Step("Ожидаем появление значения '{value}' в колонке '{columnName}'")
    public void waitForCreditDecision(String columnName, String value) {

        System.out.println("➡ Ждём, когда в колонке '" + columnName + "' появится значение '" + value + "'");

        long timeoutMs = Duration.ofSeconds(60).toMillis();
        long start = System.currentTimeMillis();

        // Xpath колонки: ищем div где label имеет title=columnName
        String headerXpath = "//label[@title='" + columnName + "']";

        // Проверяем, что колонка вообще существует (один раз)
        $x(headerXpath).shouldBe(Condition.visible, Duration.ofSeconds(10));

        // Ищем строки грида
        String rowXpath = "//div[contains(@class,'grid-listed-row') or contains(@class,'grid-row')]";

        while (System.currentTimeMillis() - start < timeoutMs) {

            ElementsCollection rows = $$x(rowXpath);

            for (SelenideElement row : rows) {
                try {
                    // Ищем значение внутри строки
                    SelenideElement cell = row.$x(".//span[@grid-data-type='text' and normalize-space()='" + value + "']");
                    if (cell.exists() && cell.isDisplayed()) {
                        System.out.println("✔ Значение найдено в строке: " + value);
                        return;
                    }

                } catch (Exception ignored) {}
            }

            System.out.println("⏳ Значение '" + value + "' пока не найдено — ждём...");
            Selenide.sleep(500);
        }

        throw new AssertionError("❌ Значение '" + value + "' в колонке '" + columnName + "' так и не появилось!");
    }

    //Скролл вправо (Вкладки) 07.12.2025 //Работает

    @Step("Нажать на элемент")
    public void safeClick(SelenideElement element) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                element.shouldBe(Condition.visible, Condition.enabled)
                        .scrollIntoView(true)
                        .click();
                return;
            } catch (Exception e) {
                attempts++;
                if (attempts == 3) {
                    throw e;
                }
                Selenide.sleep(500);
            }
        }
    }



    @Step("Ожидаем появление значения '{value}' в колонке '{columnName}'")
    public void waitForValueInGridColumnNew(String columnName, String value) {
        System.out.println("➡ Ждём значение '" + value + "' в колонке '" + columnName + "'");

        // Ждём, что хотя бы один блок заголовков появится (защита от пустой страницы)
        $$x("//div[contains(@class,'grid-captions')]//label")
                .shouldBe(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(10));

        // Повторяем попытки поиска в пределах таймаута (будет учитывать перерисовку грида)
        long timeoutMs = Duration.ofSeconds(60).toMillis();
        long start = System.currentTimeMillis();

        while (System.currentTimeMillis() - start < timeoutMs) {
            try {
                // 1) Получаем заголовки заново (каждый цикл — свежие элементы)
                ElementsCollection headers = $$x("//div[contains(@class,'grid-captions')]//label");

                int columnIndex = -1;
                for (int i = 0; i < headers.size(); i++) {
                    String h = headers.get(i).getText().trim();
                    System.out.println("→ Заголовок найден: '" + h + "' (index " + i + ")");
                    if (h.equalsIgnoreCase(columnName)) {
                        columnIndex = i + 1; // XPath индекс 1-based
                        break;
                    }
                }

                if (columnIndex == -1) {
                    System.out.println("⚠ Колонка '" + columnName + "' не найдена пока — повторяем...");
                    Thread.sleep(500);
                    continue;
                }

                System.out.println("✔ Колонка найдена. Индекс = " + columnIndex);

                // 2) Берём строки заново
                ElementsCollection rows = $$x("//div[contains(@class,'grid-listed-row') or contains(@class,'grid-row')]");

                if (rows.isEmpty()) {
                    System.out.println("⚠ Пока нет строк — ждём...");
                    Thread.sleep(500);
                    continue;
                }

                // 3) Проверяем каждую строку: скроллим её и читаем ячейку по columnIndex
                for (SelenideElement row : rows) {
                    try {
                        row.scrollIntoView(false);
                        // универсальный селектор ячейки внутри строки; адаптируй class 'grid-cols-4' при необходимости
                        SelenideElement cell = row.$x(".//div[contains(@class,'grid-cols-')][" + columnIndex + "]");
                        if (!cell.exists()) {
                            // пробуем альтернативный путь: поиск по span внутри div
                            cell = row.$x(".//div[" + columnIndex + "]//span");
                        }
                        if (cell.exists() && cell.isDisplayed()) {
                            String cellText = cell.getText().trim();
                            System.out.println("→ Проверяем ячейку: '" + cellText + "'");
                            if (cellText.equalsIgnoreCase(value)) {
                                System.out.println("✔ Значение найдено: " + value);
                                return;
                            }
                        }
                    } catch (org.openqa.selenium.StaleElementReferenceException ser) {
                        System.out.println("♻ Ссылка протухла (Stale) — пропускаем и повторим цикл");
                        // continue to next row
                    }
                }

                System.out.println("⚠ Значение '" + value + "' пока не найдено — повторяем...");
                Thread.sleep(500);

            } catch (Throwable t) {
                System.out.println("❗ Ошибка внутри цикла: " + t.getClass().getSimpleName() + " - " + t.getMessage());
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            }
        }

        throw new AssertionError("Значение '" + value + "' в колонке '" + columnName + "' не появилось за отведённое время");
    }



    @Step("Сохранить значение поля по marker '{marker}'")
    public ContractCreditApplicationPage saveValueByMarker(String number) {
        this.savedValue = FieldUtils.getValueByMarker(number);
        System.out.println("✔ Saved [" + number + "] = " + this.savedValue);
        return this;
    }

    //Закрыть фильтр по тегу  06.12.2025
    @Step("Удалить фильтр если он есть")
    public boolean removeFilterIfExists() {

        // 1) Контейнер быстрых фильтров
        SelenideElement filterContainer =
                $x("//*[contains(@id,'QuickFilterModuleV2') or contains(@class,'folder-filter-container')]")
                        .shouldBe(Condition.visible);

        // 2) Ищем X ТОЛЬКО внутри контейнера!
        ElementsCollection xButtons =
                filterContainer.$$x(".//*[contains(@class,'filter-remove-button')]");

        if (xButtons.isEmpty()) {
            System.out.println("ℹ Фильтр отсутствует");
            return false;
        }

        SelenideElement xBtn = xButtons.first().shouldBe(Condition.visible);

        // 3) Клик по X (обычный → JS)
        try {
            xBtn.click();
        } catch (Exception e) {
            Selenide.executeJavaScript("arguments[0].click();", xBtn);
        }

        // 4) Tooltip "Удалить" если появится
        try {
            SelenideElement deleteBtn = $x("//*[@data-item-marker='Удалить']");
        } catch (Exception ignored) { }

        // 5) Проверяем исчезновение X
        xBtn.should(Condition.disappear);

        System.out.println("✔ Фильтр удалён");
        return true;
    }


    //Работает 06.12.2025
    @Step("Ввести и выбрать значение '{value}' в поле по DIM '{name}'")
    public ContractCreditApplicationPage setFieldScheduleDetailByDIMCheck(String name, String value) {

        // 1️⃣ Находим input
        SelenideElement input = $x("//div[@data-item-marker='" + name + "']/input")
                .shouldBe(visible, enabled)
                .scrollIntoView(true);

        // 2️⃣ Вводим текст
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.BACK_SPACE);
        input.sendKeys(value);

        // 3️⃣ Ждём появления видимого listview
        SelenideElement listView = $$x("//div[contains(@class,'listview')]")
                .findBy(visible)
                .shouldBe(visible);

        // 4️⃣ Ищем LI по data-item-marker или тексту
        SelenideElement item = listView.$x(".//li[@data-item-marker='" + value + "' or normalize-space(.)='" + value + "']")
                .shouldBe(visible, enabled);

        // 5️⃣ Делаем стабильный JS-клик (для Creatio критично)
        Selenide.executeJavaScript("arguments[0].click();", item);

        // 6️⃣ Ждём, что список закроется = выбор зафиксирован
        listView.should(disappear);

        // 7️⃣ Проверяем, что поле теперь имеет выбранное значение
        input.shouldHave(Condition.exactValue(value));

        return this;
    }


    //Работает 06.12.2025
    @Step("Вставить сохранённое значение в поле фильтра '{fieldMarker}' и нажать галочку")
    public ContractCreditApplicationPage applySavedValueIntoField(String fieldMarker, String value) {

        if (this.savedValue == null) {
            throw new IllegalStateException("❌ Нет сохранённого значения! Сначала вызови saveValueByMarker().");
        }

        // 1️⃣ Ищем input внутри контейнера фильтра
        SelenideElement input = $x("//*[@data-item-marker='" + fieldMarker + "']//input")
                .shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .scrollIntoView(true);

        // 2️⃣ Кликаем (иногда overlay мешает — пробуем оба варианта)
        try {
            input.click();
        } catch (Exception e) {
            // fallback на JS click
            Selenide.executeJavaScript("arguments[0].click();", input);
        }

        // 3️⃣ Очищаем и вводим значение
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.BACK_SPACE);
        input.setValue(this.savedValue);

        // 4️⃣ Проверяем ввод
        input.shouldHave(Condition.value(this.savedValue));

        // 5️⃣ Жмём галочку applyButton
        SelenideElement applyButton = $x("//*[@data-item-marker='" + value + "']")
                .shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);

        applyButton.click();

        return this;
    }

    //Новый метод 06.12.2025

    @Step("Клик по первой строке грида '{gridWrapId}' и ожидание кнопки '{buttonText}'")
    public ContractCreditApplicationPage clickFirstRowInGridAndWaitButtonNew(String gridWrapId, String buttonText) {

        for (int attempt = 1; attempt <= 5; attempt++) {
            try {
                log.info("Попытка №" + attempt +
                        ": клик по первой строке грида '" + gridWrapId +
                        "' и ожидание кнопки '" + buttonText + "'");

                // 1. Находим контейнер грида
                SelenideElement gridWrap = $x("//div[@id='" + gridWrapId + "']")
                        .shouldBe(visible)
                        .scrollIntoView(true);

                // 2. Ищем ПЕРВУЮ строку, которая не является header
                SelenideElement row = gridWrap.$$x(".//div[contains(@class,'grid-row') and not(contains(@class,'grid-header'))]")
                        .filter(visible)
                        .first()
                        .shouldBe(visible, enabled);

                // 3. Выполняем клик через JS (самый стабильный в Creatio)
                executeJavaScript("arguments[0].click();", row);

                // 4. Ждём кнопку
                $x("//span[normalize-space()='" + buttonText + "']")
                        .shouldBe(visible, Duration.ofSeconds(5))
                        .shouldBe(enabled);

                log.info("Кнопка '" + buttonText + "' появилась");
                return this;

            } catch (Exception e) {
                log.info("Попытка №" + attempt + " не удалась, повторяем...");

                if (attempt == 5) {
                    throw new AssertionError(
                            "После клика по строке грида '" + gridWrapId +
                                    "' кнопка '" + buttonText + "' не появилась", e
                    );
                }
            }
        }
        return this;
    }


    @Step("Открыть проект решения по названию: {projectName}")
    public void openProjectByName(String projectName) {
        log.info("➡ Поиск проекта решения с названием: '{}'", projectName);

        String xpath = "//span[contains(text(),'" + projectName + "')]";

        try {
            SelenideElement element = $x(xpath)
                    .shouldBe(Condition.visible, Duration.ofSeconds(10))
                    .scrollIntoView(true);

            log.info("➡ Элемент найден. Выполняю клик по проекту '{}'", projectName);
            element.click();

            Allure.step("Клик по проекту решения: " + projectName);

        } catch (Throwable t) {
            log.error("❌ Ошибка при клике по проекту '{}'. Причина: {}",
                    projectName, t.getMessage());

            attachScreenshot();
            attachPageSource();
            attachErrorMessage(t);

            throw t;
        }


    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] attachScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver())
                .getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "PageSource", type = "text/html")
    public byte[] attachPageSource() {
        return WebDriverRunner.getWebDriver().getPageSource().getBytes();
    }

    @Attachment(value = "Error message", type = "text/plain")
    public String attachErrorMessage(Throwable t) {
        return t.getMessage();
    }



    @Step("Ожидаем появление кнопки по маркеру '{marker}' и нажимаем на неё")
    public void waitAndClickByDIM(String value) {

        String xpath = "//*[@data-item-marker='" + value + "']";

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🔎 Старт ожидания кнопки");
        System.out.println("➡ Маркер кнопки: " + value);
        System.out.println("➡ XPath: " + xpath);
        System.out.println("➡ Максимум попыток: 30 (интервал 5 сек)");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        int retries = 30;
        int pauseMs = 5000;

        for (int i = 1; i <= retries; i++) {

            System.out.println("🔁 Попытка " + i + " из " + retries);

            try {
                SelenideElement button = $x(xpath);

                if (button.exists()) {
                    System.out.println("   ✔ Элемент существует в DOM");

                    if (button.isDisplayed()) {
                        System.out.println("   ✔ Элемент видимый → пытаемся нажать...");

                        button
                                .shouldBe(Condition.visible, Duration.ofSeconds(5))
                                .shouldBe(Condition.enabled, Duration.ofSeconds(5))
                                .click();

                        System.out.println("🎉 УСПЕХ! Кнопка нажата → data-item-marker='" + value + "'");
                        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                        return;
                    } else {
                        System.out.println("   ⚠ Элемент найден, но пока НЕ видим → ждём...");
                    }
                } else {
                    System.out.println("   ⏳ Кнопка пока не найдена в DOM");
                }

            } catch (Exception e) {
                System.out.println("   ⚠ Ошибка при обращении к элементу: " + e.getMessage());
                System.out.println("   ↺ Повторяем попытку...");
            }

            // ⬇⬇⬇ ДОБАВЛЕН refresh — единственное изменение! ⬇⬇⬇
            System.out.println("🔄 Обновляем страницу (refresh), чтобы подтянуть актуальные данные...");
            Selenide.refresh();

            Selenide.sleep(pauseMs);
        }

        System.out.println("❌ ОШИБКА: Кнопка так и не появилась!");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        throw new AssertionError("Кнопка с data-item-marker='" + value + "' не появилась за заданное время!");
    }


    @Step("Ожидаем появление кнопки по маркеру '{marker}' и нажимаем на неё")
    public void waitAndClickByMarkerNew(String DIM) {

        String xpath = "//*[@data-item-marker='" + DIM + "']";

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🔎 Старт ожидания кнопки");
        System.out.println("➡ Маркер кнопки: " + DIM);
        System.out.println("➡ XPath: " + xpath);
        System.out.println("➡ Максимум попыток: 20 (интервал 3 сек)");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        int retries = 20;
        int pauseMs = 3000;

        for (int i = 1; i <= retries; i++) {

            System.out.println("🔁 Попытка " + i + " из " + retries);

            try {
                SelenideElement button = $x(xpath);

                if (button.exists()) {
                    System.out.println("   ✔ Элемент существует в DOM");

                    if (button.isDisplayed()) {
                        System.out.println("   ✔ Элемент видимый → пытаемся нажать...");

                        button
                                .shouldBe(Condition.visible, Duration.ofSeconds(5))
                                .shouldBe(Condition.enabled, Duration.ofSeconds(5))
                                .click();

                        System.out.println("🎉 УСПЕХ! Кнопка нажата → data-item-marker='" + DIM + "'");
                        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                        return;
                    } else {
                        System.out.println("   ⚠ Элемент найден, но пока НЕ видим → ждём...");
                    }
                } else {
                    System.out.println("   ⏳ Кнопка пока не найдена в DOM");
                }

            } catch (Exception e) {
                System.out.println("   ⚠ Ошибка при обращении к элементу: " + e.getMessage());
                System.out.println("   ↺ Повторяем попытку...");
            }

            Selenide.sleep(pauseMs);
        }

        System.out.println("❌ ОШИБКА: Кнопка так и не появилась!");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        throw new AssertionError("Кнопка с data-item-marker='" + DIM + "' не появилась за заданное время!");
    }

    //Работает 06.12.2025
    @Step("Ввести и выбрать значение '{value}' в поле по DIM '{name}'")
    public ContractCreditApplicationPage setFieldScheduleDetailByDIMNewCheck(String name, String value) {

        // 1️⃣ Находим input
        SelenideElement input = $x("//div[@data-item-marker='" + name + "']/input")
                .shouldBe(visible, enabled)
                .scrollIntoView(true);

        // 2️⃣ Вводим текст
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.BACK_SPACE);
        input.sendKeys(value);

        // 3️⃣ Ждём появления видимого listview
        SelenideElement listView = $$x("//div[contains(@class,'listview')]")
                .findBy(visible)
                .shouldBe(visible);

        // 4️⃣ Ищем LI по data-item-marker или тексту
        SelenideElement item = listView.$x(".//li[@data-item-marker='" + value + "' or normalize-space(.)='" + value + "']")
                .shouldBe(visible, enabled);

        // 5️⃣ Делаем стабильный JS-клик (для Creatio критично)
        Selenide.executeJavaScript("arguments[0].click();", item);

        // 6️⃣ Ждём, что список закроется = выбор зафиксирован
        listView.should(disappear);

        // 7️⃣ Проверяем, что поле теперь имеет выбранное значение
        input.shouldHave(Condition.exactValue(value));

        return this;
    }

    public static final String CONTRACT_PAGE_MARKER = "BnzContractCreditPageContainer";

    public ContractCreditApplicationPage clickElementDashboardName(String nameDashboard) {

        SelenideElement element = $x("//div[.='" + nameDashboard + "']")
                .shouldBe(visible)       // элемент виден
                .shouldBe(enabled);      // элемент кликабелен

        element.click();                 // безопасный клик

        return this;
    }


    //Работает уникальный
    public ContractCreditApplicationPage clickContractAutoWait(String pageMarker) {

        // ✅ Элемент старой страницы
        SelenideElement element =
                $x("//*[@data-item-marker='Contract']")
                        .scrollIntoView(true)
                        .shouldBe(visible)
                        .shouldBe(enabled);

        element.click();

        // ✅ ЖДЁМ, ЧТО СТАРАЯ СТРАНИЦА ИСЧЕЗЛА
        element.should(disappear);

        // ✅ ЯВНОЕ ОЖИДАНИЕ 3 СЕКУНДЫ ПЕРЕД ПРОВЕРКОЙ НОВОЙ СТРАНИЦЫ
        Selenide.sleep(3000);

        // ✅ ТОЛЬКО ПОСЛЕ ЭТОГО проверяем загрузку новой страницы
        $x("//*[@data-item-marker='" + pageMarker + "']")
                .shouldBe(visible);

        return this;
    }


    @Step("Выбрать вид получения кредита: {value}")
    public ContractCreditApplicationPage selectLoadCreditTypeNew(String value) {

        // 1️⃣ Контейнер поля
        SelenideElement control = $x(
                "//*[@id='BnzInputPlanningTypeModalBoxLoadCreditTypeContainer_Control']"
        ).scrollIntoView(true)
                .shouldBe(visible, enabled);

        // 2️⃣ Сам input комбобокса
        SelenideElement input = control.$x(".//input[contains(@id,'LoadCreditTypeComboBoxEdit-el')]")
                .shouldBe(visible, enabled);

        // 3️⃣ Открываем комбобокс и фокусируем input
        control.click();

        // 4️⃣ Вводим нужное значение и подтверждаем Enter
        input.clear();
        input.setValue(value);
        input.pressEnter();

        // 5️⃣ Проверяем, что значение реально установилось
        input.shouldHave(Condition.value(value));

        return this;
    }


    //Работает!!!
    // Метод №1 — сохранить значение из поля + проверить, что оно действительно сохранено (с RETRY до 5 раз)
    public ContractCreditApplicationPage saveValueDIMCheckWork(String sourceMarker) {

        String value = null;
        int attempts = 0;

        while (attempts < 3) {
            attempts++;

            try {
                // Находим элемент в каждой попытке (DOM может обновляться)
                SelenideElement source = $x("//*[@data-item-marker='" + sourceMarker + "']")
                        .shouldBe(visible);

                String tag = source.getTagName();

                // Если это input/textarea → берем value
                if ("input".equals(tag) || "textarea".equals(tag)) {
                    value = source.getValue();
                } else {
                    // Если это lookup/div/span → берем текст
                    value = source.getText();
                }

                // Проверка №1 — значение не должно быть пустым
                if (value == null || value.isEmpty()) {
                    System.out.println("⚠ Попытка " + attempts + ": значение пустое, пробуем снова...");
                    continue;
                }

                // Сохраняем
                this.savedValue = value;

                // Проверка №2 — корректно ли сохранилось
                if (!value.equals(this.savedValue)) {
                    System.out.println("⚠ Попытка " + attempts + ": не удалось сохранить значение, повтор...");
                    continue;
                }

                // Успешно — выходим
                System.out.println("✅ Значение успешно сохранено за " + attempts + " попыток: [" + value + "]");
                return this;

            } catch (Throwable e) {
                System.out.println("⚠ Ошибка на попытке " + attempts + ": " + e.getMessage());
            }
        }

        // Если сюда дошли — все 3 попыток провалены
        throw new AssertionError("❌ Не удалось сохранить значение (marker: " + sourceMarker + ") после 3 попыток!");
    }

    public ContractCreditApplicationPage clickSearchIconID(String lookupID) {

        // 1: Находим wrapper (куда нужно наводить мышку, чтобы лупа показалась)
        SelenideElement wrap = $x("//div[@id='" + lookupID + "-wrap']")
                .shouldBe(visible);

        wrap.hover(); // Обязательно!

        // 2: Находим правую иконку — ЭТО ЛУПА
        SelenideElement searchIcon = $x("//div[@id='" + lookupID + "-right-icon']")
                .shouldBe(visible)
                .shouldBe(enabled);

        // 3: Делаем JS-клик, потому что обычный click() может не работать
        executeJavaScript("arguments[0].click();", searchIcon);

        return this;
    }


    //Работает !!! Всталвяет сохраненное значение Сберегательного счёта
    public ContractCreditApplicationPage selectValueInLookupWork(String marker) {

        if (this.savedValue == null) {
            throw new IllegalStateException("❌ Нет сохранённого значения для вставки!");
        }

        // 1) Ищем wrapper lookup по data-item-marker
        SelenideElement wrapper = $x("//*[@data-item-marker='" + marker + "']")
                .shouldBe(visible)
                .shouldBe(enabled);

        // 2) Ищем input внутри wrapper
        SelenideElement input = wrapper.$("input")
                .shouldBe(visible)
                .shouldBe(enabled);

        // 3) Вставляем значение
        input.click();
        input.setValue(this.savedValue);

        // 4) Проверяем, что значение реально вставлено
        input.shouldHave(value(this.savedValue));

        // 5) RETRY ПОИСКА результата (до 10 попыток)
        SelenideElement itemRow = null;

        for (int i = 0; i < 10; i++) {
            try {
                // Нажимаем кнопку "Поиск"
                $x("//*[@data-tag='SearchButton']")
                        .shouldBe(visible)
                        .shouldBe(enabled)
                        .click();

                // Ищем строку результата по data-item-marker (это самый точный локатор!)
                itemRow = $x("//div[contains(@class,'grid-listed-row') and @data-item-marker='"
                        + this.savedValue + "']")
                        .shouldBe(visible, Duration.ofSeconds(1));

                break; // найдено → выходим

            } catch (Throwable ignored) {
                System.out.println("⏳ Ждём, пока появится счёт или номер..." + (i+1) + "/10");
                Selenide.sleep(1000);
            }
        }

        // Если после 10 попыток строка так и не появилась
        if (itemRow == null) {
            throw new AssertionError("❌ Счёт '" + this.savedValue + "' не найден в lookup после 10 попыток!");
        }

        // 6) Клик по найденной строке
        itemRow.click();

        // 7) Проверяем, что строка выделена
        // ВЫДЕЛЕННАЯ строка имеет класс grid-row-selected — мы нашли это по твоему DOM!
        itemRow.shouldHave(cssClass("grid-row-selected"));

        // 8) Нажимаем кнопку "Выбрать"
        $x("//*[@data-tag='SelectButton']")
                .shouldBe(visible)
                .shouldBe(enabled)
                .click();

        System.out.println("✅ Значение успешно выбрано в lookup: " + this.savedValue);

        return this;
    }


    public ContractCreditApplicationPage clickAndCheckModal(String liName, String modalText) {

        SelenideElement liElement = $x("//li[contains(text(), '" + liName + "')]")
                .shouldBe(visible)
                .shouldBe(enabled);

        liElement.click();

        // Проверка модального окна
        shouldSeeModalWithText(modalText);

        return this;
    }


    @Step("Выдать кредит способом: {issueType}")
    public ContractCreditApplicationPage issueCreditUniversal(String issueType) {

        for (int attempt = 1; attempt <= 5; attempt++) {
            try {
                log.info("Попытка №" + attempt + ": Выдача кредита способом '" + issueType + "'");

                // ✅ Меню "Выдача кредита" (СТРАНИЦА УЖЕ ОТКРЫТА)
                SelenideElement issueCreditMenu = $x("//li[@data-item-marker='Выдача кредита']")
                        .shouldBe(visible)
                        .shouldBe(enabled);

                issueCreditMenu.hover();

                // ✅ Кнопка конкретного способа (Наличными / Перечислением и т.д.)
                SelenideElement issueTypeButton = $x("//li[@data-item-marker='" + issueType + "']")
                        .shouldBe(visible)
                        .shouldBe(enabled);

                issueTypeButton.click();

                // ✅ Проверка, что модалка "Выдача кредита" открылась
                $x("//*[@data-item-marker='Выдача кредита']")
                        .shouldBe(visible)
                        .shouldHave(text("Выдача кредита"));

                log.info("✅ Кредит выдан способом '" + issueType + "'");
                return this;

            } catch (Exception e) {
                log.warn("⚠ Ошибка при попытке №" + attempt + ": " + e.getMessage());

                if (attempt == 5) {
                    throw new RuntimeException(
                            "❌ Не удалось выдать кредит способом '" + issueType + "' после 5 попыток", e
                    );
                }
            }
        }

        throw new IllegalStateException("Невозможное состояние в issueCreditUniversal()");
    }


    //Работает //06.12.2025
    @Step("Проверить открытие модального окна: {title}")
    public ContractCreditApplicationPage shouldBeModalOpened(String title) {

        // Ищем главный контейнер модалки (id оканчивается на -box)
        SelenideElement modal = $x(
                "//*[substring(@id, string-length(@id)-3)='-box']"
        ).shouldBe(visible, Duration.ofSeconds(10));

        // Проверяем заголовок
        modal.$x(".//label[contains(@id,'HeaderCaptionLabel')]")
                .shouldBe(visible)
                .shouldHave(Condition.exactText(title));

        // Проверяем кнопку Подтвердить
        modal.$x(".//span[@data-item-marker='ConfirmButton']")
                .shouldBe(visible)
                .shouldBe(enabled);

        // Проверяем кнопку Отмена
        modal.$x(".//span[@data-item-marker='CancelButton']")
                .shouldBe(visible)
                .shouldBe(enabled);

        return this;
    }




    @Step("Проверить, что поле '{fieldName}' имеет значение '{expectedValue}'")
    public void checkFieldValueNormalized(String fieldName, String expectedValue) {
        try {
            SelenideElement field = $x("//label[contains(text(),'" + fieldName + "')]/following::input[1]")
                    .shouldBe(Condition.visible, Duration.ofSeconds(10));

            String actualRaw = field.getValue() != null ? field.getValue() : field.getText();
            String expectedRaw = expectedValue;

            // Нормализуем оба значения
            String actual = normalizeForComparison(actualRaw);
            String expected = normalizeForComparison(expectedRaw);

            // Попытаемся сравнить как числа (удаляем grouping, переводим ','->'.' для парсинга)
            BigDecimal actualNum = tryParseNumber(actual);
            BigDecimal expectedNum = tryParseNumber(expected);

            if (actualNum != null && expectedNum != null) {
                if (actualNum.compareTo(expectedNum) != 0) {
                    logAndFail(fieldName, expectedValue, actualRaw);
                } else {
                    log.info("✔ Поле '{}' = {} (numeric match)", fieldName, expectedRaw);
                    Allure.step("Поле '"+fieldName+"' проверено: "+expectedRaw);
                    return;
                }
            } else {
                // fallback - строковое сравнение
                if (!expected.equals(actual)) {
                    logAndFail(fieldName, expectedValue, actualRaw);
                } else {
                    log.info("✔ Поле '{}' = {} (string match)", fieldName, expectedRaw);
                    Allure.step("Поле '"+fieldName+"' проверено: "+expectedRaw);
                    return;
                }
            }
        } catch (Throwable t) {
            attachScreenshot();
            attachPageSource();
            attachErrorMessage(t);
            throw t;
        }
    }

    // ----- helpers -----

    private String normalizeForComparison(String s) {
        if (s == null) return "";
        // 1) Replace common non-standard spaces to normal space
        s = s.replace('\u00A0', ' ')   // no-break space
                .replace('\u202F', ' ')   // narrow no-break
                .replace('\u2007', ' ')   // figure space
                .replace('\u2009', ' ');  // thin space
        // 2) Trim and collapse multiple spaces
        s = s.trim().replaceAll("\\s+", " ");
        return s;
    }

    private BigDecimal tryParseNumber(String s) {
        if (s == null || s.isEmpty()) return null;
        // remove non-digit except comma/dot/minus
        String cleaned = s.replaceAll("[^0-9,\\.-]", "");
        if (cleaned.isEmpty()) return null;
        // Try parse using comma as decimal separator first
        try {
            String norm = cleaned.replace(",", "."); // convert comma -> dot
            return new BigDecimal(norm);
        } catch (NumberFormatException ignored) {
        }
        // Last resort: try locale-aware parse (e.g. "30 000,00")
        try {
            NumberFormat nf = NumberFormat.getInstance(new Locale("ru"));
            Number n = nf.parse(s);
            return new BigDecimal(n.toString());
        } catch (ParseException | NumberFormatException ignored) {
        }
        return null;
    }

    private void logAndFail(String fieldName, String expected, String actualRaw) {
        String message = String.format("Значение поля '%s' неверное. Ожидали: '%s', получили: '%s'",
                fieldName, expected, actualRaw);
        // diagnostic: show codepoints of actual vs expected
        System.out.println("EXPECTED (hex): " + hexDump(expected));
        System.out.println("ACTUAL   (hex): " + hexDump(actualRaw));
        attachScreenshot();
        attachPageSource();
        Allure.step(message);
        throw new AssertionError(message);
    }

    private String hexDump(String s) {
        if (s == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            sb.append(String.format("%04x ", (int) s.charAt(i)));
        }
        return sb.toString().trim();
    }




    @Step("Выбор пункта печати: '{option}'")
    public void selectPrintOptionUniversal(String name) {
        try {
            System.out.println("➡ Открываю меню печати");

            // Открываем кнопку ПЕЧАТЬ (PrintButton всегда имеет data-item-marker)
            SelenideElement printBtn =
                    $x("//*[@data-item-marker='PrintButton']")
                            .shouldBe(Condition.visible, Duration.ofSeconds(10));

            executeJavaScript("arguments[0].click();", printBtn);
            Allure.step("Клик по кнопке 'Печать' выполнен");

            // Ищем пункт меню двумя способами (универсально)
            System.out.println("➡ Ищу пункт меню: " + name);

            SelenideElement item =
                    $x("//*[contains(@data-item-marker,'" + name + "') or contains(text(),'" + name + "')]")
                            .shouldBe(Condition.visible, Duration.ofSeconds(10));

            executeJavaScript("arguments[0].click();", item);
            Allure.step("Пункт меню '" + name + "' выбран");

            System.out.println("✔ Печатная форма запрошена: " + name);

        } catch (Throwable t) {
            attachScreenshot();
            attachPageSource();
            attachErrorMessage(t);
            throw t;
        }
    }

    public String getOrderState() {
        return $x(
                "//label[contains(text(),'Состояние ордера')]" +
                        "/ancestor::div[contains(@class,'label-wrap')]" +
                        "/following-sibling::div[contains(@class,'control-wrap')]" +
                        "//input"
        ).shouldBe(Condition.visible, Duration.ofSeconds(10))
                .getValue()
                .trim();
    }





}



