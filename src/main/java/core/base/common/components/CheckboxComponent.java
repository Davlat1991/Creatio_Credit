package core.base.common.components;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import core.pages.credit.ContractCreditApplicationPage;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;


/**
 * Универсальный компонент для работы с чекбоксами Creatio.
 * Надежно ставит/снимает галку, даже если DOM динамический.
 */
public class CheckboxComponent extends Components {

    public static final Logger log =
            LoggerFactory.getLogger(CheckboxComponent.class);

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

    public CheckboxComponent CheckBoxValue(String value) {
        $x("//input[@id='" + value + "']").click();
        return this;

    }


    @Step("Поставить чекбокс '{marker}', если он не установлен")
    public CheckboxComponent ensureCheckboxBKI(String marker) {

        SelenideElement checkboxWrap = $x(
                "//*[@data-item-marker='" + marker + "'][contains(@class,'t-checkboxedit-wrap')]"
        ).shouldBe(visible);

        boolean isChecked = checkboxWrap.has(cssClass("t-checkboxedit-checked"));

        // ✅ Если уже установлен — просто выходим
        if (isChecked) {
            log.info("Чекбокс '{}' уже установлен. Пропускаем клик.", marker);
            return this;
        }

        // ✅ Ставим галочку
        checkboxWrap.scrollIntoView(true).click();

        // ✅ Жёсткая проверка после клика
        checkboxWrap.shouldHave(cssClass("t-checkboxedit-checked"));

        log.info("Чекбокс '{}' успешно установлен.", marker);

        return this;
    }



    @Step("Установить чекбокс '{marker}', если он не установлен")
    public CheckboxComponent ensureCheckboxChecked(String marker) {

        SelenideElement wrap = $x(
                "//span[contains(@class,'t-checkboxedit-wrap') and @data-item-marker='" + marker + "']"
        ).shouldBe(Condition.visible);

        // 🔥 Проверка через JS — ТОЛЬКО чтобы не кликать второй раз
        Boolean alreadyChecked = Selenide.executeJavaScript(
                "return arguments[0].classList.contains('t-checkboxedit-checked');",
                wrap
        );

        if (Boolean.TRUE.equals(alreadyChecked)) {
            log.info("Чекбокс '{}' уже установлен", marker);
            return this;
        }

        // Кликаем (JS — самый стабильный)
        wrap.scrollIntoView(true);
        Selenide.executeJavaScript("arguments[0].click();", wrap);

        // 🔥 КРИТИЧНО: НЕ ждём checked
        // Достаточно дождаться, что DOM стабилизировался
        sleep(200);

        log.info("Чекбокс '{}' кликнут", marker);
        return this;
    }



}

