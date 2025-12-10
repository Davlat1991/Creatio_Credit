package core.config;

import core.data.users.LoginData;
import core.data.DbConnectionData;

public class Environment {

    // -------------------------------
    // URLs
    // -------------------------------
    public static final String BASE_URL =
            ConfigProperties.get("base.url", "http://localhost");

    // -------------------------------
    // Default test users
    // -------------------------------
    public static final LoginData USER_DAVLAT = new LoginData(
            ConfigProperties.get("user.davlat.login", "Davlat"),
            ConfigProperties.get("user.davlat.password", "")
    );

    public static final LoginData USER_ADMIN = new LoginData(
            ConfigProperties.get("user.admin.login", "Supervisor"),
            ConfigProperties.get("user.admin.password", "Supervisor")
    );

    // -------------------------------
    // DB connection
    // -------------------------------
    public static final DbConnectionData DB = new DbConnectionData(
            ConfigProperties.get("db.url", ""),
            ConfigProperties.get("db.user", ""),
            ConfigProperties.get("db.password", "")
    );
}
