package classes.items;

public class Healer extends Item {
    private int health;

    public Healer(String name, int health) {
        super(name);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }
}

