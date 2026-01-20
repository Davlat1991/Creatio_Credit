package core.base.common.utils;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Хранилище данных теста, которые должны
 * переживать refresh и навигацию.
 */
public final class TestState {

    private static final Map<String, String> DATA = new ConcurrentHashMap<>();

    private TestState() {}

    public static void put(String key, String value) {
        DATA.put(key, value);
    }

    public static String get(String key) {
        return DATA.get(key);
    }

    public static void clear() {
        DATA.clear();
    }
}

