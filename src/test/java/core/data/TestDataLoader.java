package core.data;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class TestDataLoader {

    private static TestData cached;

    private TestDataLoader() {
    }

    public static TestData load() {
        if (cached == null) {
            cached = read();
        }
        return cached;
    }

    private static TestData read() {
        try {
            var stream = TestDataLoader.class
                    .getClassLoader()
                    .getResourceAsStream("testdata.json");

            if (stream == null) {
                throw new IllegalStateException("❌ testdata.json not found in test resources");
            }

            return new Gson().fromJson(
                    new InputStreamReader(stream, StandardCharsets.UTF_8),
                    TestData.class
            );

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to load testdata.json", e);
        }
    }
}
