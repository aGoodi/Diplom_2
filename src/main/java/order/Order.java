package order;

import java.util.ArrayList;

public class Order {
    private ArrayList ingredients;

    public Order(ArrayList ingredients) {
        this.ingredients = ingredients;
    }


    public ArrayList getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList ingredients) {
        this.ingredients = ingredients;
    }
}
