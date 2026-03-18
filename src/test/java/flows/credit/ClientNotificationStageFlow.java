package flows.credit;

import core.base.UiContext;
import flows.common.ApplicationSearchFlow;
import io.qameta.allure.Step;

public class ClientNotificationStageFlow {

    private final UiContext ui;
    private final ApplicationSearchFlow applicationSearchFlow;

    public ClientNotificationStageFlow(UiContext ui) {
        this.ui = ui;
        this.applicationSearchFlow = new ApplicationSearchFlow(ui);
    }

    // =====================================================
    // 📞 CLIENT NOTIFICATION STAGE
    // =====================================================

    @Step("Client Notification: информирование клиента и подтверждение согласия")
    public void completeClientNotification(String responsiblePerson) {

        ui.basePage.closeConsultationPanelIfOpened();

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
        ui.basePage.closeConsultationPanelIfOpened();

        ui.dashboardComponent.clickElementDashboardCheck(
                "Информирование клиента",
                "Execute",
                "//*[@data-item-marker='MiniPage']"
        );
    }

    private void setClientAgreement() {
        ui.contractPage
                .setfieldScheduleDetailByDIM("Result", "Клиент согласен");

        // lookup → обязательный li
        ui.menuComponent
                .clickButtonByLiName("Клиент согласен");

        ui.basePage
                .clickButtonByDataItemMaker("SaveEditButton");
    }

    private void assignResponsibleForSigning(String responsiblePerson) {
        ui.lookupComponent
                .setHandBookFieldByValueCheck(
                        "Ответственный за подписание",
                        responsiblePerson
                );
    }

    private void confirmClientNotification() {
        ui.basePage
                .clickButtonByNameCheck("Подтвердить");
    }
}
