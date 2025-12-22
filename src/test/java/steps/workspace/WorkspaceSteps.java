package steps.workspace;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import core.pages.ui.DashboardPage;
import core.base.common.components.ButtonsComponent;
import core.base.common.components.GridComponent;

import static com.codeborne.selenide.Selenide.$x;

public class WorkspaceSteps {

    private final DashboardPage page = new DashboardPage();
    private final ButtonsComponent buttons = new ButtonsComponent();
    private final GridComponent grid = new GridComponent();

    SelenideElement
            workPlaceList = $x("//span[@id='menu-workplace-button-menuWrapEl']");

    // Выбор рабочего места и раздела
    public WorkspaceSteps selectWorkPlace(String nameWorkPlace, String sectionMenuModule){
        workPlaceList.click();
        $x("//ul[@data-item-marker='TopWorkplaceMenu']//li[.='" + nameWorkPlace + "']").click();
        $x("//div[@id='sectionMenuModule']//div[contains(@class, 'ts-sidebar')]//div[.=' " + sectionMenuModule + " ']").click();

        return this;
    }


    // ---------------------------------------------------------
    // 1. Открыть рабочее место
    // ---------------------------------------------------------
    @Step("Перейти в рабочее место: {workspace}")
    public WorkspaceSteps openWorkspace(String workspace) {
        page.selectWorkAccess(workspace);
        return this;
    }

    // ---------------------------------------------------------
    // 2. Открыть раздел из левого меню
    // ---------------------------------------------------------
    @Step("Открыть раздел левому меню: {section}")
    public WorkspaceSteps openSection(String section) {
        page.leftSidebarSelectSection(section);
        return this;
    }

    // ---------------------------------------------------------
    // 3. Одним вызовом выбрать рабочее место + раздел
    // ---------------------------------------------------------
    @Step("Перейти: рабочее место '{workspace}' → раздел '{section}'")
    public WorkspaceSteps openWorkspaceAndSection(String workspace, String section) {
        page.selectWorkPlace(workspace, section);
        return this;
    }

    // ---------------------------------------------------------
    // 4. Создать запись через кнопку 'Создать'
    // ---------------------------------------------------------
    @Step("Создать новую запись типа '{recordType}'")
    public WorkspaceSteps createRecord(String recordType) {

        // 1) Клик по кнопке "Создать"
        buttons.clickByName("Создать");

        // 2) Ждём появления списка и нужного типа
        SelenideElement typeOption = $x("//span[normalize-space()='" + recordType + "']")
                .shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);

        // 3) Клик по типу записи (с retry)
        typeOption.scrollIntoView(true).click();

        return this;
    }


    // ---------------------------------------------------------
    // 5. Поиск по тексту (универсальный)
    // ---------------------------------------------------------
    @Step("Выполнить поиск в разделе по тексту: {query}")
    public WorkspaceSteps search(String query) {

        page.search(query);

        return this;
    }

    // ---------------------------------------------------------
    // 6. Открыть запись двойным кликом
    // ---------------------------------------------------------
    @Step("Открыть запись из списка по тексту: {text}")
    public WorkspaceSteps openRecord(String text) {

        grid.doubleClickRowByText(text);

        return this;
    }

    // Выбор рабочего места (работает 18.12.2025)
    public WorkspaceSteps selectWorkAccess(String nameWorkAccess){
        workPlaceList.click();
        $x("//ul[@data-item-marker='TopWorkplaceMenu']//li[.='" + nameWorkAccess + "']").click();

        return this;
    }
}
