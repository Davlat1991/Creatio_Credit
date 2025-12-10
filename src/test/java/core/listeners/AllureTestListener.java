package core.listeners;


import io.qameta.allure.Attachment;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.getWebDriverLogs;

public class AllureTestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        saveScreenshot();
        savePageSource();
        saveBrowserLogs();
        saveErrorMessage(result.getThrowable());
        saveStackTrace(result.getThrowable());
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] saveScreenshot() {
        try {
            return ((TakesScreenshot) WebDriverRunner.getWebDriver())
                    .getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            return new byte[0];
        }
    }

    @Attachment(value = "Page Source", type = "text/html")
    public byte[] savePageSource() {
        try {
            return WebDriverRunner.source().getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "PageSource not available".getBytes(StandardCharsets.UTF_8);
        }
    }

    @Attachment(value = "Browser Console Logs", type = "text/plain")
    public String saveBrowserLogs() {
        try {
            return String.join("\n", getWebDriverLogs("browser"));
        } catch (Exception e) {
            return "No browser logs available";
        }
    }

    @Attachment(value = "Test Failure Reason", type = "text/plain")
    public String saveErrorMessage(Throwable throwable) {
        return throwable == null ? "No error message" : throwable.getMessage();
    }

    @Attachment(value = "Stacktrace", type = "text/plain")
    public String saveStackTrace(Throwable throwable) {
        if (throwable == null) return "No stacktrace";
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : throwable.getStackTrace()) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}
