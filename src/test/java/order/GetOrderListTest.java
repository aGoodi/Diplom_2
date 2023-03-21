package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import user.*;

public class GetOrderListTest {

    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private final OrderClient orderClient = new OrderClient();
    private final UserAssertions check = new UserAssertions();
    private final OrderAssertions orderCheck = new OrderAssertions();
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
    @DisplayName("Get list of orders without authorization")
    public void getOrderListWithoutAuth() {
        ValidatableResponse orderResponse = orderClient.getOrderList();
        orderCheck.gotOrderListFailsWithoutAuth(orderResponse);
    }

    @Test
    @DisplayName("Get list of orders with authorization")
    public void getOrderListWithAuth() {
        User user = generator.random();
        ValidatableResponse creationResponse = client.create(user);
        var token = check.createdSuccessfullyExtractAccessToken(creationResponse);
        String accessToken = token.split(" ")[1];

        Credentials creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.login(creds, accessToken);
        check.loggedInSuccessfully(loginResponse);

        ValidatableResponse orderResponse = orderClient.getOrderListWithAuth(accessToken);
        orderCheck.gotOrderListSuccessfullyWithAuth(orderResponse);
    }
}
