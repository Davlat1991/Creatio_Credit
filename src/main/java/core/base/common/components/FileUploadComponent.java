package core.base.common.components;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.io.File;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class FileUploadComponent {

    @Step("Проверить, что в активной детали {expected} файлов")
    public void shouldHaveFilesCount(int expected) {
        $$(".grid-listed .grid-row")
                .shouldHave(size(expected));
    }

    //16.01.2026
    @Step("Загрузить файл '{fileName}' в деталь '{detailTitle}'")
    public void uploadFileToDetail(String detailTitle, String fileName) {

        // 1️⃣ Caption детали
        SelenideElement caption = $x(
                "//span[normalize-space()='" + detailTitle + "']"
        ).shouldBe(visible);

        // 2️⃣ Корневой ControlGroup детали (КРИТИЧНО!)
        SelenideElement detailRoot = caption.closest(
                "div.ts-controlgroup, div.control-group, div[class*='controlgroup']"
        ).shouldBe(visible);

        // 3️⃣ Панель инструментов ЭТОЙ детали
        SelenideElement tools = detailRoot.$(
                ".ts-controlgroup-tools"
        ).shouldBe(visible);

        // 4️⃣ Кнопка «Добавить файл» ВНУТРИ tools
        SelenideElement addButton = tools.$(
                "span[data-item-marker='AddRecordButton']"
        ).shouldBe(visible, enabled);

        // 5️⃣ Клик (JS обязателен)
        executeJavaScript("arguments[0].click();", addButton);

        // 6️⃣ input[type=file] появляется ВНУТРИ ТОЙ ЖЕ детали
        SelenideElement fileInput = detailRoot.$(
                "input[type='file']"
        ).shouldBe(exist, enabled);

        // 7️⃣ Upload
        fileInput.uploadFile(
                new File("src/test/resources/files/" + fileName)
        );
    }




}
