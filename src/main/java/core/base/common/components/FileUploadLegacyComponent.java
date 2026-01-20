package core.base.common.components;

import io.qameta.allure.Step;

import java.io.File;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;

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
}
