package mocks.core;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public abstract class BaseMockServer {

    private static final int PORT = 8089;
    private static final String HOST = "localhost";

    protected static WireMockServer server;

    public static void start() {
        if (server != null && server.isRunning()) {
            System.out.println("[WireMock] already running on http://" + HOST + ":" + PORT);
            return;
        }

        try {
            server = new WireMockServer(
                    wireMockConfig()
                            .port(PORT)
                            .disableRequestJournal() // будем видеть запросы
            );

            server.start();
            configureFor(HOST, PORT);

            System.out.println("=================================================");
            System.out.println("🚀 WireMock STARTED");
            System.out.println("🌐 Base URL: http://" + HOST + ":" + PORT);
            System.out.println("=================================================");

        } catch (Exception e) {
            System.err.println("❌ FAILED TO START WireMock on port " + PORT);
            System.err.println("👉 Possible reasons:");
            System.err.println("   - port " + PORT + " is already in use");
            System.err.println("   - another WireMock instance is running");
            throw e;
        }
    }

    public static void reset() {
        if (server != null && server.isRunning()) {
            server.resetAll();
            System.out.println("[WireMock] stubs reset");
        }
    }

    public static void stop() {
        if (server != null) {
            System.out.println("🛑 Stopping WireMock");
            server.stop();
            server = null;
        }
    }
}
