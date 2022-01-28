package utility;

public class Bandage {
    // Возможно эти поля могут быть final, а еще можно наверное Bandage и Cast унаследовать от абстрактного хранителя description
    private String description = "бинт";

    public Bandage() {
        description = "Бинт";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
