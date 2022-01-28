package Shorties;

import utility.Bandage;
import utility.Cast;
import utility.Location;

interface MedicalStaff {
    public Bandage putBandage();

    public Cast putCast();

    public void move(Location newLocation, Hospital hosp);
}
