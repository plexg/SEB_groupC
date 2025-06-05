package classes.impediments.monsters;

import classes.impediments.Monster;
import classes.nonrooms.Player;

public class ClockMonster extends Monster {
    public ClockMonster() {
        super("Clock Monster", "It's a Clock gone wild! It shoots really sharp clock hands!",
                15,  90);
    }

    @Override
    public void attack(Player player) {
        if (player.getHp() > 0) {
            player.setHp(Math.max(player.getHp() - getAttackPower(), 0));
        }
    }
}
