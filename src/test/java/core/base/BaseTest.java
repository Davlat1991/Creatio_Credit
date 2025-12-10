package core.base;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import core.listeners.AllureTestListener;
import com.codeborne.selenide.Configuration;
import core.base.common.components.FieldComponent;
import core.pages.credit.ConsultationPanelPage;
import core.pages.credit.ContractCreditApplicationPage;
import core.pages.login.LoginPage;
import core.pages.ui.DetailPage;
import core.pages.ui.MainMenuAndWorkSpacePage;
import core.config.ConfigProperties;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;


@Listeners({AllureTestListener.class})


public class BaseTest {

    // URL
    protected final String BASE_URL = ConfigProperties.get("base.url");

    // PageObject'ы
    protected LoginPage loginPage;
    protected FieldComponent fieldPage;
    protected MainMenuAndWorkSpacePage workspacePage;
    protected ContractCreditApplicationPage contractPage;
    protected ConsultationPanelPage consultationPanel;
    protected DetailPage detailPage;

    @BeforeMethod(alwaysRun = true)
    public void configure() {
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 20000;
        Configuration.savePageSource = false;
        Configuration.screenshots = true;
    }

    @BeforeMethod(alwaysRun = true)
    public void initPages() {
        loginPage = new LoginPage();
        fieldPage = new FieldComponent();
        workspacePage = new MainMenuAndWorkSpacePage();
        contractPage = new ContractCreditApplicationPage();
        consultationPanel = new ConsultationPanelPage();
        detailPage = new DetailPage();
    }



    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(false)
                        .includeSelenideSteps(false)
        );

        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
    }



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

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            attachScreenshot();
            attachPageSource();
            attachBrowserLogs();
        }
        Selenide.closeWebDriver();
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

            try (OutputStream out = Files.newOutputStream(resultsDir.resolve("environment.properties"))) {
                props.store(out, "Allure environment properties");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Allure environment", e);
        }
    }


}
