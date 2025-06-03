package classes.items;

import classes.impediments.Monster;

public class Staplergun implements DamageDealer {
    private String name;
    private int damage;

    public Staplergun() {
        this.name = "Staplergun";
        this.damage = 20;
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
