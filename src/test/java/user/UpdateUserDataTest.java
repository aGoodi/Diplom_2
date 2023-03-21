package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;

public class UpdateUserDataTest {

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
    @DisplayName("Update user data with authorization")
    public void updateUserDataWithAuth() {
        User user = generator.random();
        ValidatableResponse creationResponse = client.create(user);
        var token = check.createdSuccessfullyExtractAccessToken(creationResponse);
        String accessToken = token.split(" ")[1];

        User newData = new User(String.format(RandomStringUtils.randomNumeric(6) + "@yandex.ru"), user.getPassword(), String.format(RandomStringUtils.randomNumeric(6)));
        ValidatableResponse updateResponse = client.updateDataWithAuth(newData, accessToken);
        check.updatedDataSuccessfully(updateResponse);
    }

    @Test
    @DisplayName("Update user data without authorization")
    public void updateUserDataWithoutAuth() {
        User user = generator.random();
        ValidatableResponse creationResponse = client.create(user);
        check.createdSuccessfully(creationResponse);

        User newData = new User(String.format(RandomStringUtils.randomNumeric(6) + "@yandex.ru"), user.getPassword(), String.format(RandomStringUtils.randomNumeric(6)));
        ValidatableResponse updateResponse = client.updateDataWithoutAuth(newData);
        check.updatedDataFails(updateResponse);
    }
}
