package classes.impediments.monsters;

import classes.impediments.Monster;
import classes.nonrooms.Player;

public class CoffeeMonster extends Monster {
    public CoffeeMonster() {
        super("Coffee Monster", "It's a coffee machine gone rogue! It shoots coffee bolts!",
                10,  60);
    }

    @Override
    public void attack(Player player) {
        if (player.getHp() > 0) {
            player.setHp(Math.max(player.getHp() - getAttackPower(), 0));
        }
    }
}