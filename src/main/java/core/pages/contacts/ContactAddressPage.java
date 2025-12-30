package core.pages.contacts;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class ContactAddressPage {

    @Step("Ожидание загрузки страницы адреса")
    public void waitForAddressPageLoaded() {
        System.out.println("⏳ Ожидание загрузки страницы AddressPageV2...");
        $x("//*[@data-item-marker='ContactAddressPageV2Container']")
                .shouldBe(Condition.visible, Duration.ofSeconds(10));
        System.out.println("✔ Страница AddressPageV2 загружена");
    }
}
