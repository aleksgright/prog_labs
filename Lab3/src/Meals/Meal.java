package Meals;

import utility.Ingridient;

import java.util.HashSet;
import java.util.Objects;

public abstract class Meal {
    private final Ingridient ingridient;
    private final String name;
    private static final HashSet<Ingridient> possibleIngridients = new HashSet<>();

    public Meal(String n, Ingridient ing) {
        name = n;
        ingridient = ing;
    }

    public String getName() {
        return name;
    }

    public Ingridient getIngridient() {
        return ingridient;
    }

    protected void setPossibleIngridients(HashSet<Ingridient> ingridients){
        for (Ingridient ing: ingridients){
            possibleIngridients.add(ing);
        }
    }

    public static HashSet<Ingridient> getPossibleIngridients() {
        return possibleIngridients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meal)) return false;
        Meal meal = (Meal) o;
        return ingridient == meal.ingridient && Objects.equals(name, meal.name);
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
