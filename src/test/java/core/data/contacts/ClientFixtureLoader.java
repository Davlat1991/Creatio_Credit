package core.data.contacts;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ClientFixtureLoader {

    private static Map<String, ClientFixture> clients;

    public static ClientFixture get(String key) {

        if (clients == null) {
            load();
        }

        return clients.get(key);
    }

    private static void load() {
        try {
            var stream = ClientFixtureLoader.class
                    .getClassLoader()
                    .getResourceAsStream("data/clients.json");

            if (stream == null) {
                throw new RuntimeException("❌ clients.json not found");
            }

            clients = new Gson().fromJson(
                    new InputStreamReader(stream, StandardCharsets.UTF_8),
                    new TypeToken<Map<String, ClientFixture>>(){}.getType()
            );

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to load clients.json", e);
        }
    }
}