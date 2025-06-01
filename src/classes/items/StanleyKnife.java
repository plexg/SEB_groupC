package classes.items;

import classes.impediments.Monster;

public class StanleyKnife extends DamageDealer {
    public StanleyKnife() {
        super("Stanley Knife", 30);
    }

    public void attack(Monster monster) {
        if (monster.getHealthPoints() >= 30) {
            monster.setHealthPoints(monster.getHealthPoints() - 30);
        } else {
            monster.setHealthPoints(0);
        }
    }
}
