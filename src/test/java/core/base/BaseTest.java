package core.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import core.data.contacts.ContactData;
import core.data.users.LoginData;
import flows.credit.AuthorizationAndClientSearchFlow;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.*;

import core.listeners.AllureTestListener;
import core.config.DriverFactory;
import core.config.ConfigProperties;

import core.base.common.components.FieldComponent;
import core.pages.credit.ConsultationPanelPage;
import core.pages.credit.ContractCreditApplicationPage;
import core.pages.login.LoginPage;
import core.pages.ui.DetailPage;
import core.pages.ui.DashboardPage;

import steps.workspace.WorkspaceSteps;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;


@Listeners({AllureTestListener.class})
public class BaseTest {

    // URL из environment.properties
    protected final String BASE_URL = ConfigProperties.get("base.url");

    // PageObject'ы
    protected LoginPage loginPage;
    protected FieldComponent fieldPage;
    protected DashboardPage dashboardPage;
    protected ContractCreditApplicationPage contractPage;
    protected ConsultationPanelPage consultationPanel;
    protected DetailPage detailPage;
    protected WorkspaceSteps workspaceSteps;
    protected AuthorizationAndClientSearchFlow authAndClientFlow;
    protected BasePage basePage;
    protected LoginData retailManager =
            new LoginData("S_RUSTMOVA_796", "S_RUSTMOVA_796S_RUSTMOVA_796");




    // -------------------------- BEFORE SUITE --------------------------

    @BeforeSuite(alwaysRun = true)
    public void setUpDriver() {
        DriverFactory.configure();
        System.out.println("⚙ DriverFactory configured");
    }

    @BeforeSuite(alwaysRun = true)
    public void generateAllureEnvironment() {
        try {
            Properties props = new Properties();
            props.put("Environment", System.getProperty("environment", "QA"));
            props.put("Base URL", ConfigProperties.get("base.url"));
            props.put("Browser", ConfigProperties.get("browser"));
            props.put("Author", "Davlat");
            props.put("Project", "Creatio Credit UI Tests");

            Path resultsDir = Paths.get("target", "allure-results");
            Files.createDirectories(resultsDir);

            try (OutputStream out = Files.newOutputStream(
                    resultsDir.resolve("environment.properties"))) {
                props.store(out, "Allure environment properties");
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Allure environment", e);
        }
    }

    // -------------------------- BEFORE METHOD --------------------------

    @BeforeMethod(alwaysRun = true)
    public void setUpAllure() {
        SelenideLogger.addListener(
                "AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)              // Скрин только при ошибке → must-have
                        .savePageSource(false)          // Creatio DOM огромный → не сохраняем
                        .includeSelenideSteps(false)    // Оставляем только ручные @Step
        );
    }

    @BeforeMethod(alwaysRun = true)
    public void initPages() {
        loginPage = new LoginPage();
        fieldPage = new FieldComponent();
        dashboardPage = new DashboardPage();
        consultationPanel = new ConsultationPanelPage();
        detailPage = new DetailPage();
        workspaceSteps = new WorkspaceSteps();
        contractPage = new ContractCreditApplicationPage();
        basePage = new BasePage();


    }

    // -------------------------- ATTACHMENTS --------------------------

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] attachScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver())
                .getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page Source", type = "text/html")
    public byte[] attachPageSource() {
        return WebDriverRunner.source().getBytes(StandardCharsets.UTF_8);
    }

    @Attachment(value = "Browser console logs", type = "text/plain")
    public String attachBrowserLogs() {
        return String.join("\n", Selenide.getWebDriverLogs("browser"));
    }

    // -------------------------- AFTER METHOD --------------------------

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {
            attachScreenshot();
            attachPageSource();
            attachBrowserLogs();
        }

        Selenide.closeWebDriver();
    }

    @BeforeClass(alwaysRun = true)
    public void setBrowserSizeBeforeDriverStart() {
        Configuration.browserSize = ConfigProperties.get("browser.size", "1530x970");
        System.out.println("📏 Browser size configured: " + Configuration.browserSize);
    }

    public ContactData contact = new ContactData
                (   "Сафарали",
                    "Нусратович",
                    "Садуллоев",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);

    public String BASE_ULR = "http://10.10.202.245/";
    public String BASE_ULR_1 = "http://10.10.202.254/";

}