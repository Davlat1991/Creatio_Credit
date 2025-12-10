package core.data.users;

public class LoginData {

    private final String login;
    private final String password;

    public LoginData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginData{login='" + login + "'}";
    }
}


