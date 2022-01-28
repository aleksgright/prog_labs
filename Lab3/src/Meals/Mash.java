package Meals;

import utility.Ingridient;

import java.util.HashSet;

public class Mash extends Meal {
    public Mash(Ingridient ing) {
        super("Mash", ing);
        HashSet<Ingridient> ingridients = new HashSet<>();
        ingridients.add(Ingridient.APPLE);
        ingridients.add(Ingridient.PEAR);
        setPossibleIngridients(ingridients);
    }
}
