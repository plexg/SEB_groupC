package classes.joker;

import classes.items.Item;
import classes.rooms.Room;

public interface Joker extends Item {
    void useIn(Room room);

    String getName();
}