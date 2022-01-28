package utility;

public class Cast {
    // Всегда лучше private поле с геттером и сеттером, чем просто public поле - на забывай про инкапсуляцию
    private String description = "гипс";

    public Cast(){
        description = "Гипс";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
