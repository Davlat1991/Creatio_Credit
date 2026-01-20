package core.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {

    private static final Properties PROPERTIES = new Properties();
    private static final String ENV;

    static {
        ENV = System.getProperty("env", "test").toLowerCase();
        String fileName = "env/" + ENV + ".properties";

        try (InputStream input = ConfigProperties.class
                .getClassLoader()
                .getResourceAsStream(fileName)) {

            if (input == null) {
                throw new RuntimeException(
                        "❌ Cannot find config file: " + fileName +
                                "\n👉 Use -Denv=test or -Denv=preprod"
                );
            }

            PROPERTIES.load(input);

            System.out.println("====================================");
            System.out.println("✅ ENVIRONMENT LOADED: " + ENV.toUpperCase());
            System.out.println("📄 CONFIG FILE: " + fileName);
            System.out.println("🌐 BASE URL: " + PROPERTIES.getProperty("base.url"));
            System.out.println("====================================");

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to load environment config", e);
        }
    }

    // ----------------------------
    // Base getters
    // ----------------------------

    public static String get(String key) {
        return System.getProperty(key, PROPERTIES.getProperty(key));
    }

    public static String get(String key, String defaultValue) {
        return System.getProperty(key, PROPERTIES.getProperty(key, defaultValue));
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(get(key, String.valueOf(defaultValue)));
    }

    public static int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(get(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String require(String key) {
        String value = get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("❌ Required property missing: " + key);
        }
        return value;
    }

    // ----------------------------
    // Debug helper
    // ----------------------------

    public static String getEnvironmentName() {
        return ENV.toUpperCase();
    }
}
