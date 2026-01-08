package steps.contacts;

import io.qameta.allure.Step;
import core.pages.contacts.ContactCardPage;
import core.base.BasePage;
import core.base.common.components.DetailComponent;

public class ContactCardSteps {

    private final ContactCardPage card = new ContactCardPage();
    private final BasePage base = new BasePage();

    @Step("Открыть вкладку '{tabName}' в карточке контакта")
    public ContactCardSteps openTab(String tabName) {
        card.openTab(tabName);
        return this;
    }

   /* @Step("Открыть деталь '{detailName}' в карточке контакта")
    public ContactCardSteps openDetail(String detailName) {
        card.openDetail(detailName);
        return this;
    }

    @Step("Нажать кнопку '{buttonName}' в карточке контакта")
    public ContactCardSteps clickCardButton(String buttonName) {
        base.clickButtonByNameCheck(buttonName);
        return this;
    }

    @Step("Дождаться загрузки карточки контакта")
    public ContactCardSteps waitForCardLoaded() {
        card.waitForCardLoaded();
        return this;
    }

    @Step("Открыть строку '{rowName}' в детали")
    public ContactCardSteps openDetailRow(String rowName) {
        card.openDetailRow(rowName);
        return this;
    }*/

    @Step("Перейти в режим редактирования карточки")
    public ContactCardSteps editCard() {
        base.clickButtonByNameCheck("Изменить");
        card.waitForEditMode();
        return this;
    }

    @Step("Сохранить карточку контакта")
    public ContactCardSteps saveCard() {
        base.clickButtonByNameCheck("Сохранить");
        card.waitForCardLoaded();
        return this;
    }
}
