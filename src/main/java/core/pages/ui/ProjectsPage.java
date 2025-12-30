package core.pages.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import core.pages.credit.ContractCreditApplicationPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class ProjectsPage {

    public static final Logger log =
            LoggerFactory.getLogger(ProjectsPage.class);

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
}
