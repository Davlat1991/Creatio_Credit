package tests.flows;

import core.base.BaseTest;
import flows.common.AuthorizationFlow;
import flows.credit.AuthorizationAndClientSearchFlow;
import org.testng.annotations.Test;

public class AuthorizationAndClientSearchFlowTest extends BaseTest {

    @Test
    public void shouldStartConsultationForClient() {

        // arrange
        AuthorizationFlow authFlow =
                new AuthorizationFlow(ctx);

        AuthorizationAndClientSearchFlow clientFlow =
                new AuthorizationAndClientSearchFlow(ctx);

        // act
        authFlow.login(BASE_ULR_1, retailManager);

        clientFlow.startConsultation(
                "Розничный менеджер",
                contact
        );

        // assert
        // минимальная проверка — что мы дошли до заявки
        // (позже вынесем в assertion-flow)
        // пример:
        // ctx.contractPage.shouldBeOpened();
    }
}
