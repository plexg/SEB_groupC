package classes.items;

import classes.impediments.Monster;

public class StanleyKnife implements DamageDealer {
    private String name;
    private int damage;

    public StanleyKnife() {
        this.name = "Stanley Knife";
        this.damage = 30;
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
