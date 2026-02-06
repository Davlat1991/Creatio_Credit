package flows.credit;

import core.base.UiContext;
import io.qameta.allure.Step;

public class ConsultationStartFlow {

    private final UiContext ui;

    public ConsultationStartFlow(UiContext ui) {
        this.ui = ui;
    }

    @Step("Старт консультации и регистрация продукта")
    public void startConsultation(String consultationThemeDim) {

        ui.basePage.clickButtonByDataItemMaker("Начать консультацию");

        ui.detailPage.openDetailByName("Оформить заявку");

        ui.consultationPanel.registerProductByDIM(consultationThemeDim);

        ui.basePage.waitForPage();
        ui.basePage.closeConsultationPanelIfOpened();
    }
}
