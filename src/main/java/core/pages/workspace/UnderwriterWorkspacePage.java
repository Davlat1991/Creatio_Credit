package core.pages.workspace;


import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Condition.visible;

public class UnderwriterWorkspacePage {

    public UnderwriterWorkspacePage openZayavkiNaRassmotrenie() {
        $x("//div[@id='sectionMenuModule']//div[normalize-space()='Заявки на рассмотрение']")
                .scrollIntoView(true)
                .shouldBe(visible)
                .click();
        return this;
    }

    public UnderwriterWorkspacePage openMonitoring() {
        $x("//div[@id='sectionMenuModule']//div[normalize-space()='Мониторинг']")
                .scrollIntoView(true)
                .shouldBe(visible)
                .click();
        return this;
    }
}
