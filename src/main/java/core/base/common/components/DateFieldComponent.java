package core.base.common.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import core.base.common.utils.FieldValueResolver;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class DateFieldComponent {

    @Step("Устанавливаем дату в поле '{fieldMarker}' значением '{value}'")
    public DateFieldComponent setDateFieldByMarker(String fieldMarker, String value) {
        Logger log = LoggerFactory.getLogger(FieldComponent.class); // если у тебя уже есть log — удали эту строку

        log.info("➡ Начало: установка даты. Маркер='{}', значение='{}'", fieldMarker, value);
        Allure.step("Поиск контейнера по маркеру: " + fieldMarker);

        try {
            // Находим контейнер date-edit по data-item-marker
            SelenideElement container = $x("//*[@data-item-marker='" + fieldMarker + "']")
                    .shouldBe(Condition.visible, Duration.ofSeconds(10));
            Allure.step("Контейнер найден");

            // Внутри находим input и ждём, что он включён/готов
            SelenideElement input = container.$x(".//input")
                    .shouldBe(Condition.enabled, Duration.ofSeconds(10));
            Allure.step("Поле ввода внутри контейнера готово");

            log.info("➡ Активируем поле (клик) и очищаем");
            input.click();
            // более надёжная очистка: CTRL+A + DEL (если clear() нестабилен)
            input.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"));
            input.sendKeys(org.openqa.selenium.Keys.DELETE);
            Allure.step("Поле очищено");

            log.info("➡ Ввод значения '{}' в поле '{}'", value, fieldMarker);
            input.setValue(value);

            log.info("➡ Ожидание применения значения");
            input.shouldHave(Condition.value(value), Duration.ofSeconds(8));
            Allure.step("Значение установлено: " + value);

            log.info("✔ Дата успешно установлена в поле '{}'", fieldMarker);
            return this;

        } catch (Throwable t) {
            log.error("❌ Ошибка при установке даты в поле '{}'. Причина: {}", fieldMarker, t.toString());
            Allure.step("Ошибка при установке даты: " + t.getMessage());
            // Неявно: если у тебя есть attachScreenshot()/attachPageSource() — можно вызвать их здесь.
            throw t;
        }
    }

}
