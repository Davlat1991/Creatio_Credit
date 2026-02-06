package flows.common;

import core.base.UiContext;
import io.qameta.allure.Step;

public class NavigationFlow {

    private final UiContext ui;

    public NavigationFlow(UiContext ui) {
        this.ui = ui;
    }

    @Step("Открыть страницу по прямой ссылке")
    public void open(String fullUrl) {
        ui.basePage.openUrl(fullUrl);
        ui.basePage.waitForPage();
    }
}
