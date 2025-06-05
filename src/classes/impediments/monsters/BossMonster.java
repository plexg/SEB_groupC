package classes.impediments.monsters;

import classes.impediments.Monster;
import classes.nonrooms.Player;

public class BossMonster extends Monster {
    public BossMonster() {
        super("The BOSS", "It's the Boss! He is the final challenge of this maze. Defeat him to escape!",
                15,  200);
    }

    @Override
    public void attack(Player player) {
        if (player.getHp() > 0) {
            player.setHp(Math.max(player.getHp() - getAttackPower(), 0));
        }
    }
}