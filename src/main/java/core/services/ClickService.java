package core.services;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.*;

public class ClickService {

    private static final Logger log = LoggerFactory.getLogger(ClickService.class);

    private static final String LOADERS =
            ".ts-loader, .ui-loader, .process-indicator, .loading-indicator, .mask";

    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    // ✅ Основной метод клика
    @Step("Клик по элементу")
    public void click(SelenideElement el) {
        waitUntilNotBusy();
        waitClickable(el);
        scrollIntoViewCenter(el);

        try {
            el.click();
            log.info("Клик выполнен обычным способом");
            return;
        } catch (Exception e) {
            log.warn("Обычный клик не сработал: {}. Переход к JS клику", e.getMessage());
        }

        jsClick(el);
    }

    // ✅ Явный JS-клик (для особых случаев)
    @Step("JS-клик по элементу")
    public void jsClick(SelenideElement el) {
        executeJavaScript("arguments[0].click();", el);
        log.info("Клик выполнен через JavaScript");
    }

    // ✅ Double click с fallback
    @Step("Двойной клик по элементу")
    public void doubleClick(SelenideElement el) {
        waitClickable(el);
        scrollIntoViewCenter(el);
        try {
            el.doubleClick();
            log.info("Двойной клик выполнен обычным способом");
        } catch (Exception e) {
            log.warn("Двойной клик не сработал: {}. Переход к JS клику", e.getMessage());
            jsClick(el);
        }
    }

    // ✅ Ожидание готовности элемента
    public void waitClickable(SelenideElement el) {
        el.shouldBe(Condition.visible, TIMEOUT)
                .shouldBe(Condition.enabled, TIMEOUT);
    }

    // ✅ Ожидание исчезновения спиннеров Creatio
    public void waitUntilNotBusy() {
        $$(LOADERS)
                .filter(Condition.visible)
                .shouldHave(size(0), TIMEOUT);
    }

    // ✅ Скролл к центру (стабильнее под Creatio чем scrollIntoView(true))
    public void scrollIntoViewCenter(SelenideElement el) {
        executeJavaScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                el
        );
    }
}
