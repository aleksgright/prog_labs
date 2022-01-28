package Shorties;

import Meals.Kvas;
import Meals.Meal;
import utility.Ingridient;
import utility.Location;

public class Pulka extends Shorty {
    private Meal meal;
    private int mealCount = 0;

    public Pulka() {
        super("Pulka");
    }

    public Pulka(Location notDefault, Hospital hosp) {
        super("Pulka", notDefault, hosp);
    }

    public void callNurse(Nurse nurse) {
        nurse.move(Location.ROOM, this.getPlace());
    }

    public void order(Nurse nurse, Meal meal) {
        System.out.println("Pulka: Cook me " + meal);
        getMeal(nurse.perform(meal));
        if (mealCount == 5) {
            nurse.complain();
            nurse.tire();
        }
        if (mealCount == 4) {
            order(nurse, new Kvas(Ingridient.PEAR));
        }
        //complain(this.meal);
    }

    public void getMeal(Meal meal) {
        System.out.println("Pulka gets " + meal);
        this.meal = meal;
        mealCount++;
        if (mealCount == 4 || mealCount == 5) {
            complain(meal);
        }
    }

    public void askForBandage(MedicalStaff medic) {
        medic.move(Location.ROOM, this.getPlace());
        medic.putBandage();
    }

    public void askForCast(MedicalStaff medic) {
        medic.move(Location.ROOM, this.getPlace());
        medic.putCast();
    }

    @Override
    protected void complain() {
        super.complain();
        System.out.println("This hospital is awful.");
    }

    protected void complain(Meal meal) {
        super.complain();
        if (mealCount == 5) {
            System.out.println("This " + meal.getName() + " stinks of onion");
        }
        if (mealCount == 4) {
            System.out.println("I asked for Pear Kvas");
        }
    }
}
