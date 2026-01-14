package flows.common;

import core.base.TestContext;
import io.qameta.allure.Step;

public class NavigationFlow {

    private final TestContext ctx;

    public NavigationFlow(TestContext ctx) {
        this.ctx = ctx;
    }

    @Step("Открыть страницу по прямой ссылке")
    public void open(String fullUrl) {
        ctx.basePage.openUrl(fullUrl);
        ctx.basePage.waitForPage();
    }
}
