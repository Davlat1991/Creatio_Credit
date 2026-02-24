package core.base.common.helpers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$x;

public class DomActions {

    public DomActions clickDivbyId(String nameField , String value) {
        $x("//div[@id='" + nameField + "']/input").setValue(value).click();
        return this;
    }


    @Step("Заполнить grid поле с id '{inputId}' значением '{value}'")
    public DomActions setGridInputValue(String inputId, String value) {

        // 1️⃣ Кликаем по ячейке (div вокруг input)
        SelenideElement cell = $x("//*[@id='" + inputId + "']")
                .shouldBe(Condition.exist)
                .scrollIntoView(true);

        // В Creatio сначала нужно активировать поле
        cell.click();

        // 2️⃣ Ждём активного input
        SelenideElement input = $x("//input[@id='" + inputId + "']")
                .shouldBe(Condition.visible, Condition.enabled);

        // 3️⃣ Ввод значения
        input.clear();
        input.setValue(value);

        // 4️⃣ Фиксация значения (обязательно для Creatio)
        input.sendKeys(Keys.TAB);

        return this;
    }


    @Step("Заполнить grid колонку '{columnName}' значением '{value}'")
    public DomActions setGridCellValueByColumn(String columnName, String value) {

        SelenideElement cell = $x("//div[@column-name='" + columnName + "']")
                .shouldBe(Condition.visible)
                .scrollIntoView(true);

        cell.click();

        SelenideElement input = cell.$("input")
                .shouldBe(Condition.visible, Condition.enabled);

        input.clear();
        input.setValue(value);
        input.sendKeys(Keys.TAB);

        return this;
    }
}
