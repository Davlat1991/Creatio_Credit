package core.base.common.components;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Component: Contract details (вклады, поля детали, сохранение значений).
 */
public class ContractDetailsComponent extends Components {

    private String savedValue;

    @Step("Открыть вкладку: {tabName}")
    public ContractDetailsComponent openTab(String tabName, String contentXpath) {
        // Используем ButtonsComponent для клика по вкладке, но даём быстрый helper
        $x("//span[normalize-space()='" + tabName + "']").shouldBe(Condition.visible).click();
        $x(contentXpath).shouldBe(Condition.visible);
        return this;
    }

    @Step("Заполнить поле в детали '{detailMarker}' поле '{fieldMarker}' = '{value}'")
    public ContractDetailsComponent setFieldInDetail(String detailMarker, String fieldMarker, String value) {
        SelenideElement input = $x("//div[@data-item-marker='" + detailMarker + "']" +
                "//div[@data-item-marker='" + fieldMarker + "']//input")
                .shouldBe(Condition.visible, Condition.enabled);
        retryClick(input, "Поле в детали " + fieldMarker);
        input.clear();
        input.setValue(value);
        input.shouldHave(Condition.value(value));
        return this;
    }

    @Step("Сохранить значение поля по marker '{marker}'")
    public ContractDetailsComponent saveValueByMarker(String marker) {
        SelenideElement el = $x("//*[@data-item-marker='" + marker + "']").shouldBe(Condition.visible);
        String tag = el.getTagName();
        if ("input".equals(tag) || "textarea".equals(tag)) {
            savedValue = el.getValue();
        } else {
            savedValue = el.getText();
        }
        return this;
    }

    @Step("Получить сохранённое значение")
    public String getSavedValue() {
        return savedValue;
    }

    @Step("Выбрать тип получения кредита (контейнер) '{value}'")
    public ContractDetailsComponent selectLoadCreditType(String value) {
        // Простой wrapper; если нужен более сложный — расширим
        $x("//*[@id='BnzInputPlanningTypeModalBoxLoadCreditTypeContainer_Control']").shouldBe(Condition.visible).click();
        SelenideElement list = $x("//*[@data-item-marker='LoadCreditType']").shouldBe(Condition.visible);
        SelenideElement opt = list.$x(".//li[@data-item-marker='" + value + "' or normalize-space(.)='" + value + "']").shouldBe(Condition.visible);
        jsClick(opt);
        return this;
    }
}
