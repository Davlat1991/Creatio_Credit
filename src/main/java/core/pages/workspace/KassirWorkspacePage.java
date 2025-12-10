package core.pages.workspace;


import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Condition.visible;

public class KassirWorkspacePage {

    public KassirWorkspacePage openKassovyeOperacii() {
        $x("//div[@id='sectionMenuModule']//div[normalize-space()='Кассовые операции']")
                .scrollIntoView(true)
                .shouldBe(visible)
                .click();
        return this;
    }

    public KassirWorkspacePage openZhurnalOperaciy() {
        $x("//div[@id='sectionMenuModule']//div[normalize-space()='Журнал операций']")
                .scrollIntoView(true)
                .shouldBe(visible)
                .click();
        return this;
    }

    public KassirWorkspacePage openDokumenty() {
        $x("//div[@id='sectionMenuModule']//div[normalize-space()='Документы']")
                .scrollIntoView(true)
                .shouldBe(visible)
                .click();
        return this;
    }
}

