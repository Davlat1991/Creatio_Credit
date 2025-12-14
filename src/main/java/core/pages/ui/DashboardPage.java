package core.pages.ui;


import com.codeborne.selenide.*;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class DashboardPage {

    private final SelenideElement workPlaceButton =
            $x("//span[@id='menu-workplace-button-menuWrapEl']");

    // ================================
    // 🔵 Универсальный выбор рабочего места
    // ================================
    @Step("Выбрать рабочее место '{workPlace}' и раздел '{section}'")
    public DashboardPage selectWorkPlace(String workPlace, String section) {

        openWorkPlaceMenu();

        // Выбор рабочего места
        clickVisibleElement(
                "//ul[@data-item-marker='TopWorkplaceMenu']//li[contains(.,'" + workPlace + "')]"
        );

        // Ожидание загрузки левого меню
        Selenide.sleep(300);

        // Выбор раздела в левом меню
        leftSidebarSelectSection(section);

        return this;
    }

    // ================================
    // 🔵 Открыть список рабочих мест
    // ================================
    @Step("Открыть меню рабочих мест")
    public DashboardPage openWorkPlaceMenu() {
        workPlaceButton.shouldBe(enabled, visible).click();
        return this;
    }


    // ================================
    // 🔵 Выбор только рабочего места
    // ================================
    @Step("Переключиться на рабочее место '{workPlace}'")
    public DashboardPage selectWorkAccess(String workPlace) {

        openWorkPlaceMenu();

        clickVisibleElement(
                "//ul[@data-item-marker='TopWorkplaceMenu']//li[contains(.,'" + workPlace + "')]"
        );

        return this;
    }

    // ================================
    // 🔵 Универсальный выбор раздела в левом меню
    // ================================
    @Step("Выбрать раздел '{sectionName}' в левом меню")
    public DashboardPage leftSidebarSelectSection(String sectionName) {

        String locator =
                "//div[@id='sectionMenuModule']//div[contains(@class,'ts-sidebar')]//div[contains(normalize-space(.),'" +
                        sectionName + "')]";

        clickVisibleElement(locator);

        return this;
    }

    // ================================
    // 🔵 Универсальный стабильный клик по XPath
    // ================================
    private void clickVisibleElement(String xpath) {

        SelenideElement element =
                $x(xpath)
                        .shouldBe(visible, enabled)
                        .scrollIntoView(true);

        try {
            element.click();
        } catch (Exception e) {
            // fallback JS click
            Selenide.executeJavaScript("arguments[0].click();", element);
        }
    }


    // ================================
// 🔵 Открыть меню создания записи
// ================================
    @Step("Открыть меню создания записи")
    public DashboardPage openCreateMenu() {

        SelenideElement createButton =
                $x("//span[normalize-space(text())='Создать' or @data-item-marker='CreateButton']")
                        .shouldBe(visible, enabled)
                        .scrollIntoView(true);

        try {
            createButton.click();
        } catch (Exception e) {
            Selenide.executeJavaScript("arguments[0].click();", createButton);
        }

        return this;
    }


    // ================================
// 🔵 Поиск через глобальное поле Creatio
// ================================
    @Step("Выполнить поиск по тексту '{query}'")
    public DashboardPage search(String query) {

        SelenideElement searchField =
                $x("//input[contains(@class,'search-input') or @placeholder='Поиск']")
                        .shouldBe(visible, enabled);

        searchField.clear();
        searchField.setValue(query).pressEnter();

        return this;
    }


    // ================================
// 🔵 Открыть запись из грида по тексту
// ================================
    @Step("Открыть запись по тексту '{text}'")
    public DashboardPage openRecord(String text) {

        SelenideElement row =
                $x("//div[contains(@class,'grid-row')]//*[contains(text(),'" + text + "')]")
                        .shouldBe(visible)
                        .scrollIntoView(true);

        try {
            row.doubleClick();
        } catch (Exception e) {
            Selenide.executeJavaScript("arguments[0].dispatchEvent(new MouseEvent('dblclick', {bubbles:true}))", row);
        }

        return this;
    }

    // =======================================
// 🔥 Новый улучшенный стабильный поиск
// =======================================
    @Step("Поиск '{query}' (улучшенный)")
    public DashboardPage searchImproved(String query) {

        String[] locators = {
                "//input[contains(@class,'search-input')]",
                "//input[@placeholder='Поиск']",
                "//input[contains(@data-item-marker,'Search')]"
        };

        SelenideElement field = null;

        for (String locator : locators) {
            if ($x(locator).exists()) {
                field = $x(locator).shouldBe(visible, enabled);
                break;
            }
        }

        if (field == null) {
            throw new RuntimeException("Поле поиска не найдено!");
        }

        field.clear();
        field.setValue(query);
        field.pressEnter();

        // Creatio иногда грузит результаты с задержкой → обязательный wait
        Selenide.sleep(500);

        return this;
    }

    // =======================================
// 🔥 Ожидание загрузки левого меню
// =======================================
    public DashboardPage waitLeftMenuLoaded() {
        $x("//div[@id='sectionMenuModule']//div[contains(@class,'ts-sidebar')]")
                .shouldBe(visible, Duration.ofSeconds(5));
        return this;
    }

    // =======================================
// 🔥 Улучшенное открытие записи
// =======================================
    @Step("Открыть запись по тексту (улучшено): {text}")
    public DashboardPage openRecordImproved(String text) {

        String xpath = "//div[contains(@class,'grid-row')]//*[contains(text(),'" + text + "')]";

        SelenideElement row = $x(xpath)
                .shouldBe(visible)
                .scrollIntoView(true);

        try {
            row.doubleClick();
        } catch (Exception e) {
            // fallback — Creatio иногда игнорирует dblclick
            Selenide.executeJavaScript(
                    "arguments[0].dispatchEvent(new MouseEvent('dblclick', {bubbles:true}))",
                    row
            );
        }

        return this;
    }

    // =======================================
// 🔥 Улучшенный метод 'Создать'
// =======================================
    @Step("Открыть меню создания записи (улучшено)")
    public DashboardPage openCreateMenuImproved() {

        String[] locators = {
                "//span[normalize-space()='Создать']",
                "//*[@data-item-marker='CreateButton']",
                "//button[contains(@class,'create')]"
        };

        SelenideElement btn = null;

        for (String loc : locators) {
            if ($x(loc).exists()) {
                btn = $x(loc).shouldBe(visible, enabled);
                break;
            }
        }

        if (btn == null) {
            throw new RuntimeException("Кнопка 'Создать' не найдена!");
        }

        try {
            btn.click();
        } catch (Exception e) {
            Selenide.executeJavaScript("arguments[0].click();", btn);
        }

        return this;
    }


}

