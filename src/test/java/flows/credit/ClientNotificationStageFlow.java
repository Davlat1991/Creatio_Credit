package flows.credit;

import core.base.TestContext;
import flows.common.ApplicationSearchFlow;
import io.qameta.allure.Step;

public class ClientNotificationStageFlow {

    private final TestContext ctx;
    private final ApplicationSearchFlow applicationSearchFlow;

    public ClientNotificationStageFlow(TestContext ctx) {
        this.ctx = ctx;
        this.applicationSearchFlow = new ApplicationSearchFlow(ctx);
    }

    // =====================================================
    // 📞 CLIENT NOTIFICATION STAGE
    // =====================================================

    @Step("Client Notification: информирование клиента и подтверждение согласия")
    public void completeClientNotification(String responsiblePerson) {

        // 1️⃣ Открываем заявку по сохранённому номеру
        applicationSearchFlow.openBySavedNumber();

        // 2️⃣ Открываем mini-page этапа «Информирование клиента»
        openClientNotificationMiniPage();

        // 3️⃣ Фиксируем согласие клиента
        setClientAgreement();

        // 4️⃣ Назначаем ответственного за подписание
        assignResponsibleForSigning(responsiblePerson);

        // 5️⃣ Подтверждаем этап
        confirmClientNotification();
    }

    // =====================================================
    // INTERNAL STEPS
    // =====================================================

    private void openClientNotificationMiniPage() {
        ctx.dashboardComponent.clickElementDashboardCheck(
                "Информирование клиента",
                "Execute",
                "//*[@data-item-marker='MiniPage']"
        );
    }

    private void setClientAgreement() {
        ctx.contractPage
                .setfieldScheduleDetailByDIM("Result", "Клиент согласен");

        // lookup → обязательный li
        ctx.menuComponent
                .clickButtonByLiName("Клиент согласен");

        ctx.basePage
                .clickButtonByDataItemMaker("SaveEditButton");
    }

    private void assignResponsibleForSigning(String responsiblePerson) {
        ctx.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Ответственный за подписание",
                        responsiblePerson
                );
    }

    private void confirmClientNotification() {
        ctx.basePage
                .clickButtonByNameCheck("Подтвердить");
    }
}
