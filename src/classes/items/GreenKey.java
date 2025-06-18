package classes.items;

public class GreenKey implements Key {
    private String name;
    private String color;
    private int doorId;

    public GreenKey(int doorId) {
        this.name = "Green Key";
        this.color = "Green";
        this.doorId = doorId;
    }

    public String getName() {
        return name;
    }
}
