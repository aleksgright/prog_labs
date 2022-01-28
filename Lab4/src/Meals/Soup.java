package Meals;

import utility.Ingridient;

import java.util.HashSet;

public class Soup extends Meal {
    public Soup(Ingridient ing) {
        super("Soup", ing);
        HashSet<Ingridient> ingridients = new HashSet<>();
        ingridients.add(Ingridient.SWEETS);
        setPossibleIngridients(ingridients);
    }
}
