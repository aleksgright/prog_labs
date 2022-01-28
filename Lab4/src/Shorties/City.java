package Shorties;

public class City{
    private final String name;

    public static class Street extends LeaveMoveTo{

    }

    public City(String cityName) {
        name = cityName;
    }

    @Override
    public String toString() {
        return name;
    }
}
