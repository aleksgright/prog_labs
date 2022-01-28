package Shorties;

import Meals.Meal;
import utility.*;


public class Nurse extends Shorty implements HospitalStaff, MedicalStaff {


    public Nurse() {
        super("Nurse");
    }

    public Nurse(LeaveMoveTo notDefault) {

        super("Nurse", notDefault);
    }

    protected Meal perform(Meal meal, LeaveMoveTo kitchen) {
        move(kitchen);
        kitchen.getShortiesHere()
                .stream()
                .filter(shorty -> shorty instanceof Cook)
                .map(shorty -> (Cook) shorty)
                .findFirst()
                .ifPresentOrElse(
                        (cook) -> askCook(meal, cook),
                        () -> {
                            throw new NoCookException();
                        }
                );
        return meal;
    }

    protected Object lookForSmth(Object o, LeaveMoveTo where) {
        LeaveMoveTo loc = getLocation();
        move(where);
        tireOfSeeking();
        move(loc);
        return null;
    }

    public void checkStorage() {
        System.out.println(this + ": two clothes missing!");
    }

    public void carryPatientTo(Shorty patient, Object o) {
        System.out.println(patient + " was carried to " + o);
    }

    protected void tireOfSeeking() {
        System.out.println(this + ": I'm tired of walking. I hope he's forgotten about it");
    }

    protected void askCook(Meal meal, Cook c) {
        // System.out.println("Nurse: Cook "+ meal.getIngridient().ingridientName() + " " + meal.getName());
        c.cook(meal);
    }

    @Override
    public void washFloor() {
        move(getLocation());
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
    protected void complain() {
        System.out.println("What a terrible patient! He'd rather get well soon");
    }
}
