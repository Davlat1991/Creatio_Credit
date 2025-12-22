package core.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {

    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        String envFile = "environment.properties";

        try (FileInputStream fis = new FileInputStream(envFile)) {
            properties.load(fis);
            System.out.println("🔧 Loaded config from: " + envFile);
        } catch (IOException e) {
            System.err.println("⚠ environment.properties NOT FOUND. Only system properties will be used.");
        }
    }

    public static String get(String key) {
        return System.getProperty(key, properties.getProperty(key));
    }

    public static String get(String key, String defaultValue) {
        return System.getProperty(key, properties.getProperty(key, defaultValue));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key, "false"));
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(get(key, String.valueOf(defaultValue)));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static int getInt(String key, int defaultValue) {
        String value = get(key);
        if (value == null) return defaultValue;

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.err.println("⚠ Invalid integer for key: " + key + " → using default: " + defaultValue);
            return defaultValue;
        }
    }

    public static String require(String key) {
        String value = get(key);
        if (value == null) {
            throw new IllegalStateException("❌ Missing required property: " + key);
        }
        return value;
    }
}
