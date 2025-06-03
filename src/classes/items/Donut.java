package classes.items;

import classes.nonrooms.Player;

public class Donut implements Healer {
    private String name;
    private int healAmount;

    public Donut() {
        this.name = "Donut";
        this.healAmount = 20;
    }

    public String getName() {
        return name;
    }

    public void heal(Player player) {
        if (player.getHp() <= 80) {
            player.setHp(player.getHp() + 20);
        } else {
            player.setHp(100);
        }
    }
}
