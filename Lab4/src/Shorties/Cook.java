package Shorties;

import Meals.Meal;

public class Cook extends Shorty implements HospitalStaff{
    public Cook() {
        super("Cook");
    }

    public Cook(LeaveMoveTo notDefault) {
        super("Cook", notDefault);
    }

    public Meal cook(Meal meal){
        if (!(Meal.getPossibleIngridients().contains(meal.getIngridient()))){
            this.complain();
        }
        return meal;
    }

    @Override
    protected void complain() {
        System.out.println("This meal doesn't exist");
    }

    @Override
    public void washFloor(){
        move(this.getLocation());
        System.out.println(this.getName() + " washes the floor in Kitchen");
    }
}
