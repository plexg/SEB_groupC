package classes.items;

import classes.nonrooms.Player;

public class Donut extends Healer {
    public Donut() {
        super("Donut", 20);
    }

    public void heal(Player player) {
        if (player.getHp() <= 80) {
            player.setHp(player.getHp() + 20);
        } else {
            player.setHp(100);
        }
    }
}
