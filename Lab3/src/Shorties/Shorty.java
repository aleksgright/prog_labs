package Shorties;

import utility.Location;

import java.util.Objects;

public abstract class Shorty {
    private final String name;
    private Location location;
    private Hospital place;

    public Shorty(String n) {
        name = n;
        location = Location.OUTOFHOSPITAL;
    }

//    public Shorty(String n, Location notDefault) {
//        name = n;
//        LOCATION = notDefault;
//    }

    public Shorty(String n, Location notDefault, Hospital hosp) {
        name = n;
        location = notDefault;
        if (!(notDefault == Location.OUTOFHOSPITAL)) {
            place = hosp;
            moveInHospital(notDefault, hosp);
        }
    }


    public void move(Location newLocation, Hospital hosp) {
        if (newLocation != location) {
            if (newLocation == Location.OUTOFHOSPITAL) {
                System.out.println(getName() + " escapes from Hospital");
                if (place != null) {
                    leavePlace(newLocation, hosp);
                    place = null;
                }
            } else {
                place = hosp;
               // System.out.println(name + " moves from " + location.locationName() + " to " + newLocation.locationName());
                leavePlace(newLocation, hosp);
                moveInHospital(newLocation, hosp);
            }
            location = newLocation;
        }
    }

    private void leavePlace(Location locat, Hospital hosp) {
        switch (locat) {
            case KITCHEN:
                hosp.removeFromKitchen(this);
            case ROOM:
                hosp.removeFromRoom(this);
            case STAFFROOM:
                hosp.removeFromStaffRoom(this);
        }
    }

    private void moveInHospital(Location locat, Hospital hosp) {
        switch (getLocation()) {
            case KITCHEN:
                hosp.addToKitchen(this);
            case ROOM:
                hosp.addToRoom(this);
            case STAFFROOM:
                hosp.addToStaffRoom(this);
        }
    }

    public void speak(String text) {
        System.out.println(text);
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public Hospital getPlace() {
        return place;
    }

    protected void complain() {
        System.out.print(this.getName() + ": I can't stand it no more. ");
    }

    protected void tire(){
        System.out.println(this.getName() + ": I'm incredibly tired!");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shorty)) return false;
        Shorty shorty = (Shorty) o;
        return Objects.equals(name, shorty.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return getName();
    }
}
