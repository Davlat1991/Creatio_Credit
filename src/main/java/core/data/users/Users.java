package core.data.users;

public class Users {

    // 🔹 Пользователь Давлат — тестовый
    public static final Credentials DAVLAT = new Credentials(
            "Davlat",
            "123456"
    );

    // 🔹 Администратор (Supervisor)
    public static final Credentials ADMIN = new Credentials(
            "Supervisor",
            "Supervisor"
    );

    // 🔹 Оператор (можно расширить позже)
    public static final Credentials OPERATOR = new Credentials(
            "Operator",
            "Operator123"
    );

    // 🔹 Только чтение
    public static final Credentials VIEW_ONLY = new Credentials(
            "readonly",
            "readonly123"
    );
}

