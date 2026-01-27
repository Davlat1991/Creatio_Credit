package flows.common;

import core.base.UiContext;
import io.qameta.allure.Step;

public class NavigationFlow {

    private final UiContext ctx;

    public NavigationFlow(UiContext ctx) {
        this.ctx = ctx;
    }

    @Step("Открыть страницу по прямой ссылке")
    public void open(String fullUrl) {
        ctx.basePage.openUrl(fullUrl);
        ctx.basePage.waitForPage();
    }
}
