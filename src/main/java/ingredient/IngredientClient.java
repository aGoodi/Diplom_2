package ingredient;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class IngredientClient {

    protected final String BASE_URI = "https://stellarburgers.nomoreparties.site";
    protected final String ROOT = "/api/ingredients";


    public ValidatableResponse getIngredientData() {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get(ROOT)
                .then().log().all();
    }

}
