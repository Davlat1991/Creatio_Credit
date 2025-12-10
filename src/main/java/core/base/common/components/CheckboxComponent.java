package core.base.common.components;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Универсальный компонент для работы с чекбоксами Creatio.
 * Надежно ставит/снимает галку, даже если DOM динамический.
 */
public class CheckboxComponent extends Components {

    /**
     * Находит чекбокс по label.
     */
    private SelenideElement findCheckbox(String label) {
        return $x("//label[normalize-space(text())='" + label + "']/../..//span[contains(@class,'checkbox')]");
    }

    /**
     * Возвращает true/false на основе класса чекбокса.
     */
    public boolean isChecked(String label) {
        return findCheckbox(label)
                .shouldBe(visible)
                .getAttribute("class")
                .contains("checked");
    }

    /**
     * СТАБИЛЬНО ставит галочку (если не стоит).
     */
    @Step("Установить чекбокс '{label}'")
    public CheckboxComponent check(String label) {
        SelenideElement checkbox = findCheckbox(label);

        if (!isChecked(label)) {
            click(checkbox, "Установить чекбокс '" + label + "'");
        }

        checkbox.shouldHave(Condition.attributeMatching("class", ".*checked.*"));

        return this;
    }

    /**
     * СТАБИЛЬНО снимает галочку (если стоит).
     */
    @Step("Снять чекбокс '{label}'")
    public CheckboxComponent uncheck(String label) {
        SelenideElement checkbox = findCheckbox(label);

        if (isChecked(label)) {
            click(checkbox, "Снять чекбокс '" + label + "'");
        }

        checkbox.shouldNotHave(Condition.attributeMatching("class", ".*checked.*"));

        return this;
    }

    /**
     * Принудительно выставляет нужное состояние (true/false)
     */
    @Step("Установить значение чекбокса '{label}' → {value}")
    public CheckboxComponent setCheckbox(String label, boolean value) {
        if (value) {
            check(label);
        } else {
            uncheck(label);
        }
        return this;
    }

    /**
     * Чекбокс по data-item-marker (часто в деталях и mini-pages)
     */
    @Step("Клик по чекбоксу data-item-marker='{marker}'")
    public CheckboxComponent clickByMarker(String marker) {
        SelenideElement checkbox = $x("//span[@data-item-marker='" + marker + "']");
        click(checkbox, "Клик по чекбоксу с marker=" + marker);
        return this;
    }

    @Step("Установить чекбокс '{marker}' в состояние Выбрано")
    public CheckboxComponent setCheckedDIM(String marker) {

        SelenideElement checkbox = $x("//*[@data-item-marker='" + marker + "']")
                .shouldBe(visible);

        // Проверяем состояние
        boolean checked = checkbox.getAttribute("class").contains("checked");

        if (!checked) {
            checkbox.click();
        }

        checkbox.shouldHave(Condition.attributeMatching("class", ".*checked.*"));

        return this;
    }

    @Step("Установить чекбокс '{marker}'")
    public CheckboxComponent setChecked(String marker) {
        SelenideElement cb = find(marker);
        if (!cb.$("input").isSelected()) {
            retryClick(cb, "Set checkbox " + marker);
        }
        return this;
    }

    private SelenideElement find(String markerOrLabel) {
        return $x("//*[( @data-item-marker='" + markerOrLabel + "' ) or (normalize-space(text())='" + markerOrLabel + "')]/ancestor::*[contains(@class,'checkbox')]")
                .shouldBe(visible);
    }



    @Step("Снять чекбокс '{marker}'")
    public CheckboxComponent setUnchecked(String marker) {
        SelenideElement cb = find(marker);
        if (cb.$("input").isSelected()) {
            retryClick(cb, "Unset checkbox " + marker);
        }
        return this;
    }

}

