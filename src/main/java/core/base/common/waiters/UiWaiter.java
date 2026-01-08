package core.base.common.waiters;

import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public final class UiWaiter {

    @Step("Ожидание появления кнопки '{buttonText}'")
    public static void waitForButton(String buttonText, Duration timeout) {

        $x("//span[normalize-space()='" + buttonText + "']")
                .shouldBe(visible, timeout)
                .shouldBe(enabled);
    }

    // ================= BUTTONS =================

    @Step("Ожидание кнопки по тексту '{text}'")
    public static void waitButtonByText(String text, Duration timeout) {
        $x("//span[normalize-space()='" + text + "']")
                .shouldBe(visible, timeout)
                .shouldBe(enabled);
    }

    @Step("Ожидание кнопки по data-item-marker '{marker}'")
    public static void waitButtonByMarker(String marker, Duration timeout) {
        $x("//span[@data-item-marker='" + marker + "']")
                .shouldBe(visible, timeout)
                .shouldBe(enabled);
    }

    // ================= ELEMENTS =================

    @Step("Ожидание элемента '{xpath}'")
    public static void waitVisible(String xpath, Duration timeout) {
        $x(xpath).shouldBe(visible, timeout);
    }

    @Step("Ожидание исчезновения элемента '{xpath}'")
    public static void waitDisappear(String xpath, Duration timeout) {
        $x(xpath).should(disappear, timeout);
    }

    // ================= PAGES =================

    @Step("Ожидание загрузки mini-page")
    public static void waitMiniPage(Duration timeout) {
        $x("//*[contains(@id,'MiniPage') or contains(@class,'mini-page')]")
                .shouldBe(visible, timeout);
    }




}
