package classes.items;

import classes.impediments.Monster;

public class Staplergun extends DamageDealer {
    public Staplergun() {
        super("Staplergun", 20);
    }

    public void attack(Monster monster) {
        if (monster.getHealthPoints() >= 20) {
            monster.setHealthPoints(monster.getHealthPoints() - 20);
        } else {
            monster.setHealthPoints(0);
        }
    }
}
