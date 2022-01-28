package Meals;

import utility.Ingredient;

import java.util.HashSet;

public class Mash extends Meal {
    public Mash(Ingredient ing) {
        super("Mash", ing);
        HashSet<Ingredient> ingredients = new HashSet<>();
        ingredients.add(Ingredient.APPLE);
        ingredients.add(Ingredient.PEAR);
        setPossibleIngridients(ingredients);
    }
}
