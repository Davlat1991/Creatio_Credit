package core.data.users;

public class UserTestData {

    private String login;
    private String password;
    private String role;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return login + " (" + role + ")";
    }
}
