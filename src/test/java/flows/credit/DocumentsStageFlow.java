package flows.credit;

import core.api.DocumentUploadApi;
import core.base.TestContext;
import io.qameta.allure.Step;

import java.io.File;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class DocumentsStageFlow {

    private final TestContext ctx;

    public DocumentsStageFlow(TestContext ctx) {
        this.ctx = ctx;
    }

    @Step("Загрузить файл в деталь '{detailTitle}' через API")
    public void uploadFileToDetail(String detailTitle, String fileName) {

        // 0️⃣ Переход на вкладку Документы (КРИТИЧНО)
        ctx.buttonsComponent.openDocumentsTab();

        // 1️⃣ Открываем деталь
        ctx.detailPage.openDetailByName(detailTitle);

        // 2️⃣ ЖДЁМ, пока появится хотя бы пустой grid
        ctx.detailPage.waitUntilDetailGridReady();

        // 3️⃣ Берём ID записи детали
        String parentColumnValue =
                ctx.detailPage.getActiveDetailRecordId();

        // 4️⃣ Cookie
        String cookie =
                getWebDriver().manage().getCookies()
                        .stream()
                        .map(c -> c.getName() + "=" + c.getValue())
                        .reduce((a, b) -> a + "; " + b)
                        .orElseThrow();

        // 5️⃣ Файл
        File file = new File("src/test/resources/files/" + fileName);

        // 6️⃣ API upload
        DocumentUploadApi.uploadFile(
                ctx.baseUrl,
                cookie,
                file,
                parentColumnValue
        );

        // 7️⃣ ОБЯЗАТЕЛЬНО обновляем UI
        ctx.detailPage.refreshDetail();
    }
}
