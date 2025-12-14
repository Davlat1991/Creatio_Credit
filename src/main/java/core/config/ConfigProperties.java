package core.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {

    private static final Properties properties = new Properties();

    static {
        loadEnvironment();
    }

    private static void loadEnvironment() {

        // --------------------------
        // 1. Определяем какой env файл грузить
        // --------------------------
        String env = System.getProperty("env", "local"); // default local
        String path = "env/environment." + env + ".properties";

        System.out.println("🔧 Loading environment config: " + path);

        try (FileInputStream fis = new FileInputStream(path)) {
            properties.load(fis);
            System.out.println("✅ Environment loaded successfully: " + env);
        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to load: " + path, e);
        }
    }

    // --------------------------
    // GET STRING
    // --------------------------
    public static String get(String key) {
        return System.getProperty(key, properties.getProperty(key));
    }

    public static String get(String key, String defaultValue) {
        return System.getProperty(key, properties.getProperty(key, defaultValue));
    }

    // --------------------------
    // GET BOOLEAN
    // --------------------------
    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key, "false"));
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(get(key, String.valueOf(defaultValue)));
    }

    // --------------------------
    // GET INT
    // --------------------------
    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(get(key, String.valueOf(defaultValue)));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    // --------------------------
    // REQUIRED PROPERTY
    // --------------------------
    public static String require(String key) {
        String value = get(key);
        if (value == null) {
            throw new IllegalStateException("❌ Missing required property: " + key);
        }
        return value;
    }
}
