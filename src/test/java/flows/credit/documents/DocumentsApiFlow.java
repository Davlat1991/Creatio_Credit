package flows.credit.documents;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import core.api.files.FileUploadApiHelper;
import core.base.UiContext;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.refresh;

public class DocumentsApiFlow {

    private final UiContext ui;

    public DocumentsApiFlow(UiContext ui) {
        this.ui = ui;
    }

    // =====================================================
    // MAIN FLOW
    // =====================================================

    @Step("Загрузка документов через API")
    public void uploadAllDocumentsViaApi() {

        openDocumentsTab();
        uploadBorrower();
        //uploadGuarantor();
        //uploadPledger();
        //refreshAndWait();
       // completeDocumentsActivity();
    }

    private void uploadBorrower() {

        openTab("Документы заемщика");
        prepareBorrowerDetails();

        DocumentUploadMap uploadMap = new DocumentUploadMap().build(0);

        upload("Income.pdf",   uploadMap.get("Income"));
        upload("Passport.pdf", uploadMap.get("Passport"));
        upload("INN.pdf",      uploadMap.get("INN"));
    }

    private void uploadGuarantor() {
        openTab("Документы поручителя");
        prepareGuarantorDetails();

        DocumentUploadMap uploadMap = new DocumentUploadMap().build(3);

        upload("Income.pdf",   uploadMap.get("Income"));
        upload("Passport.pdf", uploadMap.get("Passport"));
        upload("INN.pdf",      uploadMap.get("INN"));
    }

    private void uploadPledger() {
        openTab("Документы залогодателя");
        preparePledgerDetails();

        // offset=6, только 1 документ
        DocumentUploadMap uploadMap = new DocumentUploadMap()
                .build(6, "Passport");

        upload("Passport.pdf", uploadMap.get("Passport"));
    }


    private void prepareBorrowerDetails() {
        ui.detailPage.openDetailStable("Финансовое досье");
        ui.detailPage.openDetailStable("Досье клиента");
        ui.basePage.waitForPage();
    }

    private void prepareGuarantorDetails() {
        ui.detailPage.openDetailStable("Финансовое досье");
        ui.detailPage.openDetailStable("Досье клиента");
        ui.basePage.waitForPage();
    }

    private void preparePledgerDetails() {
        ui.detailPage.openDetailStable("Досье клиента");
        ui.basePage.waitForPage();
    }


    private void upload(String fileName, String parent) {
        if (parent == null) {
            throw new RuntimeException("❌ parentColumnValue null для файла: " + fileName);
        }
        FileUploadApiHelper.uploadFile(fileName, parent);
    }

    private void openTab(String name) {
        var tab = $x("//li[.//span[normalize-space()='" + name + "']]");
        tab.shouldBe(Condition.visible).click();
        tab.shouldHave(Condition.cssClass("ts-tabpanel-active-item"));
    }

    private void refreshAndWait() {
        refresh();
        ui.basePage.waitForPage();
    }

    private void openDocumentsTab() {
        ui.contractPage.legacyFiles().clickButtonByContainName("Документы");
        ui.basePage.waitForPage();
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
}