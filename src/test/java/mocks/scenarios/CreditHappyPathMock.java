package mocks.scenarios;

import mocks.config.MockEndpoints;
import mocks.core.BaseMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class CreditHappyPathMock extends BaseMockServer {

    public static void setup() {

        stubFor(post(urlEqualTo(MockEndpoints.CREDIT_CREATE))
                .willReturn(okJson("""
                    { "status": "CREATED" }
                """)));

        stubFor(post(urlEqualTo(MockEndpoints.CREDIT_SCORING))
                .willReturn(okJson("""
                    { "result": "APPROVED", "limit": 200000 }
                """)));

        stubFor(get(urlEqualTo(MockEndpoints.CREDIT_STATUS))
                .willReturn(okJson("""
                    { "status": "APPROVED" }
                """)));
    }
}
