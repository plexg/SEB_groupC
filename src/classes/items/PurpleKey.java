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

    public void useKey() {
        System.out.println("You can now open the door with the " + color + " color!");
    }
}
