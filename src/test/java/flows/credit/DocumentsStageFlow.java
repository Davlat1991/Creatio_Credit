package flows.credit;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import core.base.UiContext;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

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
        uploadGuarantorDocuments();
        uploadPledgerDocuments();
        completeDocumentsActivity();
        verifyCreditDecisionApproved();
        saveApplicationNumber();

    }

    @Step("Documents: загрузка документов поручителя")
    public void uploadGuarantorDocuments() {

        openDocumentsTabAndWait("Документы поручителя");

        uploadFinancialDossierGuarantor();
        uploadClientDossierGuarantor();
        uploadAdditionalClientDossierGuarantor();

    }

    @Step("Documents: загрузка документов залогодателя")
    public void uploadPledgerDocuments() {

        openDocumentsTabAndWait("Документы залогодателя");


        uploadFinancialDossierPledger();
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
    // DOSSIER Borrow
    // =====================================================

    private void uploadFinancialDossier() {

        ui.detailPage.openDetailByName("Финансовое досье");

        startUploadIfNeeded();

        uploadAndValidate(
                "Income.pdf",
                1
        );
    }

    private void uploadClientDossier() {

        ui.detailPage.openDetailByName("Досье клиента");

        uploadAndValidate(
                "Passport.pdf",
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
    // DOSSIER Guarantor
    // =====================================================

    private void uploadFinancialDossierGuarantor() {

        ui.detailPage.openDetailByName("Финансовое досье");
        //ui.detailPage.openDetailByName("DocGuarantorTab", "Финансовое досье");

        startUploadIfNeeded();

        uploadAndValidate(
                "Income.pdf",
                1
        );
    }

    private void uploadClientDossierGuarantor() {

        ui.detailPage.openDetailByName("Досье клиента");

        uploadAndValidate(
                "Passport.pdf",
                2
        );
    }

    private void uploadAdditionalClientDossierGuarantor() {

        uploadAndValidate(
                "ИНН.pdf",
                3
        );
    }

    // =====================================================
    // DOSSIER Guarantor
    // =====================================================

    private void uploadFinancialDossierPledger() {

        openDocumentsTabAndWait("Документы залогодателя");
        startUploadIfNeeded();
        ui.detailPage.openDetailByName("Досье клиента");

        uploadAndValidate(
                "Income.pdf",
                1
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

    private void openGuarantorDocumentsTab() {

        SelenideElement tab = $x("//li[.//span[normalize-space()='Документы поручителя']]")
                .shouldBe(Condition.visible);

        tab.scrollIntoView(true).hover();

        Selenide.executeJavaScript("arguments[0].click();", tab);

        // ждём что вкладка стала активной
        tab.shouldHave(Condition.cssClass("ts-tabpanel-active-item"));

        ui.basePage.waitForPage();
    }

    private void openPledgerDocumentsTab() {

        SelenideElement tab = $x("//li[.//span[normalize-space()='Документы залогодателя']]")
                .shouldBe(Condition.visible);

        tab.scrollIntoView(true).hover();

        Selenide.executeJavaScript("arguments[0].click();", tab);

        tab.shouldHave(Condition.cssClass("ts-tabpanel-active-item"));

        ui.basePage.waitForPage();
    }


    private void openDocumentsTabAndWait1(String tabName) {

        SelenideElement tab = $x("//li[.//span[normalize-space()='" + tabName + "']]")
                .shouldBe(Condition.visible);

        tab.scrollIntoView(true).hover();

        // клик
        Selenide.executeJavaScript("arguments[0].click();", tab);

        // 🔥 1. ждём что вкладка стала активной
        tab.shouldHave(Condition.cssClass("ts-tabpanel-active-item"));

        // 🔥 2. ждём появления контента внутри вкладки (критично)
        $x("//div[contains(@class,'tab-content-container-marker') and not(contains(@style,'display: none'))]")
                .shouldBe(Condition.visible, Duration.ofSeconds(10));

        // 🔥 3. дополнительная стабилизация
        ui.basePage.waitForPage();
    }

    private void openDocumentsTabAndWait2(String tabName) {

        SelenideElement tab = $x("//li[.//span[normalize-space()='" + tabName + "']]")
                .shouldBe(Condition.visible);

        tab.scrollIntoView(true).hover();
        Selenide.executeJavaScript("arguments[0].click();", tab);

        tab.shouldHave(Condition.cssClass("ts-tabpanel-active-item"));

        // 🔥 конкретная вкладка
        if (tabName.equals("Документы поручителя")) {
            $x("//div[@id='DocGuarantorTab' and not(contains(@style,'display: none'))]")
                    .shouldBe(Condition.visible, Duration.ofSeconds(10));
        } else if (tabName.equals("Документы залогодателя")) {
            $x("//div[@id='DocPledgerTab' and not(contains(@style,'display: none'))]")
                    .shouldBe(Condition.visible, Duration.ofSeconds(10));
        }

        ui.basePage.waitForPage();
    }

    private void openDocumentsTabAndWait(String tabName) {

        SelenideElement tab = $x("//li[.//span[normalize-space()='" + tabName + "']]")
                .shouldBe(Condition.visible);

        tab.scrollIntoView(true).hover();
        Selenide.executeJavaScript("arguments[0].click();", tab);

        tab.shouldHave(Condition.cssClass("ts-tabpanel-active-item"));

        // 🔥 ЖДЁМ КОНКРЕТНУЮ ВКЛАДКУ
        if (tabName.equals("Документы поручителя")) {
            $x("//div[@id='DocGuarantorTab' and not(contains(@style,'display: none'))]")
                    .shouldBe(Condition.visible, Duration.ofSeconds(10));
        }

        if (tabName.equals("Документы залогодателя")) {
            $x("//div[@id='DocPledgerTab' and not(contains(@style,'display: none'))]")
                    .shouldBe(Condition.visible, Duration.ofSeconds(10));
        }

        ui.basePage.waitForPage();
    }








}
