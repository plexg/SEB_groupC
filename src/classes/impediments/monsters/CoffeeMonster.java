package classes.impediments.monsters;

import classes.impediments.Monster;
import classes.nonrooms.Player;

public class CoffeeMonster extends Monster {
    public CoffeeMonster() {
        super("Coffee Monster", "It's a coffee machine gone rogue! It shoots coffee bolts!",
                10,  50);
    }

    @Override
    public void attack(Player player) {
        if (player.getHp() > 0) {
            int damage = 10;
            player.setHp(Math.max(player.getHp() - damage, 0));
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