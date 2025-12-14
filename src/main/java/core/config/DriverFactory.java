package core.config;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverFactory {

    public static void configure() {

        System.out.println("⚙ Initializing Selenide configuration (DriverFactory)");

        // ================================
        // 📌 Базовая конфигурация браузера
        // ================================
        Configuration.browser = ConfigProperties.get("browser", "chrome");
        Configuration.browserSize = ConfigProperties.get("browser.size", "1530x970");
        Configuration.headless = ConfigProperties.getBoolean("headless.enabled", false);

        // ================================
        // 📌 Таймауты (адаптированы под Creatio)
        // ================================
        Configuration.timeout = ConfigProperties.getInt("selenide.timeout", 30000);
        Configuration.pageLoadTimeout = ConfigProperties.getInt("selenide.pageLoadTimeout", 60000);
        Configuration.pollingInterval = 200;       // Creatio требует повышенного polling
        Configuration.pageLoadStrategy = "normal"; // Creatio работает нестабильно на other strategies

        // ================================
        // 📌 Аллюр + артефакты
        // ================================
        Configuration.screenshots = ConfigProperties.getBoolean("screenshots.enabled", true);
        Configuration.savePageSource = ConfigProperties.getBoolean("save.page.source", false);
        Configuration.reportsFolder = ConfigProperties.get("screen.result.directory", "target/allure-results");

        // ================================
        // 📌 Chrome Options
        // ================================
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-infobars");
        chromeOptions.addArguments("--disable-dev-shm-usage");

        if (Configuration.headless) {
            chromeOptions.addArguments("--headless=new");
        }

        // ================================
        // 📌 Локальный режим
        // ================================
        if (!ConfigProperties.getBoolean("remote.enabled", false)) {
            DesiredCapabilities localCaps = new DesiredCapabilities();
            localCaps.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

            Configuration.browserCapabilities = localCaps;

            System.out.println("🌐 Driver Mode: LOCAL");
            return;
        }

        // ================================
        // 📌 Remote WebDriver (Selenoid / Jenkins / Grid)
        // ================================
        Configuration.remote = ConfigProperties.get("remote.url");
        DesiredCapabilities remoteCaps = new DesiredCapabilities();

        remoteCaps.setCapability("enableVNC", true);
        remoteCaps.setCapability("enableVideo", ConfigProperties.getBoolean("remote.video.enabled", false));
        remoteCaps.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        Configuration.browserCapabilities = remoteCaps;

        System.out.println("🌐 Driver Mode: REMOTE → " + Configuration.remote);
    }
}
