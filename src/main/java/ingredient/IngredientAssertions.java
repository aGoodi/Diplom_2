package ingredient;

import io.restassured.response.ValidatableResponse;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;

public class IngredientAssertions {

    public ArrayList gotIngredientsIdSuccessfully(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(200)
                .body("success", is(true))
                .extract().path("data._id");
    }

}
