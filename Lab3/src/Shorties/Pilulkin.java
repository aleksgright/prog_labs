package Shorties;

import utility.Bandage;
import utility.Cast;
import utility.Location;

public class Pilulkin extends Shorty implements MedicalStaff {
    public Pilulkin() {
        super("Pilulkin");
    }

    public Pilulkin(Location notDefault, Hospital hosp) {

        super("Pilulkin", notDefault, hosp);
    }

    @Override
    public Bandage putBandage() {
        return new Bandage();
    }

    @Override
    public Cast putCast() {
        return new Cast();
    }
}
