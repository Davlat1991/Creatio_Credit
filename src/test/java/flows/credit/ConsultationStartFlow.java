package flows.credit;

import core.base.UiContext;
import io.qameta.allure.Step;

public class ConsultationStartFlow {

    private final UiContext ctx;

    public ConsultationStartFlow(UiContext ctx) {
        this.ctx = ctx;
    }

    @Step("Старт консультации и регистрация продукта")
    public void startConsultation(String consultationThemeDim) {

        ctx.basePage.clickButtonByDataItemMaker("Начать консультацию");

        ctx.detailPage.openDetailByName("Оформить заявку");

        ctx.consultationPanel.registerProductByDIM(consultationThemeDim);

        ctx.basePage.waitForPage();
    }
}
