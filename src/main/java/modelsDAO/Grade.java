package modelsDAO;

public class Grade {
    private String name;
    private Double value;

    public Grade(String name, Double value) {
        this.name = name;
        this.value = value;
    }

    public Grade() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
