package core.base.common.components;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * ⚠ LEGACY component
 * Используется временно для завершения кредитного флоу
 */
public class FileUploadLegacyComponent {

    // 1️⃣ Переход на вкладку (Документы)
    @Step("Нажать кнопку '{name}'")
    public void clickButtonByContainName(String name) {
        $x("//span[contains(text(), '" + name + "')]").click();
    }

    // 2️⃣ JS fix FileApiService
    @Step("Инициализация FileApiService")
    public void startUpload() {
        executeJavaScript(
                "const originalOpen = XMLHttpRequest.prototype.open;" +
                        "XMLHttpRequest.prototype.open = function(method, url) {" +
                        "  if (url.includes('FileApiService')) {" +
                        "    url = url.replace(/(?:Tsi)*FileApiService/, 'TsiFileApiService');" +
                        "  }" +
                        "  return originalOpen.apply(this, arguments);" +
                        "};"
        );
    }

    // 3️⃣ Upload по индексу (как в старом проекте)
    @Step("Загрузить файл '{fileName}' (index = {index})")
    public void uploadFile(String fileName, int index) {
        $x("//input[@data-item-marker='AddRecordButton'][" + index + "]")
                .uploadFile(new File("src/test/resources/files/" + fileName));
    }

    // 4️⃣ Клик по 'Файлы (' + index
    @Step("Открыть список файлов '{nameButton}' (index = {index})")
    public void clickButtonByNameContains(String nameButton, int index) {
        $x("(//span[contains(.,'" + nameButton + " (')])[" + index + "]").click();
    }

    // 5️⃣ Проверка загрузки
    @Step("Проверить, что файл '{fileName}' загружен")
    public void validateUploadFile(String fileName) {
        $x("//div[@data-item-marker='" + fileName + "']")
                .shouldBe(visible);
    }

    @Step("Получить parentColumnValue (tabId = {tabId}, index = {index})")
    public String getParentColumnValue1(String tabId, int index) {

        return (String) Selenide.executeJavaScript(
                "var container = document.getElementById(arguments[0]);" +
                        "if (!container) return null;" +

                        "var inputs = container.querySelectorAll(\"input[data-item-marker='AddRecordButton']\");" +
                        "if (!inputs || inputs.length === 0) return null;" +

                        "var input = inputs[arguments[1] - 1];" +
                        "if (!input) return null;" +

                        "var match = input.id.match(/V2_([a-f0-9\\-]{36})Tsi/);" +
                        "return match ? match[1] : null;",

                tabId, index
        );
    }

    @Step("Получить parentColumnValue (index = {index})")
    public String getParentColumnValue2(int index) {

        // ✅ Ждём пока появятся кнопки загрузки (самый стабильный якорь)
        $$x("//div[not(contains(@style,'display: none'))]//input[@data-item-marker='AddRecordButton']")
                .shouldBe(CollectionCondition.sizeGreaterThanOrEqual(index));

        String value = (String) Selenide.executeJavaScript(

                "var container = document.querySelector(\"div[data-item-marker='tab-content-container-marker']:not([style*='display: none'])\");" +
                        "if (!container) return null;" +

                        "var inputs = container.querySelectorAll(\"input[data-item-marker='AddRecordButton']\");" +
                        "if (!inputs || inputs.length === 0) return null;" +

                        "var input = inputs[arguments[0] - 1];" +
                        "if (!input) return null;" +

                        "var match = input.id.match(/V2_([a-f0-9\\-]{36})Tsi/);" +
                        "return match ? match[1] : null;",

                index
        );

        if (value == null) {
            throw new RuntimeException("❌ parentColumnValue = NULL → DOM не успел прогрузиться");
        }

        return value;
    }



    @Step("Получить parentColumnValue по названию документа '{docName}'")
    public String getParentColumnValueByName(String docName) {

        return (String) Selenide.executeJavaScript(
                "var inputs = document.querySelectorAll(\"input[data-item-marker='AddRecordButton']\");" +

                        "for (var i = 0; i < inputs.length; i++) {" +
                        "   var input = inputs[i];" +

                        // ищем ближайший текст рядом (название документа)
                        "   var container = input.closest('div');" +
                        "   if (!container) continue;" +

                        "   if (container.innerText.includes(arguments[0])) {" +
                        "       var match = input.id.match(/V2_([a-f0-9\\-]{36})/);" +
                        "       if (match) return match[1];" +
                        "   }" +
                        "}" +

                        "return null;",
                docName
        );
    }


    @Step("Получить все parentColumnValue")
    public List<String> getAllParentColumnValues() {

        return (List<String>) Selenide.executeJavaScript(
                "var inputs = document.querySelectorAll(\"input[data-item-marker='AddRecordButton']\");" +
                        "var result = [];" +
                        "for (var i = 0; i < inputs.length; i++) {" +
                        "   var match = inputs[i].id.match(/V2_([a-f0-9\\-]{36})/);" +
                        "   if (match) result.push(match[1]);" +
                        "}" +
                        "return result;"
        );
    }


    @Step("Получить fieldId по кнопке загрузки")
    public String getFieldIdByIndex(String tabId, int index) {

        return Selenide.executeJavaScript(
                "var container = document.getElementById(arguments[0]);" +
                        "if (!container) return null;" +

                        "var inputs = container.querySelectorAll(\"input[data-item-marker='AddRecordButton']\");" +
                        "if (!inputs || inputs.length === 0) return null;" +

                        "var input = inputs[arguments[1] - 1];" +
                        "if (!input) return null;" +

                        "return input.id;",
                tabId, index
        );
    }


    @Step("Получить parentColumnValue для детали '{detailCaption}'")
    public String getParentColumnValueByDetail(String detailCaption) {
        return (String) Selenide.executeJavaScript(
                // Ищем контейнер детали по тексту заголовка
                "var headers = document.querySelectorAll('.detail-caption, .ts-caption-label, [class*=\"caption\"]');" +
                        "for (var i = 0; i < headers.length; i++) {" +
                        "  if (headers[i].textContent.trim() === arguments[0]) {" +
                        "    var detail = headers[i].closest('[id*=\"FileDetailV2\"], [class*=\"detail\"]');" +
                        "    if (detail) {" +
                        "      var input = detail.querySelector(\"input[data-item-marker='AddRecordButton']\");" +
                        "      if (input) {" +
                        "        var match = input.id.match(/V2_([a-f0-9\\-]{36})Tsi/);" +
                        "        return match ? match[1] : null;" +
                        "      }" +
                        "    }" +
                        "  }" +
                        "}" +
                        "return null;",
                detailCaption
        );
    }







}
