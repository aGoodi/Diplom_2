package order;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrderClient {

    protected final String BASE_URI = "https://stellarburgers.nomoreparties.site";
    protected final String ROOT = "/api/orders";

    public ValidatableResponse create(Order order) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .and()
                .body(order)
                .when()
                .post(ROOT)
                .then().log().all();
    }


    public ValidatableResponse getOrderList() {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get(ROOT)
                .then().log().all();
    }

    public ValidatableResponse getOrderListWithAuth(String accessToken) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .auth().oauth2(accessToken)
                .when()
                .get(ROOT)
                .then().log().all();
    }

}
