package moviesApp.utils;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private double x;
    private double y;

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
