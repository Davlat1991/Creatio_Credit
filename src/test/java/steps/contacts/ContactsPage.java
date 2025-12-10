package steps.contacts;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import core.base.BasePage;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Condition.*;

public class ContactsPage extends BasePage {

    // Локатор рабочего места (левое меню)
    private final SelenideElement sidebar =
            $x("//div[@id='sectionMenuModule']//div[contains(@class,'ts-sidebar')]");

    // Универсальный метод выбора пункта меню в разделе
    private SelenideElement menuItem(String name) {
        return sidebar.$x(".//div[normalize-space()='" + name + "']");
    }

    /**
     * Открыть раздел "Контакты"
     */
    public ContactsPage openContactsSection() {
        menuItem("Контакты")
                .scrollIntoView(true)
                .shouldBe(visible)
                .click();
        return this;
    }

    /**
     * Открыть подраздел "Физические лица"
     */
    public ContactsPage openFizLitsoSection() {
        menuItem("Физические лица")
                .scrollIntoView(true)
                .shouldBe(visible)
                .click();
        return this;
    }

    /**
     * Ожидание появления мини-страницы (MiniPage)
     */
    public ContactsPage waitForMiniPage() {
        $x("//*[@data-item-marker='MiniPage']")
                .shouldBe(visible);
        return this;
    }

    /**
     * Ожидание загрузки полной карточки Contacts → EntityLoaded контейнер
     */
    public ContactsPage waitForCardLoaded() {
        $x("//*[@data-item-marker='EntityLoaded']")
                .shouldBe(visible);
        return this;
    }

    /**
     * Проверка, что открыт список Физических лиц (грид загружен)
     */
    public ContactsPage waitForGridLoaded() {
        $x("//div[contains(@id,'Grid')]")
                .shouldBe(visible);
        return this;
    }

    /**
     * Открыть карточку контакта по ФИО
     */
    public ContactsPage openContactCard(String fullName) {
        $x("//div[contains(@class,'grid')]//div[text()='" + fullName + "']")
                .shouldBe(visible)
                .click();
        return this;
    }
    public void clickAddContactButton() {
        $x("//span[@data-item-marker='AddRecordButton']").click();
    }

}


