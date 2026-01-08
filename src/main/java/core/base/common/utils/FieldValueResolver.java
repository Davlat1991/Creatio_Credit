package core.base.common.utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import core.base.common.components.FieldComponent;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Configuration.timeout;
import static com.codeborne.selenide.Selenide.$x;
import static com.google.common.net.HostSpecifier.isValid;
import static core.base.common.components.FieldComponent.findFieldByLabel;

public class FieldValueResolver {

    @Step("Получить непустое значение поля '{fieldName}'")
    public String getNonEmptyValue(String fieldName) {
        SelenideElement field = findFieldByLabel(fieldName)
                .shouldBe(Condition.visible);

        long end = System.currentTimeMillis() + timeout;
        String current = null;

        while (System.currentTimeMillis() < end) {
            try {
                current = field.getAttribute("value");
                if (isValid(current)) return current;

                try {
                    current = field.val();
                    if (isValid(current)) return current;
                } catch (Throwable ignored) {}

                current = field.getAttribute("data-value");
                if (isValid(current)) return current;

                current = field.getText();
                if (isValid(current)) return current;

                SelenideElement inner = field.closest("div")
                        .$x(".//input[not(@type='hidden')]");
                if (inner.exists()) {
                    current = inner.getAttribute("value");
                    if (isValid(current)) return current;

                    try {
                        current = inner.val();
                        if (isValid(current)) return current;
                    } catch (Throwable ignored) {}
                }
            } catch (Throwable ignored) {}

            Selenide.sleep(150);
        }

        throw new AssertionError(
                "Поле '" + fieldName + "' пустое. Значение не удалось получить."
        );
    }


    @Step("Получить значение поля '{fieldName}' любым возможным способом")
    public String getFieldValueSmart(String fieldName) {

        SelenideElement field = findFieldByLabel(fieldName)
                .shouldBe(Condition.visible);

        long end = System.currentTimeMillis() + timeout;
        String current = null;

        while (System.currentTimeMillis() < end) {

            try {
                // 1️⃣ Стандартный value=""
                current = field.getAttribute("value");
                if (isValid(current)) return current;

                // 2️⃣ val() (selenide)
                try {
                    current = field.val();
                    if (isValid(current)) return current;
                } catch (Throwable ignored) {}

                // 3️⃣ Lookup, dropdown: aria-label
                current = field.getAttribute("aria-label");
                if (isValid(current)) return current;

                // 4️⃣ Display value (очень частый вариант в Creatio)
                current = field.getAttribute("data-display-value");
                if (isValid(current)) return current;

                // 5️⃣ Lookup: title (отображаемое значение)
                current = field.getAttribute("title");
                if (isValid(current)) return current;

                // 6️⃣ Вложенный lookup текст
                SelenideElement lookupSpan = field.closest("div")
                        .$x(".//span[contains(@class,'lookup-edit')]");

                if (lookupSpan.exists()) {
                    current = lookupSpan.getText();
                    if (isValid(current)) return current;
                }

                // 7️⃣ Любой вложенный input (даты, маски, lookup)
                SelenideElement innerInput = field.closest("div")
                        .$x(".//input[not(@type='hidden')]");

                if (innerInput.exists()) {
                    current = innerInput.getAttribute("value");
                    if (isValid(current)) return current;

                    try {
                        current = innerInput.val();
                        if (isValid(current)) return current;
                    } catch (Throwable ignored) {}

                    current = innerInput.getAttribute("data-display-value");
                    if (isValid(current)) return current;
                }

                // 8️⃣ Поле может быть textarea
                SelenideElement textarea = field.closest("div")
                        .$x(".//textarea");

                if (textarea.exists()) {
                    current = textarea.val();
                    if (isValid(current)) return current;

                    current = textarea.getText();
                    if (isValid(current)) return current;
                }

                // 9️⃣ Иногда Creatio пишет текст просто внутрь div/span
                current = field.getText();
                if (isValid(current)) return current;

            } catch (Throwable ignored) {}

            Selenide.sleep(150);
        }

        throw new AssertionError("Поле '" + fieldName + "' не содержит значения после ожидания.");
    }





}
