package core.utils;

import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LogStep {

    private static final Logger log = LoggerFactory.getLogger("BUSINESS");

    private LogStep() {}

    public static void info(String message) {
        // ✔ Console (через logback)
        log.info(message);

        // ✔ Allure
        Allure.step(message);

        // ✔ Прямой вывод (если вдруг логгер отключён)
        System.out.println(message);
    }

    public static void warn(String message) {
        log.warn(message);
        Allure.step("⚠ " + message);
        System.out.println("⚠ " + message);
    }

    public static void error(String message) {
        log.error(message);
        Allure.step("❌ " + message);
        System.out.println("❌ " + message);
    }
}
