package user;

import io.restassured.response.ValidatableResponse;
import static org.hamcrest.CoreMatchers.is;

public class UserAssertions {

    public void createdSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    public void createdFailsWithSameEmail(ValidatableResponse response) {
        response.assertThat()
                .statusCode(403)
                .body("success", is(false));
    }

    public void createdFailsWithEmptyPassword(ValidatableResponse response) {
        response.assertThat()
                .statusCode(403)
                .body("success", is(false));
    }

    public String createdSuccessfullyExtractAccessToken(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(200)
                .body("success", is(true))
                .extract().path("accessToken");
    }

    public void loggedInSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    public void loggedInFailsWithInvalidData(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("success", is(false));

    }

    public void loggedOutSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    public String loggedOutSuccessfullyExtractRefreshToken(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(200)
                .body("success", is(true))
                .extract().path("refreshToken");
    }

    public void gotDataSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    public void updatedDataSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    public void updatedDataFails(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("success", is(false))
                .body("message", is ("You should be authorised"));
    }

    public void deletedUserSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(202)
                .body("success", is(true));
    }
}
