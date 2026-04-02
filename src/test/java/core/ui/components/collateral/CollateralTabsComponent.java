package core.ui.components.collateral;

import com.codeborne.selenide.SelenideElement;
import core.base.UiContext;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CollateralTabsComponent {

    private final UiContext ui;

    public CollateralTabsComponent(UiContext ui) {
        this.ui = ui;
    }

    @Step("Открыть вкладку «Обеспечение»")
    public void openCollateralTab() {

        // 🔥 ЖДЁМ ПОКА ВКЛАДКИ ВООБЩЕ ПРОГРУЗЯТСЯ
        $x("//div[@id='FinApplicationPageTabsContainerContainer']")
                .shouldBe(visible);
       refresh();
        ui.buttonsComponent.clickButtonByContainName("Обеспечение");

    }


    @Step("Открыть вкладку «Обеспечение»")
    public void openCollateralTab1() {

        for (int i = 0; i < 3; i++) {
            try {

                // 2️⃣ жёсткий клик
                SelenideElement tab = $x("//li//span[normalize-space()='Обеспечение']")
                        .shouldBe(visible);

                executeJavaScript("arguments[0].click();", tab);

                // 🔥 3️⃣ ВАЖНО — ЖДЁМ (а не проверяем)
                $x("//span[contains(text(),'Залоги')]")
                        .shouldBe(visible, Duration.ofSeconds(5));

                return;

            } catch (Exception e) {
                System.out.println("Retry click Обеспечение: " + (i + 1));
            }

            sleep(500);
        }

        throw new RuntimeException("Не удалось открыть вкладку 'Обеспечение'");
    }
}
