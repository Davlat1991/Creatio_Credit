package core.base.common.components;


import com.codeborne.selenide.*;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Универсальный компонент для работы с Dashboard (бизнес-процессы Creatio).
 * Поддерживает действия Execute/Approve/Reject, ожидание блоков и текстов.
 */
public class DashboardComponent extends Components {

    /**
     * Находим основной контейнер Dashboard.
     * Обычно он один и всегда содержит бизнес-панели этапов.
     */
    private SelenideElement getDashboard() {
        return $x("//*[@data-item-marker='Dashboard']")
                .shouldBe(visible);
    }

    /**
     * Ожидаем появления блока процесса по тексту.
     * Например: "Проверка клиента", "Проверка решения КК", "Информирование клиента".
     */
    @Step("Ожидать появление блока процесса '{blockName}'")
    public DashboardComponent waitProcessBlock(String blockName) {

        getDashboard().$x(".//*[contains(text(),'" + blockName + "')]")
                .shouldBe(visible);

        return this;
    }

    /**
     * Нажимаем кнопку действия внутри блока процесса:
     * Execute / Approve / Reject / Завершить / Выполнить.
     */
    @Step("Нажать кнопку действия '{actionButton}' в блоке '{blockName}'")
    public DashboardComponent clickAction(String blockName, String actionButton) {

        SelenideElement dashboard = getDashboard();

        // Находим блок
        SelenideElement block = dashboard.$x(
                ".//*[contains(text(),'" + blockName + "')]/ancestor::*[contains(@class,'controlgroup')]"
        ).shouldBe(visible);

        // Находим кнопку
        SelenideElement button = block.$x(
                ".//span[contains(text(),'" + actionButton + "')]"
        ).shouldBe(visible, enabled);

        retryClick(button, "Кнопка Dashboard '" + actionButton + "'");

        return this;
    }

    /**
     * Ожидание текста, который означает, что требуется MiniPage (например "Укажите дату" или "Добавьте и заполните анкеты")
     */
    @Step("Ожидать появления текста '{text}' в Dashboard")
    public DashboardComponent waitText(String text) {

        getDashboard().$x(".//*[contains(text(),'" + text + "')]")
                .shouldBe(visible);

        return this;
    }

    /**
     * Клик по кнопке в Dashboard, когда ожидается открытие MiniPage
     */
    @Step("Выполнить действие '{action}' для '{blockName}' с ожиданием MiniPage")
    public DashboardComponent clickActionWaitMiniPage(String blockName, String action) {

        clickAction(blockName, action);

        // MiniPage появится — его компонент сам обработает появление
        $x("//*[@data-item-marker='MiniPage']").shouldBe(visible);

        return this;
    }

    @Step("Перейти на стадию процесса '{stageName}'")
    public DashboardComponent selectStage(String stageName) {

        SelenideElement stage = getDashboard().$x(
                ".//*[contains(@class,'t-title') or contains(@class,'step-title')][contains(text(),'" + stageName + "')]"
        ).shouldBe(visible, enabled);

        retryClick(stage, "Стадия процесса '" + stageName + "'");

        return this;
    }

}
