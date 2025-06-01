package classes.items;

public class Key extends Item {
    private String color;
    private int doorId;

    public Key(String name, String color, int doorId) {
        super(name);
        this.color = color;
        this.doorId = doorId;
    }

    public String getColor() {
        return color;
    }

    public int getDoorId() {
        return doorId;
    }
}

