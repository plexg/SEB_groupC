package classes.items;

import classes.nonrooms.Player;

public interface Healer extends Item {
    void heal(Player player);
    String getName();
}

