package classes.items;

public class DamageDealer extends Item {
    private int damage;

    public DamageDealer(String name, int damage) {
        super(name);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }
}

