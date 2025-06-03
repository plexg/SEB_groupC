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

    public void useKey() {
        System.out.println("You can now open the door with the " + color + " color!");
    }
}
