package Shorties;

import Shorties.Pulka.PulkaLeg.Tumour;
import utility.Bandage;
import utility.Cast;

public class Medunica extends Shorty implements MedicalStaff {
    public Medunica() {
        super("Medunica");
    }

    public Medunica(LeaveMoveTo notDefault) {
        super("Medunica", notDefault);
    }

    public void heal(Tumour tumour) {
        Pulka pulka = tumour.getPulkaLeg().getPulka();
        System.out.println(this + " was sitting and healing " + pulka + " all night");
        tumour.decrease();
        pulka.decreaseTemperature();
    }

    public void orderToWashFloor() {
        HospitalStaff cleaner = new HospitalStaff() {
            @Override
            public void washFloor() {
                System.out.println("Опять работа");
            }
        };
        cleaner.washFloor();
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
        // No action
    }
}
