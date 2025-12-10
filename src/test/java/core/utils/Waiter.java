package core.utils;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;

public class Waiter {

    // ===========================
    // 🔥 Creatio Loader (универсальный)
    // ===========================
    public static void waitForLoader() {
        SelenideElement loader =
                $x("//div[contains(@class,'base-loader') or contains(@class,'process-mask')]");
        try {
            loader.shouldBe(Condition.hidden);
        } catch (Exception ignore) {}
    }


    // ===========================
    // 🔍 Ожидание видимости с retry
    // ===========================
    public static void waitForVisible(SelenideElement element) {
        retry(() -> element.shouldBe(Condition.visible));
    }

    // ===========================
    // 🔍 Ожидание кликабельности с retry
    // ===========================
    public static void waitForClickable(SelenideElement element) {
        retry(() -> {
            element.shouldBe(Condition.visible);
            element.shouldBe(Condition.enabled);
        });
    }

    // ===========================
    // 🔍 scrollIntoView с retry
    // ===========================
    public static void scrollIntoView(SelenideElement element) {
        retry(() -> element.scrollIntoView(true));
    }


    // ===========================
    // 🔥 MINI-PAGE (Creatio)
    // ===========================
    public static void waitMiniPageOpen() {
        retry(() ->
                $x("//div[contains(@class,'mini-page')]")
                        .shouldBe(Condition.visible)
        );
    }

    public static void waitMiniPageClose() {
        retry(() ->
                $x("//div[contains(@class,'mini-page')]")
                        .shouldBe(Condition.hidden)
        );
    }


    // ===========================
    // 🔥 LOOKUP
    // ===========================
    public static void waitLookupOpen() {
        retry(() ->
                $x("//div[contains(@class,'listview')]")
                        .shouldBe(Condition.visible)
        );
    }

    public static void waitLookupSelect() {
        retry(() ->
                $x("//div[contains(@class,'lookup-selected') or contains(@class,'listview-selected')]")
                        .shouldBe(Condition.visible)
        );
    }


    // ===========================
    // 🔥 GRID
    // ===========================
    public static void waitGridLoaded() {
        retry(() ->
                $x("(//div[contains(@class,'grid-row')])[1]")
                        .shouldBe(Condition.visible)
        );
    }

    public static void waitGridRowSelected() {
        retry(() ->
                $x("//div[contains(@class,'grid-row') and contains(@class,'grid-row-selected')]")
                        .shouldBe(Condition.visible)
        );
    }


    // ===========================
    // 🔁 UNIVERSAL RETRY PATTERN
    // ===========================
    @FunctionalInterface
    private interface Action {
        void run();
    }

    private static void retry(Action action) {
        for (int i = 0; i < 3; i++) {
            try {
                action.run();
                return;
            } catch (Exception e) {
                sleep(250);
            }
        }
        action.run();
    }


    // ===========================
    // 🕑 SAFE SLEEP
    // ===========================
    public static void sleepSafe(long ms) {
        try {
            sleep(ms);
        } catch (Exception ignore) {}
    }
}

