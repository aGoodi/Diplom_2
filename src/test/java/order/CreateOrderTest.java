package order;

import ingredient.IngredientAssertions;
import ingredient.IngredientClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.*;

import java.util.ArrayList;
import java.util.List;

public class CreateOrderTest {
    private final OrderClient orderClient = new OrderClient();
    private final OrderAssertions orderCheck = new OrderAssertions();
    private final UserGenerator userGenerator = new UserGenerator();

    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();
    private final IngredientClient ingredientClient = new IngredientClient();
    private final IngredientAssertions ingredientCheck = new IngredientAssertions();
    private ValidatableResponse creationResponse;
    private String accessToken;

    //private UserGenerator generator = new UserGenerator();

    @Before
    public void createUser() {
        User user = userGenerator.random();
        creationResponse = client.create(user);
        var token = check.createdSuccessfullyExtractAccessToken(creationResponse);
        String accessToken = token.split(" ")[1];

        Credentials creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.login(creds, accessToken);
        check.loggedInSuccessfully(loginResponse);
    }

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
    @DisplayName("Create new order without authorization")
    public void createOrderWithoutAuth() {
        var refreshToken = check.loggedOutSuccessfullyExtractRefreshToken(creationResponse);
        var token1 = new Token(refreshToken);
        ValidatableResponse logoutResponse = client.logout(token1);
        check.loggedOutSuccessfully(logoutResponse);

        ValidatableResponse ingredientResponse = ingredientClient.getIngredientData();
        var ingredient = ingredientCheck.gotIngredientsIdSuccessfully(ingredientResponse);

        var order = new Order(ingredient);
        ValidatableResponse orderResponse = orderClient.create(order);
        orderCheck.creatingOrderFailsWithoutAuth(orderResponse);
    }

    @Test
    @DisplayName("Create new order without ingredients")
    public void createOrderWithoutIngredients() {
        var ingredient = new ArrayList<>();

        var order = new Order(ingredient);
        ValidatableResponse orderResponse = orderClient.create(order);
        orderCheck.creatingOrderFailsWithoutIngredients(orderResponse);
    }

    @Test
    @DisplayName("Create new order with all ingredients")
    public void createOrder() {
        ValidatableResponse ingredientResponse = ingredientClient.getIngredientData();
        var ingredient = ingredientCheck.gotIngredientsIdSuccessfully(ingredientResponse);

        var order = new Order(ingredient);
        ValidatableResponse orderResponse = orderClient.create(order);
        orderCheck.creatingOrderSuccessfully(orderResponse);
    }

    @Test
    @DisplayName("Create new order with invalid ingredient")
    public void createOrderWithInvalidIngredient() {
        var ingredient = new ArrayList<>(List.of("123"));

        var order = new Order(ingredient);
        ValidatableResponse orderResponse = orderClient.create(order);
        orderCheck.creatingOrderFailsWithInvalidIngredients(orderResponse);
    }
}
