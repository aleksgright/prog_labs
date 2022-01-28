package Meals;

import utility.Ingridient;

import java.util.HashSet;

public class Porridge extends Meal {
    public Porridge(Ingridient ing) {
        super("Porridge", ing);
        HashSet<Ingridient> ingridients = new HashSet<>();
        ingridients.add(Ingridient.MARMALADE);
        setPossibleIngridients(ingridients);
    }
}
