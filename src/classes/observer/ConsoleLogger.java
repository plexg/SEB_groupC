package classes.observer;

import classes.nonrooms.Player;

public class ConsoleLogger implements Observer {
    @Override
    public void update(Player player) {
        System.out.println("Player HP changed to " + player.getHp());
    }
}
