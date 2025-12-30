package core.base.common;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class HeaderPage {

    //Новый рабочий метод
    @Step("Выход из системы")
    public void logout() {

        System.out.println("➡ Клик по кнопке профиля");
        $x("//*[@data-item-marker='userProfileButton']")
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .click();
        Allure.step("Клик по профилю выполнен");

        System.out.println("➡ Клик по пункту меню 'Выход'");
        $x("//*[@data-item-marker='Выход']")
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .click();
        Allure.step("Клик по 'Выход' выполнен");

        // 🔥 ЛУЧШАЯ И СТАБИЛЬНАЯ ПРОВЕРКА (по DOM реальной страницы)
        $x("//*[@id='loginContainer']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15));

        System.out.println("✔ Logout подтверждён — loginContainer отображается");
        Allure.step("Logout подтверждён — loginContainer отображается");
    }
}
