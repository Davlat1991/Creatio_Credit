package core.base.common.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.io.File;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Компонент для стабильной загрузки файлов в Creatio.
 * Поддерживает загрузку через data-item-marker, id, name, generic locators.
 */
public class FileUploadComponent extends Components {

    private final String baseFilePath = "src/main/resources/resourcesFiles/";

    /**
     * Основной метод загрузки файла через data-item-marker="AddRecordButton" (стандарт Creatio).
     */
    @Step("Загрузить файл '{fileName}' через AddRecordButton")
    public FileUploadComponent upload(String fileName) {
        SelenideElement uploadInput = $x("//input[@data-item-marker='AddRecordButton']")
                .shouldBe(Condition.exist)
                .shouldBe(Condition.visible);

        File file = new File(baseFilePath + fileName);

        uploadInput.uploadFile(file);

        return this;
    }

    /**
     * Загрузка файла через произвольный data-item-marker поля.
     */
    @Step("Загрузить файл '{fileName}' через marker='{marker}'")
    public FileUploadComponent uploadByMarker(String marker, String fileName) {
        SelenideElement uploadInput =
                $x("//input[@data-item-marker='" + marker + "']")
                        .shouldBe(Condition.exist)
                        .shouldBe(Condition.visible);

        uploadInput.uploadFile(new File(baseFilePath + fileName));

        return this;
    }

    /**
     * Загрузка файла по любому XPath локатору input[type='file']
     */
    @Step("Загрузить файл '{fileName}' по xpath='{xpath}'")
    public FileUploadComponent uploadByXpath(String xpath, String fileName) {
        SelenideElement uploadInput =
                $x(xpath).shouldBe(Condition.exist).shouldBe(Condition.visible);

        uploadInput.uploadFile(new File(baseFilePath + fileName));

        return this;
    }

    /**
     * Иногда Creatio делает input невидимым → пробуем JS-клик + retry.
     */
    @Step("Принудительная загрузка файла '{fileName}' через скрытый input marker='{marker}'")
    public FileUploadComponent forceUpload(String marker, String fileName) {

        SelenideElement input = $x("//input[@data-item-marker='" + marker + "']")
                .should(Condition.exist);

        File file = new File(baseFilePath + fileName);

        // Creatio иногда скрывает input — загружаем даже если не visible
        input.uploadFile(file);

        return this;
    }

    /**
     * Проверка, что файл появился в списке загруженных файлов.
     */
    @Step("Проверить, что файл '{fileName}' успешно загружен")
    public FileUploadComponent validateUploaded(String fileName) {

        // Проверка по названию файла в grid документов
        $x("//a[contains(text(),'" + fileName + "')]")
                .shouldBe(Condition.visible);

        return this;
    }
}

