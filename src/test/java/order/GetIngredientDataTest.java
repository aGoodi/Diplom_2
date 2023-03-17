package order;

import ingredient.IngredientAssertions;
import ingredient.IngredientClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;


public class GetIngredientDataTest {

    private final IngredientClient ingredientClient = new IngredientClient();
    private final IngredientAssertions ingredientCheck = new IngredientAssertions();

    @Test
    @DisplayName("Get list of ingredients")
    public void getIngredientData() {
        ValidatableResponse ingredientResponse = ingredientClient.getIngredientData();
        ingredientCheck.gotIngredientsIdSuccessfully(ingredientResponse);
    }
}
