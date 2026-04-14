package core.base.common.components;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

/**
 * Component: работа с быстрыми фильтрами и общими lookup-операциями в фильтрах.
 */
public class FiltersComponent extends Components {

    @Step("Удалить быстрый фильтр, если он есть")
    public FiltersComponent removeQuickFilterIfExists() {
        SelenideElement filterContainer = $x("//*[contains(@id,'QuickFilterModuleV2') or contains(@class,'folder-filter-container')]");
        if (filterContainer.exists()) {
            ElementsCollection xButtons = filterContainer.$$x(".//*[contains(@class,'filter-remove-button')]");
            if (!xButtons.isEmpty()) {
                SelenideElement xBtn = xButtons.first().shouldBe(Condition.visible);
                retryClick(xBtn, "Remove filter X");
                xBtn.should(Condition.disappear);
            }
        }
        return this;
    }


    @Step("Применить сохранённое значение в поле фильтра '{fieldMarker}' (apply marker = {applyMarker})")
    public FiltersComponent applySavedValueIntoField(String fieldMarker, String applyMarker, String value) {
        SelenideElement input = $x("//*[@data-item-marker='" + fieldMarker + "']//input").shouldBe(Condition.visible, Condition.enabled);
        retryClick(input, "Input filter " + fieldMarker);
        input.clear();
        input.setValue(value);
        input.shouldHave(Condition.value(value));
        SelenideElement applyBtn = $x("//*[@data-item-marker='" + applyMarker + "']").shouldBe(Condition.visible, Condition.enabled);
        retryClick(applyBtn, "Apply filter " + applyMarker);
        return this;
    }


    //Закрыть фильтр по тегу  06.12.2025
    @Step("Удалить фильтр если он есть")
    public boolean removeFilterIfExists1() {

        // 1) Контейнер быстрых фильтров
        SelenideElement filterContainer =
                $x("//*[contains(@id,'QuickFilterModuleV2') or contains(@class,'folder-filter-container')]")
                        .shouldBe(Condition.visible);

        // 2) Ищем X ТОЛЬКО внутри контейнера!
        ElementsCollection xButtons =
                filterContainer.$$x(".//*[contains(@class,'filter-remove-button')]");

        if (xButtons.isEmpty()) {
            System.out.println("ℹ Фильтр отсутствует");
            return false;
        }

        SelenideElement xBtn = xButtons.first().shouldBe(Condition.visible);

        // 3) Клик по X (обычный → JS)
        try {
            xBtn.click();
        } catch (Exception e) {
            Selenide.executeJavaScript("arguments[0].click();", xBtn);
        }

        // 4) Tooltip "Удалить" если появится
        try {
            SelenideElement deleteBtn = $x("//*[@data-item-marker='Удалить']");
        } catch (Exception ignored) { }

        // 5) Проверяем исчезновение X
        xBtn.should(Condition.disappear);

        System.out.println("✔ Фильтр удалён");
        return true;
    }

    //18.01.2026
    @Step("Удалить фильтр если он есть")
    public boolean removeFilterIfExists() {

        // 1️⃣ Ждём, что секция стабилизировалась
        SelenideElement filterContainer =
                $x("//*[contains(@id,'QuickFilterModuleV2')]")
                        .shouldBe(Condition.visible, Duration.ofSeconds(10));

        // 2️⃣ ЖДЁМ появления ❌ ИЛИ истечения таймаута
        ElementsCollection xButtons =
                filterContainer.$$x(".//*[contains(@class,'filter-remove-button')]");

        long timeoutMs = Duration.ofSeconds(25).toMillis();
        long start = System.currentTimeMillis();

        while (System.currentTimeMillis() - start < timeoutMs) {

            if (!xButtons.isEmpty()) {
                break;
            }

            Selenide.sleep(5000);
        }

        // 3️⃣ Если реально нет фильтра — это уже факт
        if (xButtons.isEmpty()) {
            System.out.println("ℹ Фильтр действительно отсутствует");
            return false;
        }

        // 4️⃣ Безопасный клик по ❌
        SelenideElement xBtn = xButtons.first()
                .shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);

        try {
            xBtn.click();
        } catch (Exception e) {
            Selenide.executeJavaScript("arguments[0].click();", xBtn);
        }

        // 5️⃣ Проверяем, что фильтр исчез
        xBtn.should(Condition.disappear, Duration.ofSeconds(5));

        System.out.println("✔ Фильтр удалён");
        return true;
    }




}
