package utility;

public enum Ingredient {
    STRAWBERRY("Strawberry"), SWEETS("Sweets"), MARMALADE("Marmalade"),
    MUSHROOM("Mushroom"), APPLE("Apple"),
    PEAR("Pear"), ONION("Onion"), BEEF("Beef");
    private String name;

    Ingredient(String ingridientName) {
        this.name = ingridientName;
    }

    public String ingridientName() {
        return name;
    }
}
