package Shorties;

import utility.Bandage;
import utility.Cast;

interface MedicalStaff {
    Bandage putBandage();
    Cast putCast();
    void move(LeaveMoveTo loc);
}
