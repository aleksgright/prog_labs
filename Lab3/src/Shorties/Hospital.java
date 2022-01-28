package Shorties;

import Shorties.Shorty;

import java.util.HashSet;
import java.util.Objects;

public class Hospital {
    private String name;
    private HashSet<Shorty> kitchenShorties = new HashSet<>();
    private HashSet<Shorty> roomShorties = new HashSet<>();
    private HashSet<Shorty> staffRoomShorties = new HashSet<>();

    public Hospital(String hospName) {
        name = hospName;
    }

    void addToKitchen(Shorty shorty) {
        kitchenShorties.add(shorty);
    }

    void removeFromKitchen(Shorty shorty) {
        kitchenShorties.remove(shorty);
    }

    void addToStaffRoom(Shorty shorty) {
        staffRoomShorties.add(shorty);
    }

    void removeFromStaffRoom(Shorty shorty) {
        staffRoomShorties.remove(shorty);
    }

    void addToRoom(Shorty shorty) {
        roomShorties.add(shorty);
    }

    void removeFromRoom(Shorty shorty) {
        roomShorties.remove(shorty);
    }

    public HashSet<Shorty> getKitchenShorties() {
        return kitchenShorties;
    }

    public HashSet<Shorty> getRoomShorties() {
        return roomShorties;
    }

    public HashSet<Shorty> getStaffRoomShorties() {
        return staffRoomShorties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hospital)) return false;
        Hospital hospital = (Hospital) o;
        return Objects.equals(name, hospital.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
