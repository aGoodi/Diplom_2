package user;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient {
    protected final String BASE_URI = "https://stellarburgers.nomoreparties.site";
    protected final String ROOT = "/api/auth";

    public ValidatableResponse create(User user) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .and()
                .body(user)
                .when()
                .post(ROOT + "/register")
                .then().log().all();
    }

    public ValidatableResponse login(Credentials creds, String accessToken) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .auth().oauth2(accessToken)
                .and()
                .body(creds)
                .when()
                .post(ROOT + "/login")
                .then().log().all();
    }

    public ValidatableResponse getData(String accessToken) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .auth().oauth2(accessToken)
                .get(ROOT + "/user")
                .then().log().all();
    }

    public ValidatableResponse updateDataWithAuth(User user, String accessToken) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .auth().oauth2(accessToken)
                .and()
                .body(user)
                .when()
                .patch(ROOT + "/user")
                .then().log().all();
    }
    public ValidatableResponse updateDataWithoutAuth(User user) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .and()
                .body(user)
                .when()
                .patch(ROOT + "/user")
                .then().log().all();
    }

    public ValidatableResponse logout(Token refreshToken) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .and()
                .body(refreshToken)
                .when()
                .post(ROOT + "/logout")
                .then().log().all();
    }

    public ValidatableResponse delete(String accessToken) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .auth().oauth2(accessToken)
                .when()
                .delete(ROOT + "/user")
                .then().log().all();
    }

}
