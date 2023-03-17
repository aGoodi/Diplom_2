package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class GetUserDataTest {

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
    @DisplayName("Get user data")
    public void getUserData() {
        User user = generator.random();
        ValidatableResponse creationResponse = client.create(user);
        var accessToken = check.createdSuccessfullyExtractAccessToken(creationResponse);
        String accessTokenWithoutPrefix = accessToken.split(" ")[1];

        ValidatableResponse dataResponse = client.getData(accessTokenWithoutPrefix);
        check.gotDataSuccessfully(dataResponse);
    }
}
