package Shorties;

import Meals.Meal;
import utility.Bandage;
import utility.Cast;
import utility.Location;

public class Nurse extends Shorty implements HospitalStaff, MedicalStaff {


    public Nurse() {
        super("Nurse");
    }

    public Nurse(Location notDefault, Hospital hosp) {

        super("Nurse", notDefault, hosp);
    }

    protected Meal perform(Meal meal) {
        boolean f = false;
        move(Location.KITCHEN, this.getPlace());
        for (Shorty shorty : getPlace().getKitchenShorties()) {
            if (shorty instanceof Cook) {
                f = true;
                askCook(meal, (Cook) shorty);
            }
        }
        move(Location.ROOM, this.getPlace());
        if (!f) {
            move(Location.ROOM, this.getPlace());
            //System.out.println(this.getName() + ": We have no cook!");
            return null;
        } else {
            return meal;
        }
    }

    protected Meal askCook(Meal meal, Cook c) {
       // System.out.println("Nurse: Cook "+ meal.getIngridient().ingridientName() + " " + meal.getName());
        return c.cook(meal);
    }

    @Override
    public void washFloor() {
        move(Location.STAFFROOM, getPlace());
        System.out.println(this.getName() + " washes the floor in StaffRoom");
    }

    @Override
    public Bandage putBandage() {
        return new Bandage();
    }

    @Override
    public Cast putCast() {
        return new Cast();
    }

    @Override
    protected void complain(){
        super.complain();
        System.out.println("What a terrible patient! He'd rather get well soon");
    }
}
