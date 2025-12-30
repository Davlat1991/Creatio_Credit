package core.assertions;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$x;


public class FieldAssertions {

    public static final Logger log =
            LoggerFactory.getLogger(FieldAssertions.class);

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

    private static String normalizeForComparison(String s) {
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

    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] attachScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver())
                .getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page Source", type = "text/html")
    public static byte[] attachPageSource() {
        return WebDriverRunner.source().getBytes(StandardCharsets.UTF_8);
    }

    @Attachment(value = "Browser console logs", type = "text/plain")
    public static String attachBrowserLogs() {
        return String.join("\n", Selenide.getWebDriverLogs("browser"));
    }

    @Attachment(value = "Error message", type = "text/plain")
    public static String attachErrorMessage(Throwable t) {
        return t.getMessage();
    }



    // ----- helpers -----


    private static BigDecimal tryParseNumber(String s) {
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

    private static void logAndFail(String fieldName, String expected, String actualRaw) {
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

    private static String hexDump(String s) {
        if (s == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            sb.append(String.format("%04x ", (int) s.charAt(i)));
        }
        return sb.toString().trim();
    }




}
