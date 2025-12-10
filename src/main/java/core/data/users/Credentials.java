package core.data.users;

public class Credentials {

    private final String username;
    private final String password;

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Credentials{username='" + username + "'}";
        // Пароль намеренно НЕ выводим — безопасность
    }
}

