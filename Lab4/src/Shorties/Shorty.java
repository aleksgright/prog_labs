package Shorties;


import Shorties.Hospital.StorageRoom;
import utility.MoveException;
import java.util.Objects;
import java.lang.*;

public abstract class Shorty {
    private final String name;
    private LeaveMoveTo location;
    private boolean brokenLeg = false;
    double temperature = 36.6;

    public Shorty(String n) {
        name = n;
        location = null;
    }

    public Shorty(String n, LeaveMoveTo notDefault) {
        name = n;
        location = notDefault;
        moveIn(notDefault);
    }

    public void move(LeaveMoveTo newLocation) {
        if (newLocation != location) {
            try {
                leavePlace(location);
//                if (newLocation.getClass().getEnclosingClass() != Hospital.class) {
//                    System.out.println(getName() + " escapes from Hospital");
//                }
                if (location.getClass().getEnclosingClass() == City.class) {
                    System.out.println(this + " returned to Hospital");
                }
                if (location.getClass() == StorageRoom.class) {
                    if (this instanceof MedicalStaff) {
                        System.out.println(this + " went to Storage Room");
                    }
//                    else{
//                        System.out.println(this + " sneaked to Storage Room");
//                    }
                }
                if (newLocation.getClass().getEnclosingClass() == City.class) {
                    System.out.println(this + " ran to city");
                }
                moveIn(newLocation);
                location = newLocation;
            } catch (MoveException ex) {
                System.out.println(this + " tried to run, but he twisted his leg and fell!");
            }
        }
    }

    private void leavePlace(LeaveMoveTo location) throws MoveException {
        if (isBrokenLeg()) {
            throw new MoveException();
        } else {
            location.remove(this);
        }
    }

    private void moveIn(LeaveMoveTo location) {
        location.add(this);
    }

    public void riseTemperature() {
        temperature++;
        System.out.println(this + "'s temperature has risen");
    }

    public void decreaseTemperature() {
        temperature--;
        System.out.println(this + "'s temperature has risen");
    }

    public void speak(String text) {
        System.out.println(this + ": " + text);
    }

    public void wakeUp() {
        System.out.println(this + " woke up");
    }

    public String getName() {
        return name;
    }

    public LeaveMoveTo getLocation() {
        return location;
    }

    protected abstract void complain();

    protected void tire() {
        System.out.println(this.getName() + ": I'm incredibly tired!");
    }

    public void runAfter(Shorty shorty) {
        System.out.println(this + " ran after " + shorty);
    }

    public void takeClothes(StorageRoom storageRoom) {
        move(storageRoom);
        storageRoom.takeCloth();
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

    public boolean isBrokenLeg() {
        return brokenLeg;
    }

    public void setBrokenLeg(boolean brokenLeg) {
        this.brokenLeg = brokenLeg;
    }
}
