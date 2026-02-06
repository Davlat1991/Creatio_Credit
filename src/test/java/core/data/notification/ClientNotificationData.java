package core.data.notification;

public class ClientNotificationData {

    public String receiver;
    public boolean required;

    // Удобный factory-метод (рекомендую)
    public static ClientNotificationData optional(String receiver) {
        ClientNotificationData data = new ClientNotificationData();
        data.receiver = receiver;
        data.required = false;
        return data;
    }

    public static ClientNotificationData required(String receiver) {
        ClientNotificationData data = new ClientNotificationData();
        data.receiver = receiver;
        data.required = true;
        return data;
    }
}
