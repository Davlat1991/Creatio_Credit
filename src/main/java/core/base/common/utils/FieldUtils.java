package core.base.common.utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import java.util.concurrent.Callable;
import static com.codeborne.selenide.Selenide.$x;

public class FieldUtils {

    private static final int DEFAULT_RETRY = 5;

    /**
     * Найти поле по видимому label и вернуть его значение.
     * Работает для input/textarea (value) и для div/span (text).
     */
    @Step("Получить значение поля по label: '{label}'")
    public static String getValueByLabel(String label) {
        return WaitUtils.retry((Callable<String>) () -> {

            // 1) Находим видимый label по точному (normalize-space) совпадению
            SelenideElement labelEl = $x("//label[normalize-space()='" + label + "']")
                    .shouldBe(Condition.exist);

            // 2) Попытки найти связанный input/textarea рядом с label:
            //    a) input с data-item-marker внутри контейнера лейбла
            SelenideElement input = null;

            // 2.a — input внутри ближайшего контейнера (обычный Creatio layout)
            try {
                input = labelEl.closest("div")
                        .$x(".//input[not(@type='hidden') or @type='text']")
                        .shouldBe(Condition.exist);
            } catch (Throwable ignored) {}

            // 2.b — input с data-item-marker=some (если не в контейнере)
            if (input == null) {
                try {
                    input = $x("//label[normalize-space()='" + label + "']/following::input[1]")
                            .shouldBe(Condition.exist);
                } catch (Throwable ignored) {}
            }

            // 2.c — input привязанный через id/for (редкий случай)
            if (input == null) {
                try {
                    String forAttr = labelEl.getAttribute("for");
                    if (forAttr != null && !forAttr.isBlank()) {
                        input = $x("//*[@id='" + forAttr + "']").shouldBe(Condition.exist);
                    }
                } catch (Throwable ignored) {}
            }

            // 2.d — если все input не найдены — пробуем соседний span/div (read-only render)
            SelenideElement valueElement = input;
            if (valueElement == null) {
                try {
                    valueElement = labelEl.closest("div")
                            .$x(".//div[contains(@class,'control-wrap') or contains(@class,'base-edit-text') or .//span]")
                            .shouldBe(Condition.exist);
                } catch (Throwable ignored) {}
            }

            if (valueElement == null) {
                // финальный fallback — простой following::* (правая сторона)
                try {
                    valueElement = $x("//label[normalize-space()='" + label + "']/following::*[1]")
                            .shouldBe(Condition.exist);
                } catch (Throwable t) {
                    throw new RuntimeException("Не найдено поле для label='" + label + "'");
                }
            }

            // 3) Прочитать значение в зависимости от тега
            String tag = valueElement.getTagName().toLowerCase();
            String value;
            if ("input".equals(tag) || "textarea".equals(tag)) {
                value = valueElement.getValue();
            } else {
                value = valueElement.getText();
            }

            // 4) Если значение пустое — возможно, скрытый список или placeholder — пробуем привести focus и взять value через JS
            if (value == null || value.isBlank()) {
                try {
                    // попытка через data-item-marker: если есть, читаем input по нему
                    String marker = valueElement.getAttribute("data-item-marker");
                    if (marker != null && !marker.isBlank()) {
                        SelenideElement byMarker = $x("//*[@data-item-marker='" + marker + "']");
                        if (byMarker.exists()) {
                            if ("input".equals(byMarker.getTagName())) {
                                value = byMarker.getValue();
                            } else {
                                value = byMarker.getText();
                            }
                        }
                    }
                } catch (Throwable ignored) {}
            }

            // 5) финальная проверка
            if (value == null || value.isBlank()) {
                throw new RuntimeException("Пустое значение для label='" + label + "'");
            }

            return value;

        }, DEFAULT_RETRY);
    }

    public static String getValueByMarker(String marker) {

        // Находим input внутри контейнера data-item-marker
        SelenideElement input = $x("//*[@data-item-marker='" + marker + "']//input")
                .shouldBe(Condition.exist)
                .shouldBe(Condition.visible);

        // Читаем значение через getAttribute("value") — НАДЕЖНЕЙШЕЕ для Creatio
        String value = input.getAttribute("value");

        if (value == null || value.isBlank()) {
            throw new RuntimeException("Поле с marker=" + marker + " найдено, но value пустое!");
        }

        return value;
    }


    @Step("Получить значение поля по label/data-item-marker '{label}'")
    public static String getUniversalValue(String label) {

        return WaitUtils.retry(() -> {

            // 1. Ищем label сначала по data-item-marker (надежный путь)
            SelenideElement labelEl = $x("//*[@data-item-marker[contains(., '" + label + "')]]");

            if (!labelEl.exists()) {
                // 2. Если не нашли - ищем по тексту как fallback
                labelEl = $x("//label[contains(normalize-space(), '" + label + "')]");
            }

            labelEl.shouldBe(Condition.exist).scrollIntoView(true);

            // 3. Ищем контейнер поля (ближайший control-wrap)
            SelenideElement container = labelEl.closest("div").parent();

            // 4. Считываем lookup <a> (read-only lookup → твой случай!)
            try {
                SelenideElement linkValue = container.$("a.base-edit-link");
                if (linkValue.exists()) {
                    String text = linkValue.getText();
                    if (text != null && !text.isBlank()) return text.trim();
                }
            } catch (Throwable ignored) {}

            // 5. Считываем input.value
            try {
                SelenideElement input = container.$("input");
                if (input.exists()) {
                    String val = input.getAttribute("value");
                    if (val != null && !val.isBlank()) return val;
                }
            } catch (Throwable ignored) {}

            // 6. Считываем div/span текст
            try {
                SelenideElement divSpan = container.$x(".//span | .//div");
                if (divSpan.exists()) {
                    String txt = divSpan.getText();
                    if (txt != null && !txt.isBlank()) return txt.trim();
                }
            } catch (Throwable ignored) {}

            throw new RuntimeException("Не удалось получить значение поля '" + label + "'");

        }, 5);
    }





}
