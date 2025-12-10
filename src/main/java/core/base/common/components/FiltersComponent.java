package core.base.common.components;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

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
}
