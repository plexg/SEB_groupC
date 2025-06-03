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

    public void useKey() {
        System.out.println("You can now open the door with the " + color + " color!");
    }
}
