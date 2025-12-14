package core.base;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class TestListener implements ITestListener {

    // -----------------------------
    // 🔥 Allure Attachments
    // -----------------------------

    @Attachment(value = "Screenshot", type = "image/png")
    private byte[] attachScreenshot() {
        try {
            return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (Throwable ignore) {
            return new byte[0];
        }
    }

    @Attachment(value = "Page Source", type = "text/html", fileExtension = ".html")
    private byte[] attachPageSource() {
        try {
            return getWebDriver().getPageSource().getBytes();
        } catch (Throwable ignore) {
            return new byte[0];
        }
    }

    @Attachment(value = "Error message", type = "text/plain")
    private String attachException(Throwable throwable) {
        return throwable == null ? "No message" : throwable.toString();
    }

    // -----------------------------
    // 🔥 TestNG Listener
    // -----------------------------

    @Override
    public void onStart(ITestContext context) {
        System.out.println("📘 START TEST RUN: " + context.getName());

        // Включаем шаги Selenide в Allure
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true)
                        .includeSelenideSteps(true)
        );
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("📙 FINISH TEST RUN: " + context.getName());
        SelenideLogger.removeListener("AllureSelenide");
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("🟦 START TEST: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("🟩 SUCCESS TEST: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("🟥 FAILED TEST: " + result.getName());

        attachScreenshot();
        attachPageSource();
        attachException(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⚠ SKIPPED TEST: " + result.getName());
        attachScreenshot();
    }
}
