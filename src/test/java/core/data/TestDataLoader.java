package core.data;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class TestDataLoader {

    private static TestData testData;

    private TestDataLoader() {
    }

    public static TestData load() {
        if (testData == null) {
            testData = read();
        }
        return testData;
    }

    private static TestData read() {
        try {
            var stream = TestDataLoader.class
                    .getClassLoader()
                    .getResourceAsStream("testdata.json");

            if (stream == null) {
                throw new RuntimeException("❌ testdata.json not found in resources");
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
