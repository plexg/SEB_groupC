package classes.items;

import classes.nonrooms.Player;

public class CupOfCoffee implements Healer {
    private String name;
    private int healAmount;
    public CupOfCoffee() {
        this.name = "Cup of Coffee";
        this.healAmount = 50;
    }

    public String getName() {
        return name;
    }

    public void heal(Player player) {
        if (player.getHp() <= healAmount) {
            player.setHp(player.getHp() + healAmount);
        } else {
            player.setHp(100);
        }
    }
}
