package classes.items;

import classes.impediments.Monster;

public class Pencil implements DamageDealer {
    private String name;
    private int damage;

    public Pencil() {
        this.name = "Pencil";
        this.damage = 10;
    }

    public String getName() {
        return name;
    }

    public void attack(Monster monster) {
        if (monster.getHealthPoints() >= damage) {
            monster.setHealthPoints(monster.getHealthPoints() - damage);
        } else {
            monster.setHealthPoints(0);
        }
    }
}
