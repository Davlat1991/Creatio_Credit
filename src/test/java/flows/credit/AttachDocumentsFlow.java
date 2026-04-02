package flows.credit;

import com.codeborne.selenide.SelenideElement;
import core.base.UiContext;
import flows.common.ApplicationSearchFlow;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class AttachDocumentsFlow {

    private final UiContext ui;
    private static final Logger log = LoggerFactory.getLogger(AttachDocumentsFlow.class);

    private final ApplicationSearchFlow applicationSearchFlow;

    public AttachDocumentsFlow(UiContext ui) {
        this.ui = ui;
        this.applicationSearchFlow = new ApplicationSearchFlow(ui);

    }

    @Step("Attach Document: полный сценарий прикрепления документа")
    public void attachDocument() {

        ui.basePage.closeConsultationPanelIfOpened();

        // 1. Открыть договор по сохранённому номеру
        applicationSearchFlow.openBySavedСontracts();

        openAttachmentsTab();
        uploadFinancialDossier();
        checkFileUploaded("Справка о доходах.pdf");
        completeAttachmentDocument();

    }



    // =====================================================
    // NAVIGATION
    // =====================================================

    @Step("Открыть вкладку Параметры договора")
    private void openAttachmentsTab() {

        ui.buttonsComponent
                .clickButtonByContainNameCheck("Файлы и примечания");
    }
    // =====================================================
    // DOSSIERS
    // =====================================================

    private void uploadFinancialDossier() {

        ui.detailPage.openDetailByName("Файлы и ссылки");

        startUploadIfNeeded();

        uploadAndValidate("Справка о доходах.pdf",1);
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

    }



    @Step("Проверить, что файл '{fileName}' загружен")
    private void checkFileUploaded(String fileName) {

        Allure.step("🔍 Начата проверка файла: " + fileName);
        log.info("Проверка файла: {}", fileName);

        ui.basePage.waitUntilNotBusy2();

        $x("//div[@data-item-marker='" + fileName + "']")
                .shouldBe(visible);

        Allure.step("✅ Файл найден в списке: " + fileName);
        log.info("Файл найден в списке: {}", fileName);
    }

    // =====================================================
    // COMPLETE ATTACHMENTS ACTIVITY
    // =====================================================

    @Step("Завершение активности Прикрепите документы для завершения заявки")
    private void completeAttachmentDocument() {

        ui.dashboardComponent.clickElementDashboardCheck(
                "Прикрепите документы для завершения заявки",
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


}
