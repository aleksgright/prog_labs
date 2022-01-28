package Shorties;

import java.util.HashSet;

public abstract class LeaveMoveTo {
    private String name;
    private final HashSet<Shorty> ShortiesHere = new HashSet<>();

    void add(Shorty shorty) {
        ShortiesHere.add(shorty);
    }

    void remove(Shorty shorty) {
        ShortiesHere.remove(shorty);
    }

    public HashSet<Shorty> getShortiesHere() {
        return ShortiesHere;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
