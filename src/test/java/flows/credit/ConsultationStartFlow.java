package flows.credit;

import core.base.TestContext;
import io.qameta.allure.Step;

public class ConsultationStartFlow {

    private final TestContext ctx;

    public ConsultationStartFlow(TestContext ctx) {
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
