package core.config;

import core.data.users.LoginData;
import core.data.DbConnectionData;

public final class Environment {

    private Environment() {
        // prevent instantiation
    }

    // ===============================
    // 🌐 ENV INFO
    // ===============================

    public static final String ENV_NAME =
            ConfigProperties.getEnvironmentName();

    // ===============================
    // 🌐 BASE URL (SSOT)
    // ===============================

    public static final String BASE_URL =
            ConfigProperties.require("base.url");

    // ===============================
    // 👤 USERS
    // ===============================

    public static final LoginData USER_DAVLAT = new LoginData(
            ConfigProperties.get("user.davlat.login"),
            ConfigProperties.get("user.davlat.password")
    );

    public static final LoginData USER_ADMIN = new LoginData(
            ConfigProperties.get("user.admin.login"),
            ConfigProperties.get("user.admin.password")
    );

    // ===============================
    // 💾 DB (optional)
    // ===============================

    public static final DbConnectionData DB = new DbConnectionData(
            ConfigProperties.get("db.url", ""),
            ConfigProperties.get("db.user", ""),
            ConfigProperties.get("db.password", "")
    );

    // ===============================
    // 🧾 DEBUG PRINT
    // ===============================

    public static void print() {
        System.out.println("====================================");
        System.out.println("ENVIRONMENT : " + ENV_NAME);
        System.out.println("BASE URL    : " + BASE_URL);
        System.out.println("USER_DAVLAT : " + USER_DAVLAT.getLogin());
        System.out.println("====================================");
    }
}
