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
 */


public class ContractCreditApplicationPage extends BasePage {

    public static final String CONTRACT_PAGE_MARKER = "BnzContractCreditPageContainer";



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
    public static final Logger log =
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



    @Step("Получить и открыть график платежей")
    public ContractCreditApplicationPage openPaymentSchedule() {
        buttons.clickByName("Получить график платежей");
        grid.doubleClickFirstRow("PaymentScheduleDetail");
        return this;
    }

    //Статус: ⚠️ оставить временно

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

    // Статус: ⚠️ оставить, но НЕ выносить дальше

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
    public void clickAddBorrower() {
        new ButtonsComponent().clickByDataItemMarker("AddBorrowerButton");
    }
    public String getApplicationNumber() {
        return $x("//*[@data-item-marker='Number']").getValue();
    }


// Сейчас метод слишком тяжёлый
//Page должна оркестрировать, а не делать всё сама
//Статус: ⚠️ оставить временно, позже разбить

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


//  Статус: ❌ убрать из Page → ✅ перенести в FieldComponent

    public ContractCreditApplicationPage setfieldScheduleDetailByDIM(String name, String value) {
        $x("//div[@data-item-marker='" + name + "']/input").setValue(value);
        return this;
    }

    // core.base.common.components.LookupComponent

    public ContractCreditApplicationPage setHandBookFieldByValue(String nameField, String value) {
        setfieldScheduleDetailByDIM(nameField, value);
        $x("//div[contains(@class,'listview')]//li[.='" + value + "']").click();
        return this;
    }


    @Step("Сохранить значение поля по marker '{marker}'")
    public ContractCreditApplicationPage saveValueByMarker(String number) {
        this.savedValue = FieldUtils.getValueByMarker(number);
        System.out.println("✔ Saved [" + number + "] = " + this.savedValue);
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


    private final PrintComponent print = new PrintComponent();

    public ContractCreditApplicationPage print(String name) {
        print.selectPrintOption(name);
        return this;
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


    @Step("Завершить консультацию")
    public void completeConsultation() {

        // 1. Нажимаем кнопку Завершить в панели консультации
        System.out.println("➡ Клик по кнопке 'Завершить' в ConsultationPanel");
        SelenideElement completeBtnPanel = $x("//*[@data-item-marker='CompleteConsultationButton']")
                .shouldBe(Condition.visible, Duration.ofSeconds(10));
        executeJavaScript("arguments[0].click();", completeBtnPanel);
        Allure.step("Клик по кнопке панели 'Завершить'");

        // 2. Ждём появления модального окна
        System.out.println("⏳ Ожидание появления модального окна завершения консультации...");
        SelenideElement modalCompleteBtn = $x("//*[@data-item-marker='CompleteButton']")
                .shouldBe(Condition.visible, Duration.ofSeconds(10));

        // 3. Кликаем кнопку Завершить в модальном окне
        System.out.println("➡ Клик по кнопке 'Завершить' в модальном окне");
        executeJavaScript("arguments[0].click();", modalCompleteBtn);
        Allure.step("Клик по кнопке модального окна 'Завершить'");

        System.out.println("✔ Консультация успешно завершена");
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



}



