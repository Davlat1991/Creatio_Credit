package core.base.common.components;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Component: высокоуровневые действия на Dashboard (Execute, Approve, Reject и т.д.)
 */
public class DashboardActionsComponent extends Components {

    @Step("Нажать действие '{action}' в блоке Dashboard '{blockName}'")
    public DashboardActionsComponent performAction(String blockName, String action) {
        SelenideElement block = $x("//div[.='" + blockName + "']/ancestor::*[contains(@class,'controlgroup') or contains(@class,'card')]")
                .shouldBe(Condition.visible);
        SelenideElement btn = block.$x(".//span[normalize-space(text())='" + action + "']")
                .shouldBe(Condition.visible, Condition.enabled);
        retryClick(btn, "Dashboard action: " + action);
        return this;
    }

    @Step("Нажать действие '{action}' и ждать mini-page")
    public DashboardActionsComponent performActionWaitMiniPage(String blockName, String action) {
        performAction(blockName, action);
        $x("//*[@data-item-marker='MiniPage']").shouldBe(Condition.visible);
        return this;
    }

    public void approve() {

        SelenideElement approveButton = $x("//span[@data-item-marker='Approve']")
                .shouldBe(Condition.visible, Condition.enabled);

        approveButton.click();

        // Ожидаем открытие мини-страницы или модалки (Creatio style)
        $x("//*[contains(@id,'MiniPage') or contains(@class,'mini-page')]")
                .shouldBe(Condition.visible);
    }

    public void issueCredit(String type) {
        // Открываем меню
        SelenideElement menu = $x("//li[@data-item-marker='Выдача кредита']")
                .shouldBe(Condition.visible, Condition.enabled);

        menu.hover();

        // Нажимаем тип выдачи
        SelenideElement option = $x("//li[@data-item-marker='" + type + "']")
                .shouldBe(Condition.visible, Condition.enabled);
        option.click();

        // Проверяем открытие модалки
        $x("//*[@data-item-marker='Выдача кредита']")
                .shouldBe(Condition.visible);
    }
}

