package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class CreateUserTest {

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
    @DisplayName("Create new user with valid data")
    public void createNewUser() {
        User user = generator.random();
        creationResponse = client.create(user);
        check.createdSuccessfully(creationResponse);
    }

    @Test
    @DisplayName("Create existing user")
    public void createExistingUser() {
        User user = generator.generic();
        creationResponse = client.create(user);
        check.createdFailsWithSameEmail(creationResponse);
    }

    @Test
    @DisplayName("Create user with empty password field")
    public void createUserWithEmptyPassword() {
        User user = generator.emptyPassword();
        creationResponse = client.create(user);
        check.createdFailsWithEmptyPassword(creationResponse);
    }
}
