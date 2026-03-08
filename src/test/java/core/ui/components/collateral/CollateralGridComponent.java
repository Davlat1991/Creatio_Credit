package core.ui.components.collateral;

import com.codeborne.selenide.Condition;
import core.base.UiContext;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class CollateralGridComponent {

    private final UiContext ui;

    public CollateralGridComponent(UiContext ui) {
        this.ui = ui;
    }

    @Step("Добавить запись залога")
    public void addCollateral() {
        ui.basePage
                .clickButtonById("FinAppPledgeDetailAddRecordButtonButton-imageEl")
                .clickElementByTagAndDIM("div", "PropertyData");
    }

    @Step("Открыть лупу выбора имущества")
    public void openPropertyLookup() {
        ui.basePage
                .clickLookupIconByInputId("FinAppPledgeDetailPropertyDataLookupEdit-el");
    }

    @Step("Добавить залоговую ценность")
    public void addCollateralValue() {
        ui.basePage
                .clickButtonById("EshCollateralValuesDetailAddRecordButtonButton-imageEl");
    }

    //Новый метод 27.02.2026 Статус:
    @Step("Проверка загрузки страницы")
    public void waitForPageLoaded() {

        $("[data-item-marker='EntityLoaded']")
                .shouldBe(Condition.visible);
    }

}
