package classes.items;

import classes.impediments.Monster;

public class Pencil extends DamageDealer {
    public Pencil() {
        super("Pencil", 10);
    }

    public void attack(Monster monster) {
        if (monster.getHealthPoints() >= 10) {
            monster.setHealthPoints(monster.getHealthPoints() - 10);
        } else {
            monster.setHealthPoints(0);
        }
    }
}
