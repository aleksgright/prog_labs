package Meals;

import utility.Ingredient;

import java.util.HashSet;
import java.util.Objects;

public abstract class Meal {
    private final Ingredient ingredient;
    private final String name;
    private static final HashSet<Ingredient> POSSIBLE_INGREDIENTS = new HashSet<>();

    public Meal(String n, Ingredient ing) {
        name = n;
        ingredient = ing;
    }

    public String getName() {
        return name;
    }

    public Ingredient getIngridient() {
        return ingredient;
    }

    protected void setPossibleIngridients(HashSet<Ingredient> ingredients){
        for (Ingredient ing: ingredients){
            POSSIBLE_INGREDIENTS.add(ing);
        }
    }

    public static HashSet<Ingredient> getPossibleIngridients() {
        return POSSIBLE_INGREDIENTS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meal)) return false;
        Meal meal = (Meal) o;
        return ingredient == meal.ingredient && Objects.equals(name, meal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return getIngridient().ingridientName() + " " +getName();
    }
}
