package core.pages.workspace;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Condition.visible;

public class RetailManagerWorkspacePage {

    public RetailManagerWorkspacePage openConsultations() {
        $x("//div[@id='sectionMenuModule']//div[normalize-space()='Консультации']")
                .scrollIntoView(true)
                .shouldBe(visible)
                .click();
        return this;
    }

    public RetailManagerWorkspacePage openCreditApplications() {
        $x("//div[@id='sectionMenuModule']//div[normalize-space()='Кредитные заявки']")
                .scrollIntoView(true)
                .shouldBe(visible)
                .click();
        return this;
    }

    public RetailManagerWorkspacePage openKlienty() {
        $x("//div[@id='sectionMenuModule']//div[normalize-space()='Клиенты']")
                .scrollIntoView(true)
                .shouldBe(visible)
                .click();
        return this;
    }
}

