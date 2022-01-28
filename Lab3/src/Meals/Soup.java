package Meals;

import utility.Ingredient;

import java.util.HashSet;

public class Soup extends Meal {
    public Soup(Ingredient ing) {
        super("Soup", ing);
        HashSet<Ingredient> ingredients = new HashSet<>();
        ingredients.add(Ingredient.SWEETS);
        setPossibleIngridients(ingredients);
    }
}
