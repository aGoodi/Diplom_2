package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

public class DeleteUserTest {

    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();

    @Test
    @DisplayName("Delete user")
    public void deleteExistingUser() {
        User user = generator.random();
        ValidatableResponse creationResponse = client.create(user);
        var token = check.createdSuccessfullyExtractAccessToken(creationResponse);
        String accessToken = token.split(" ")[1];

        Credentials creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.login(creds, accessToken);
        check.loggedInSuccessfully(loginResponse);

        ValidatableResponse deleteResponse = client.delete(accessToken);
        check.deletedUserSuccessfully(deleteResponse);
    }
}
