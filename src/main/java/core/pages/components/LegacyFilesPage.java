package core.pages.components;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;

public class LegacyFilesPage {

    @Step("Получить parentColumnValue из DOM (tabId = {tabId}, index = {index})")
    public String getParentColumnValue(String tabId, int index) {
        return (String) Selenide.executeJavaScript(
                "var container = document.getElementById(arguments[0]);" +
                        "if (!container) return null;" +
                        "var inputs = container.querySelectorAll(\"input[data-item-marker='AddRecordButton']\");" +
                        "var input = inputs[arguments[1] - 1];" +
                        "if (!input) return null;" +
                        "var match = input.id.match(/V2_([a-f0-9\\-]{36})Tsi/);" +
                        "return match ? match[1] : null;",
                tabId, index
        );
    }
}
