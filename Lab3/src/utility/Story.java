package utility;

import Meals.*;
import Shorties.*;

public class Story {
    public static void main(String[] args) {
        Hospital durka = new Hospital("durka");
        Vorchun vorchun = new Vorchun(Location.ROOM, durka);
        Pilulkin pilulkin = new Pilulkin(Location.STAFFROOM, durka);
        vorchun.move(Location.OUTOFHOSPITAL, durka);
        pilulkin.move(Location.OUTOFHOSPITAL,durka);
        Nurse nurse = new Nurse(Location.STAFFROOM, durka);
        Pulka pulka = new Pulka(Location.ROOM, durka);
        pulka.callNurse(nurse);
        Cook cook = new Cook(Location.KITCHEN, durka);
        pulka.order(nurse, new Soup(Ingredient.SWEETS));
        pulka.order(nurse, new Porridge(Ingredient.MARMALADE));
        pulka.order(nurse, new Cutlet(Ingredient.STRAWBERRY));
        pulka.order(nurse, new Mash(Ingredient.APPLE));
    }
}