package flows.credit;

import core.base.UiContext;
import io.qameta.allure.Step;

public class DocumentsStageFlow {

    private final UiContext ui;

    public DocumentsStageFlow(UiContext ui) {
        this.ui = ui;
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
        completeDocumentsActivity();
        verifyCreditDecisionApproved();
        saveApplicationNumber();
    }

    // =====================================================
    // NAVIGATION
    // =====================================================

    private void openDocumentsTab() {
        ui.contractPage
                .legacyFiles()
                .clickButtonByContainName("Документы");
    }

    // =====================================================
    // DOSSIERS
    // =====================================================

    private void uploadFinancialDossier() {

        ui.detailPage.openDetailByName("Финансовое досье");

        startUploadIfNeeded();

        uploadAndValidate(
                "Справка о доходах.pdf",
                1
        );
    }

    private void uploadClientDossier() {

        ui.detailPage.openDetailByName("Досье клиента");

        uploadAndValidate(
                "Шиносномаи шахрванди ЧТ.pdf",
                2
        );
    }

    private void uploadAdditionalClientDossier() {

        uploadAndValidate(
                "ИНН.pdf",
                3
        );
    }

    // =====================================================
    // UPLOAD HELPERS
    // =====================================================

    private void startUploadIfNeeded() {
        ui.contractPage
                .legacyFiles()
                .startUpload();
    }

    private void uploadAndValidate(String fileName, int slotIndex) {

        ui.contractPage
                .legacyFiles()
                .uploadFile(fileName, slotIndex);

        ui.contractPage
                .legacyFiles()
                .clickButtonByNameContains("Файлы", slotIndex);

        ui.contractPage
                .legacyFiles()
                .validateUploadFile(fileName);
    }

    // =====================================================
    // COMPLETE DOCUMENTS ACTIVITY
    // =====================================================

    private void completeDocumentsActivity() {

        ui.dashboardComponent.clickElementDashboardCheck(
                "Вложить документы и отправить на рассмотрение",
                "Execute",
                "//*[@data-item-marker='MiniPage']"
        );

        ui.contractPage
                .setfieldScheduleDetailByDIM("Result", "Выполнена");

        ui.menuComponent
                .clickButtonByLiName("Выполнена");

        ui.basePage
                .clickButtonByDataItemMaker("SaveEditButton");
    }

    //Новый метод нужно протестировать
    private void verifyCreditDecisionApproved() {
        ui.gridAssertions.waitForAnyCreditDecision(); //Одобрить Отказать

    }

    private void saveApplicationNumber() {
        ui.contractPage
                .saveValueByMarker("Number");
    }



}
