package Shorties;

import Meals.Meal;
import utility.Location;

public class Cook extends Shorty implements HospitalStaff{
    public Cook() {
        super("Cook");
    }

    public Cook(Location notDefault, Hospital hosp) {
        super("Cook", notDefault, hosp);
    }

    public Meal cook(Meal meal){
        if (!(meal.getPossibleIngridients().contains(meal.getIngridient()))){
            this.complain();
        }
        return meal;
    }

    @Override
    protected void complain() {
        super.complain();
        System.out.println("This meal doesn't exist");
    }

    @Override
    public void washFloor(){
        move(Location.KITCHEN, getPlace());
        System.out.println(this.getName() + " washes the floor in Kitchen");
    }


}
