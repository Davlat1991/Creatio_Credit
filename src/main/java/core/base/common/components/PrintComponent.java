package core.base.common.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static core.assertions.FieldAssertions.*;

public class PrintComponent {

    @Step("Выбор пункта печати: '{option}'")
    public void selectPrintOption(String name) {
        try {
            System.out.println("➡ Открываю меню печати");

            // Открываем кнопку ПЕЧАТЬ (PrintButton всегда имеет data-item-marker)
            SelenideElement printBtn =
                    $x("//*[@data-item-marker='PrintButton']")
                            .shouldBe(Condition.visible, Duration.ofSeconds(10));

            executeJavaScript("arguments[0].click();", printBtn);
            Allure.step("Клик по кнопке 'Печать' выполнен");

            // Ищем пункт меню двумя способами (универсально)
            System.out.println("➡ Ищу пункт меню: " + name);

            SelenideElement item =
                    $x("//*[contains(@data-item-marker,'" + name + "') or contains(text(),'" + name + "')]")
                            .shouldBe(Condition.visible, Duration.ofSeconds(10));

            executeJavaScript("arguments[0].click();", item);
            Allure.step("Пункт меню '" + name + "' выбран");

            System.out.println("✔ Печатная форма запрошена: " + name);

        } catch (Throwable t) {
            attachScreenshot();
            attachPageSource();
            attachErrorMessage(t);
            throw t;
        }
    }
}
