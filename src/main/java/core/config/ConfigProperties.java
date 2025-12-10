package core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream is = ConfigProperties.class
                .getClassLoader()
                .getResourceAsStream("framework.properties")) {

            if (is == null)
                throw new RuntimeException("framework.properties not found in resources!");

            PROPERTIES.load(is);

        } catch (IOException e) {
            throw new RuntimeException("Cannot load framework.properties", e);
        }
    }

    //
    // MAIN GETTER
    //
    public static String get(String key) {

        // 1) ENV → highest priority
        String envValue = System.getenv(key.toUpperCase().replace('.', '_'));
        if (envValue != null && !envValue.isEmpty()) return envValue;

        // 2) JVM -D override
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.isEmpty()) return sysProp;

        // 3) framework.properties
        return PROPERTIES.getProperty(key);
    }

    //
    // GETTER WITH DEFAULT VALUE  ← ЭТО ВАЖНО!
    //
    public static String get(String key, String defaultValue) {
        String value = get(key);
        return (value == null || value.isEmpty()) ? defaultValue : value;
    }

    //
    // Int getters
    //
    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static int getInt(String key, int defaultValue) {
        String value = get(key);
        return (value == null || value.isEmpty())
                ? defaultValue
                : Integer.parseInt(value);
    }

    //
    // Boolean getter
    //
    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}
