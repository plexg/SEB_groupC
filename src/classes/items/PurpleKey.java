package classes.items;

public class PurpleKey implements Key {
    private String name;
    private String color;
    private int doorId;

    public PurpleKey(int doorId) {
        this.name = "Purple Key";
        this.color = "Purple";
        this.doorId = doorId;
    }

    public String getName() {
        return name;
    }
}
