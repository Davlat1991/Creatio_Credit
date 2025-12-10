package core.data.users;


public enum UserRoles {

    ADMIN("Administrator"),
    SUPERVISOR("Supervisor"),
    OPERATOR("Operator"),
    ANALYST("Analyst"),
    CREDIT_MANAGER("Credit Manager"),
    VIEW_ONLY("Read Only User");

    private final String roleName;

    UserRoles(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}

