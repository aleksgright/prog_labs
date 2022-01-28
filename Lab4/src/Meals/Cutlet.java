package Meals;

import utility.Ingridient;

import java.util.HashSet;

public class Cutlet extends Meal {
    public Cutlet(Ingridient ing) {
        super("Cutlet", ing);
        HashSet<Ingridient> ingridients = new HashSet<>();
        ingridients.add(Ingridient.BEEF);
        setPossibleIngridients(ingridients);
    }
}
