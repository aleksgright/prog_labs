package utility;

public enum Ingridient {
    STRAWBERRY("Strawberry"), SWEETS("Sweets"), MARMALADE("Marmalade"),
    MUSHROOM("Mushroom"), APPLE("Apple"),
    PEAR("Pear"), ONION("Onion"), BEEF("Beef");
    private String name;

    Ingridient(String ingridientName) {
        this.name = ingridientName;
    }

    public String ingridientName() {
        return name;
    }
}
