package core.pages.credit;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import core.base.common.components.Components;
import io.qameta.allure.Step;
import core.pages.ui.DetailPage;
import core.base.BasePage;

import static com.codeborne.selenide.Selenide.$x;

public class ConsultationPanelPage extends Components {

    public BasePage basePage = new BasePage();
    private final DetailPage detailPage = new DetailPage();

    /**
     * Закрыть message-box, если он появится
     */
    @Step("Закрыть message-box (если есть)")
    public ConsultationPanelPage closeMessageBox() {
        SelenideElement msg = $x("//div[contains(@class,'messagebox-caption')]");
        if (msg.exists()) {
            $x("//span[@data-item-marker='ОК']")
                    .shouldBe(Condition.visible)
                    .click();
        }
        return this;
    }

    /**
     * Фиксато правой панели консультации
     */
    @Step("Зафиксировать панель консультации")
    public ConsultationPanelPage secureCommunicationPanel() {
        SelenideElement fixer = $x("//span[@data-item-marker='right-panel-fixator']")
                .shouldBe(Condition.exist);

        if (!fixer.getAttribute("class").contains("checked")) {
            fixer.scrollIntoView(true).click();
        }
        fixer.shouldHave(Condition.attributeMatching("class", ".*checked.*"));
        return this;
    }

    /**
     * 🔥 Универсальный метод выбора продукта — используется в новом тесте
     */
    @Step("Выбрать продукт по marker: {marker}")
    public ConsultationPanelPage registerProduct(String marker) {

        detailPage.openDetailByName("Оформить заявку");

        SelenideElement product =
                $x("//*[@data-item-marker='" + marker + "']")
                        .shouldBe(Condition.visible, Condition.enabled);

        retryClick(product, "Выбор продукта: " + marker);

        // Проверяем, что мини-форма продукта открылась
        $x("//*[contains(@id,'FinApplicationPage')]")
                .shouldBe(Condition.visible);

        return this;
    }

    /**
     * Старый вариант выбора продукта по имени
     */
    @Step("Выбрать продукт по имени: {name}")
    public ConsultationPanelPage registerProductByName(String name) {

        detailPage.openDetailByName("Оформить заявку");

        $x("//label[normalize-space(.)='" + name + "']")
                .shouldBe(Condition.visible)
                .click();

        return this;
    }

    /**
     * Старый DIM-метод (твой оригинальный)
     */
    @Step("Выбрать продукт по data-item-marker (DIM): {dim}")
    public ConsultationPanelPage registerProductByDIM1(String dim) {

        detailPage.openDetailByName("Оформить заявку");

        $x("//label[@data-item-marker='" + dim + "']")
                .shouldBe(Condition.visible)
                .click();

        return this;
    }


    public ConsultationPanelPage registerProductByDIM(String name) {

        detailPage
                .openDetailByName("Оформить заявку");

        basePage
                .clickElementByTagAndDIM("label", name);

        return this;
    }


}



