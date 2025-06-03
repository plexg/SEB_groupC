package classes.items;

import classes.impediments.Monster;

public interface DamageDealer extends Item{
    void attack(Monster monster);
    String getName();
}

