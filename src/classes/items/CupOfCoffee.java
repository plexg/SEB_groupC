package classes.items;

import classes.nonrooms.Player;

public class CupOfCoffee extends Healer {
    public CupOfCoffee() {
        super("Cup of Coffee", 50);
    }

    public void heal(Player player) {
        if (player.getHp() <= 50) {
            player.setHp(player.getHp() + 50);
        } else {
            player.setHp(100);
        }
    }
}
