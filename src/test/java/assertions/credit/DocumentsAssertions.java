package assertions.credit;

import core.base.UiContext;
import io.qameta.allure.Step;

public class DocumentsAssertions {

    private final UiContext ctx;

    public DocumentsAssertions(UiContext ctx) {
        this.ctx = ctx;
    }

    @Step("Проверить, что в детали '{detailTitle}' {expected} файлов")
    public void shouldHaveUploadedFiles(String detailTitle, int expected) {

        ctx.buttonsComponent.openDocumentsTab();
        ctx.detailPage.openDetailByName(detailTitle);
        ctx.detailPage.waitUntilDetailGridReady();

        ctx.fileUploadComponent.shouldHaveFilesCount(expected);
    }
}
