package classes.items;

public class WhiteKey implements Key {
    private String name;
    private String color;
    private int doorId;

    public WhiteKey(int doorId) {
        this.name = "White Key";
        this.color = "White";
        this.doorId = doorId;
    }

    public String getName() {
        return name;
    }

    public void useKey() {
        System.out.println("You can now open the door with the " + color + " color!");
    }
}
