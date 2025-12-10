package core.config;

import com.codeborne.selenide.Configuration;
import core.config.ConfigProperties;

public class DriverFactory {

    public static void configure() {

        // --- Браузер ---
        Configuration.browser = ConfigProperties.get("browser", "chrome");
        Configuration.browserSize = ConfigProperties.get("browser.size", "1530x970");

        // --- Таймауты ---
        Configuration.timeout = ConfigProperties.getInt("selenide.timeout", 30000);
        Configuration.pageLoadTimeout = ConfigProperties.getInt("selenide.pageLoadTimeout", 60000);

        // --- Скриншоты, логи ---
        Configuration.screenshots = ConfigProperties.getBoolean("screenshots.enabled");
        Configuration.savePageSource = ConfigProperties.getBoolean("save.page.source");
        Configuration.reportsFolder = ConfigProperties.get("screen.result.directory", "target/allure-results");

        // --- Сетевые задержки Creatio ---
        Configuration.pollingInterval = 200;       // дефолт Selenide = 100ms → слишком быстро для Creatio
        Configuration.pageLoadStrategy = "normal"; // Creatio иначе ведёт себя нестабильно

        // --- Отключение GPU и логов в Chrome (оптимизация) ---
        System.setProperty("chromeoptions.args", "--disable-gpu --disable-extensions");

        System.out.println("⚙ Selenide configuration initialized by DriverFactory");
    }
}
