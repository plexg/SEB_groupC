package classes.impediments.monsters;

import classes.impediments.Monster;
import classes.items.DamageDealer;
import classes.nonrooms.Player;

public class ClockMonster extends Monster {
    public ClockMonster() {
        super("Clock Monster", "It's a Clock gone wild! It shoots really sharp clock hands!",
                15,  75);
    }

    @Override
    public void attack(Player player) {
        if (player.getHp() > 0) {
            player.setHp(Math.max(player.getHp() - getAttackPower(), 0));
        }
    }

    public void encounter() {
        System.out.println("=== MONSTER ENCOUNTERED ===");
        System.out.println("You encountered " + getName() + "!");
        System.out.println(getDescription());
        System.out.println("Monster HP: " + getHealthPoints());
        System.out.println("Attack Power: " + getAttackPower());
        System.out.println("==========================");
    }
}
