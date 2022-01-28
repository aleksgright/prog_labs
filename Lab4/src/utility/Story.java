package utility;

import Meals.*;
import Shorties.*;
import Shorties.Pulka.PulkaLeg;
import Shorties.Pulka.PulkaLeg.Tumour;

public class Story {
    public static void main(String[] args) {
        Hospital durka = new Hospital("durka");
        Hospital.Kitchen kitchen = durka.new Kitchen();
        Hospital.StaffRoom staffRoom = durka.new StaffRoom();
        Hospital.Room room = durka.new Room();
        Hospital.StorageRoom storageRoom = durka.new StorageRoom();
        City city = new City("city");
        City.Street street = new City.Street();
        Medunica medunica = new Medunica(street);
        Nurse nurse = new Nurse(street);
        Cook cook = new Cook(street);
        Vorchun vorchun = new Vorchun(room);
        Pilulkin pilulkin = new Pilulkin(room);
        Pulka pulka = new Pulka(room);
        PulkaLeg pulkaLeg = pulka.new PulkaLeg();
        Hospital.Room.Bed bed = new Hospital.Room.Bed();
        System.out.println();

        pilulkin.move(street);
        nurse.runAfter(pilulkin);
        medunica.runAfter(pilulkin);
        cook.runAfter(pilulkin);
        vorchun.takeClothes(storageRoom);
        vorchun.move(street);
        medunica.move(room);
        nurse.move(room);
        cook.move(kitchen);
        if (room.getShortiesHere().size() < 5) {
            System.out.println("Medunica: Vorchun and Pilulkin ran away!");
        }
        nurse.checkStorage();
        System.out.println();

        pulka.callNurse(nurse);
        pulka.order(nurse, new Soup(Ingridient.SWEETS), kitchen);
        pulka.order(nurse, new Porridge(Ingridient.MARMALADE), kitchen);
        pulka.order(nurse, new Cutlet(Ingridient.STRAWBERRY), kitchen);
        pulka.order(nurse, new Mash(Ingridient.APPLE), kitchen);
        System.out.println();

        pulka.orderToFindDog(nurse, street);
        System.out.println();

        medunica.speak("Pulka's character get worse day by day, only hospital discharge can help him");
        pulka.wakeUp();
        if (!pulkaLeg.isPain()) {
            pulka.speak("I feel no pain!");
        }
        pulka.move(street);
        nurse.carryPatientTo(pulka, bed);
        Tumour tumour = pulkaLeg.new Tumour(3.4);
        pulka.riseTemperature();
        medunica.heal(tumour);
    }
}