package core.base.common.components;

import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.$$;

public class FileUploadComponent {

    @Step("Проверить, что в активной детали {expected} файлов")
    public void shouldHaveFilesCount(int expected) {
        $$(".grid-listed .grid-row")
                .shouldHave(size(expected));
    }
}
