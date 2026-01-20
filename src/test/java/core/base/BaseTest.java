package core.base;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import core.base.common.utils.TestState;
import core.config.DriverFactory;
import core.config.Environment;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Listeners({AllureTestNg.class})
public abstract class BaseTest {

    protected TestContext ctx;

    // ==========================
    // BEFORE SUITE
    // ==========================

    @BeforeSuite(alwaysRun = true)
    public void setUpDriver() {
        DriverFactory.configure();
    }

    @BeforeSuite(alwaysRun = true)
    public void generateAllureEnvironment() {
        try {
            Properties props = new Properties();
            props.put("Environment", Environment.ENV_NAME);
            props.put("Base URL", Environment.BASE_URL);
            props.put("Browser", System.getProperty("browser", "chrome"));
            props.put("Project", "Creatio Credit UI Tests");

            Path resultsDir = Paths.get("target", "allure-results");
            Files.createDirectories(resultsDir);

            try (OutputStream out =
                         Files.newOutputStream(resultsDir.resolve("environment.properties"))) {
                props.store(out, "Allure environment properties");
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Allure environment", e);
        }
    }

    // ==========================
    // BEFORE METHOD
    // ==========================

    @BeforeMethod(alwaysRun = true)
    public void clearState() {
        System.out.println("🧹 TestState очищен");
        TestState.clear();
    }


    @BeforeMethod(alwaysRun = true)
    public void setUpAllure() {
        SelenideLogger.addListener(
                "AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(false)
                        .includeSelenideSteps(false)
        );
    }

    @BeforeMethod(alwaysRun = true)
    public void initContext() {
        ctx = new TestContext();
    }

    // ==========================
    // AFTER METHOD
    // ==========================

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {
            attachScreenshot();
            attachPageSource();
            attachBrowserLogs();
        }

        Selenide.closeWebDriver();
    }

    // ==========================
    // ALLURE ATTACHMENTS
    // ==========================

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
}
