package assertions.credit;

import core.base.UiContext;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class ConsultationAssertions {

    private final UiContext ctx;

    public ConsultationAssertions(UiContext ctx) {
        this.ctx = ctx;
    }

    @Step("Проверить, что консультация успешно открыта")
    public void shouldBeOpened() {
        $x("//*[@data-item-marker='OpportunityPageV2Container']")
                .shouldBe(visible);
    }

    @Step("Проверить, что ФИО клиента заполнены")
    public void clientFioShouldBeFilled() {
        ctx.fieldPage
                .shouldHaveValue("Фамилия", ctx.fieldPage.getValue("Фамилия"))
                .shouldHaveValue("Имя", ctx.fieldPage.getValue("Имя"))
                .shouldHaveValue("Отчество", ctx.fieldPage.getValue("Отчество"));
    }
}
