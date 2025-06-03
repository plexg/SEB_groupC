package classes.jokers;

import classes.rooms.Room;

public abstract class Joker {
    private boolean used = false;

    public void useIn(Room room) {
        if (used) {
            throw new IllegalStateException("This joker has already been used.");
        }
        performAction(room);
        used = true;
    }

    protected abstract void performAction(Room room);
}