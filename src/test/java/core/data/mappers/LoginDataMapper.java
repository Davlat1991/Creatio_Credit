package core.data.mappers;

import core.data.users.LoginData;
import core.data.users.UserTestData;

public class LoginDataMapper {

    private LoginDataMapper() {
        // util class
    }

    public static LoginData from(UserTestData testData) {
        return new LoginData(
                testData.getLogin(),
                testData.getPassword()
        );
    }
}
