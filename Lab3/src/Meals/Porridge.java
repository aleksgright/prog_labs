package Meals;

import utility.Ingredient;

import java.util.HashSet;

public class Porridge extends Meal {
    public Porridge(Ingredient ing) {
        super("Porridge", ing);
        HashSet<Ingredient> ingredients = new HashSet<>();
        ingredients.add(Ingredient.MARMALADE);
        setPossibleIngridients(ingredients);
    }
}
