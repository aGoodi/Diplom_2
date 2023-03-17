package order;

import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.is;

public class OrderAssertions {
    public void gotOrderListFailsWithoutAuth(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("success", is(false));
    }

    public void gotOrderListSuccessfullyWithAuth(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    public void creatingOrderFailsWithoutIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("success", is(false));
    }

    public void creatingOrderFailsWithoutAuth(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("success", is(false));
    }

    public void creatingOrderSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    public void creatingOrderFailsWithInvalidIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(500);
    }
}
