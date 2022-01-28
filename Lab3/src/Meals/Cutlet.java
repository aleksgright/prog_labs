package Meals;

import utility.Ingredient;

import java.util.HashSet;

public class Cutlet extends Meal {
    public Cutlet(Ingredient ing) {
        super("Cutlet", ing);
        HashSet<Ingredient> ingredients = new HashSet<>();
        ingredients.add(Ingredient.BEEF);
        setPossibleIngridients(ingredients);
    }
}
