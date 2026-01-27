package flows.credit.registration;

import core.base.UiContext;
import io.qameta.allure.Step;

public class RegistrationDocumentsFlow {

    private final UiContext ctx;

    public RegistrationDocumentsFlow(UiContext ctx) {
        this.ctx = ctx;
    }

    @Step("Заполнение документов заемщика")
    public void fillBorrowerDocuments() {

        ctx.lookupComponent
                .setHandBookFieldByValueCheck("Страна рождения", "Точикистон")
                .setFieldByValueCheck("Дата выдачи", "01.01.2020");

        ctx.lookupComponent
                .setFieldByValueCheck("Действителен до", "01.01.2030");

        ctx.gridComponent
                .DoubleclickByDIM("Шиносномаи ЧТ");

        ctx.basePage
                .checkCurrentPage("RegDocumentPageV2Container");

        ctx.lookupComponent
                .setFieldByValueCheck("Дата выдачи", "01.01.2020")
                .setFieldByValueCheck("Действует до", "01.01.2030");

        ctx.contractPage
                .clickButtonByNameCheck("Сохранить");

        ctx.basePage
                .checkCurrentPage("EntityLoaded");
    }
}
