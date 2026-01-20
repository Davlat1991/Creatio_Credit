package flows.credit.registration;

import core.base.TestContext;
import io.qameta.allure.Step;

public class RegistrationAddressFlow {

    private final TestContext ctx;

    public RegistrationAddressFlow(TestContext ctx) {
        this.ctx = ctx;
    }

    @Step("Заполнение адресов")
    public void fillAddresses() {

        // Регистрация
        ctx.lookupComponent.selectDropdownValueWithCheck("BnzAffiliation", "Мобильный");
        ctx.buttonsComponent.doubleclickButtonByName("Регистрация");
        ctx.contactAddressPage.waitForAddressPageLoaded();

        ctx.dateFieldComponent.setDateFieldByMarker("BnzRegistrationDate", "01.01.2020");
        ctx.contractPage.clickButtonByNameCheck("Сохранить");

        ctx.basePage.waitForPage();

        // Фактический
        ctx.lookupComponent.setHandBookFieldByValueCheck("Тип клиента", "Нав");
        ctx.buttonsComponent.doubleclickButtonByName("Фактический");
        ctx.contactAddressPage.waitForAddressPageLoaded();

        ctx.dateFieldComponent.setDateFieldByMarker("BnzRegistrationDate", "01.01.2020");
        ctx.contractPage.clickButtonByNameCheck("Сохранить");
    }
}
