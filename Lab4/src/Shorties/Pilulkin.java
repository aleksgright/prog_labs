package Shorties;

import utility.Bandage;
import utility.Cast;

public class Pilulkin extends Shorty implements MedicalStaff {
    public Pilulkin() {
        super("Pilulkin");
    }

    public Pilulkin(LeaveMoveTo notDefault) {
        super("Pilulkin", notDefault);
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
    }
}
