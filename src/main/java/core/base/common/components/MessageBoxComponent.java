package core.base.common.components;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import core.pages.credit.ContractCreditApplicationPage;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Компонент обработки системных popup / messagebox Creatio.
 */
public class MessageBoxComponent extends Components {

    /**
     * Основной контейнер messagebox Creatio.
     * Учитывает оба типа DOM: caption + msg-content.
     */
    private SelenideElement getMessageBox() {
        return $x("//div[contains(@class,'messagebox') or contains(@class,'msg-box')]")
                .shouldBe(Condition.visible);
    }

    /**
     * Ждать появления любого messagebox.
     */
    @Step("Ожидать появление системного сообщения")
    public MessageBoxComponent waitAppear() {
        getMessageBox();
        return this;
    }

    /**
     * Проверить, что messagebox содержит текст (например «Нет задолженности»).
     */
    @Step("Проверить, что messagebox содержит текст: '{text}'")
    public MessageBoxComponent validateText(String text) {
        getMessageBox()
                .$x(".//*[contains(text(),'" + text + "')]")
                .shouldBe(Condition.visible);
        return this;
    }

    /**
     * Нажать OK внутри messagebox (частый кейс Creatio).
     */
    @Step("Нажать кнопку OK в messagebox")
    public MessageBoxComponent clickOk() {
        SelenideElement okButton =
                getMessageBox().$x(".//span[normalize-space(text())='OK' or normalize-space(text())='ОК']")
                        .shouldBe(Condition.visible, Condition.enabled);

        retryClick(okButton, "Кнопка OK");

        return this;
    }

    /**
     * Нажать любую кнопку по названию.
     */
    @Step("Нажать кнопку '{buttonName}' в messagebox")
    public MessageBoxComponent clickButton(String buttonName) {
        SelenideElement button =
                getMessageBox().$x(".//span[normalize-space(text())='" + buttonName + "']")
                        .shouldBe(Condition.visible, Condition.enabled);

        retryClick(button, "Кнопка messagebox '" + buttonName + "'");

        return this;
    }

    /**
     * Проверяет исчезновение popup (например после OK).
     */
    @Step("Ожидать закрытие messagebox")
    public MessageBoxComponent waitClose() {
        $x("//div[contains(@class,'messagebox') or contains(@class,'msg-box')]")
                .shouldBe(Condition.disappear);
        return this;
    }

    /**
     * Быстрый метод: подождать текст + нажать OK + дождаться закрытия
     */
    @Step("Подтвердить системное сообщение '{text}' нажатием OK")
    public MessageBoxComponent confirmOk(String text) {
        waitAppear();
        validateText(text);
        clickOk();
        waitClose();
        return this;
    }


    //Миграция

    public MessageBoxComponent shouldSeeModalWithText(String expectedText) {
        $x("//div[contains(@class,'modal') or contains(@class,'dialog') or contains(@class,'message')]"
                + "[contains(., '" + expectedText + "')]")
                .shouldBe(visible, Duration.ofSeconds(50));

        return this;
    }


    //Работает //06.12.2025
    @Step("Проверить открытие модального окна: {title}")
    public MessageBoxComponent shouldBeModalOpened(String title) {

        // Ищем главный контейнер модалки (id оканчивается на -box)
        SelenideElement modal = $x(
                "//*[substring(@id, string-length(@id)-3)='-box']"
        ).shouldBe(visible, Duration.ofSeconds(10));

        // Проверяем заголовок
        modal.$x(".//label[contains(@id,'HeaderCaptionLabel')]")
                .shouldBe(visible)
                .shouldHave(Condition.exactText(title));

        // Проверяем кнопку Подтвердить
        modal.$x(".//span[@data-item-marker='ConfirmButton']")
                .shouldBe(visible)
                .shouldBe(enabled);

        // Проверяем кнопку Отмена
        modal.$x(".//span[@data-item-marker='CancelButton']")
                .shouldBe(visible)
                .shouldBe(enabled);

        return this;
    }


    //РЕКОМЕНДУЕМОЕ ИСПРАВЛЕНИЕ
    //Разделить на 2 метода:
    // public void clickMenuItem(String name) { ... }
    // public void shouldBeOpened(String title) { ... }
    //В PageObject:
    //messages.clickMenuItem("Выдача кредита");
    //messages.shouldBeOpened("Выдача кредита");

    public MessageBoxComponent clickAndCheckModal(String liName) {

        SelenideElement liElement = $x("//li[contains(text(), '" + liName + "')]")
                .shouldBe(visible)
                .shouldBe(enabled);

        liElement.click();

        return this;
    }



}

