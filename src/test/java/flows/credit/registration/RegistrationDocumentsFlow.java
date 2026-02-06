package flows.credit.registration;

import core.base.UiContext;
import io.qameta.allure.Step;

public class RegistrationDocumentsFlow {

    private final UiContext ui;

    public RegistrationDocumentsFlow(UiContext ui) {
        this.ui = ui;
    }

    @Step("Заполнение документов заемщика")
    public void fillBorrowerDocuments() {

        ui.lookupComponent
                .setHandBookFieldByValueCheck("Страна рождения", "Точикистон")
                .setFieldByValueCheck("Дата выдачи", "01.01.2020");

        ui.lookupComponent
                .setFieldByValueCheck("Действителен до", "01.01.2030");

        ui.gridComponent
                .DoubleclickByDIM("Шиносномаи ЧТ");

        ui.basePage
                .checkCurrentPage("RegDocumentPageV2Container");

        ui.lookupComponent
                .setFieldByValueCheck("Дата выдачи", "01.01.2020")
                .setFieldByValueCheck("Действует до", "01.01.2030");

        ui.contractPage
                .clickButtonByNameCheck("Сохранить");

        ui.basePage
                .checkCurrentPage("EntityLoaded");
    }
}
