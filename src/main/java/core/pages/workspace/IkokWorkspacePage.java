package core.pages.workspace;


import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Condition.visible;

public class IkokWorkspacePage {

    public IkokWorkspacePage openZayavki() {
        $x("//div[@id='sectionMenuModule']//div[normalize-space()='Заявки']")
                .scrollIntoView(true)
                .shouldBe(visible)
                .click();
        return this;
    }

    public IkokWorkspacePage openKlienty() {
        $x("//div[@id='sectionMenuModule']//div[normalize-space()='Клиенты']")
                .scrollIntoView(true)
                .shouldBe(visible)
                .click();
        return this;
    }

    public IkokWorkspacePage openMonitoring() {
        $x("//div[@id='sectionMenuModule']//div[normalize-space()='Мониторинг']")
                .scrollIntoView(true)
                .shouldBe(visible)
                .click();
        return this;
    }
}

