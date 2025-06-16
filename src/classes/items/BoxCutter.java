package classes.items;

import classes.impediments.Monster;

public class BoxCutter implements DamageDealer {
    private String name;
    private int damage;

    public BoxCutter() {
        this.name = "Box Cutter";
        this.damage = 40;
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
