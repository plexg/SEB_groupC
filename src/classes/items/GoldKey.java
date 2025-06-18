package classes.items;

public class GoldKey implements Key {
    private String name;
    private String color;
    private int doorId;

    public GoldKey(int doorId) {
        this.name = "Gold Key";
        this.color = "Gold";
        this.doorId = doorId;
    }

    public String getName() {
        return name;
    }
}
