package moviesApp.utils;

public class Coordinates {
    private double x; //Максимальное значение поля: 393
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
