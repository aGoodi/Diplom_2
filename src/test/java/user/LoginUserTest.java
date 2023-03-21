package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class LoginUserTest {

    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();

    private ValidatableResponse creationResponse;
    private String accessToken;

    @After
    public void deleteUser() {
        if (accessToken != null) {
            var token = check.createdSuccessfullyExtractAccessToken(creationResponse);
            String accessToken = token.split(" ")[1];

            ValidatableResponse deleteResponse = client.delete(accessToken);
            check.deletedUserSuccessfully(deleteResponse);
        }
    }

    @Test
    @DisplayName("Login existing user")
    public void loginExistingUser() {
        User user = generator.random();
        ValidatableResponse creationResponse = client.create(user);
        var token = check.createdSuccessfullyExtractAccessToken(creationResponse);
        String accessToken = token.split(" ")[1];

        Credentials creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.login(creds, accessToken);
        check.loggedInSuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Login non-existing user")
    public void loginNonExistingUser() {
        User user = generator.random();
        ValidatableResponse creationResponse = client.create(user);
        var accessToken = check.createdSuccessfullyExtractAccessToken(creationResponse);

        Credentials creds = new Credentials("user", "1234");
        ValidatableResponse loginResponse = client.login(creds, accessToken);
        check.loggedInFailsWithInvalidData(loginResponse);
    }
}
