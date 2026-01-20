package flows.credit;

import core.base.TestContext;
import io.qameta.allure.Step;

public class DocumentsStageFlow {

    private final TestContext ctx;

    public DocumentsStageFlow(TestContext ctx) {
        this.ctx = ctx;
    }

    // =====================================================
    // 📄 DOCUMENTS STAGE (LEGACY)
    // =====================================================

    @Step("Documents: загрузка документов (legacy)")
    public void uploadDocumentsLegacy() {

        openDocumentsTab();

        uploadFinancialDossier();
        uploadClientDossier();
        uploadAdditionalClientDossier();
    }

    // =====================================================
    // NAVIGATION
    // =====================================================

    private void openDocumentsTab() {
        ctx.contractPage
                .legacyFiles()
                .clickButtonByContainName("Документы");
    }

    // =====================================================
    // DOSSIERS
    // =====================================================

    private void uploadFinancialDossier() {

        ctx.detailPage.openDetailByName("Финансовое досье");

        startUploadIfNeeded();

        uploadAndValidate(
                "Registration (Example).xlsx",
                1
        );
    }

    private void uploadClientDossier() {

        ctx.detailPage.openDetailByName("Досье клиента");

        uploadAndValidate(
                "Registration (Example).xlsx",
                2
        );
    }

    private void uploadAdditionalClientDossier() {

        uploadAndValidate(
                "Registration (Example).xlsx",
                3
        );
    }

    // =====================================================
    // UPLOAD HELPERS
    // =====================================================

    private void startUploadIfNeeded() {
        ctx.contractPage
                .legacyFiles()
                .startUpload();
    }

    private void uploadAndValidate(String fileName, int slotIndex) {

        ctx.contractPage
                .legacyFiles()
                .uploadFile(fileName, slotIndex);

        ctx.contractPage
                .legacyFiles()
                .clickButtonByNameContains("Файлы", slotIndex);

        ctx.contractPage
                .legacyFiles()
                .validateUploadFile(fileName);
    }
}
