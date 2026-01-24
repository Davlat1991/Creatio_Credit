package core.utils;

import io.qameta.allure.Allure;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class AllureAttachments {

    private AllureAttachments() {
        // utility
    }

    /** 📄 Текстовое вложение */
    public static void attachText(String name, String content) {
        Allure.addAttachment(name, "text/plain", content);
    }

    /** 🧩 HTML / DOM */
    public static void attachHtml(String name, String html) {
        Allure.addAttachment(name, "text/html", html);
    }

    /** ❌ Stacktrace ошибки */
    public static void attachException(Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        attachText("❌ Exception", sw.toString());
    }
}
