package classes.joker;

import classes.rooms.Room;

public class KeyJoker implements Joker {
    private boolean used = false;
    private String name;

    public KeyJoker() {
        this.name = "Key Joker";
    }

    @Override
    public void useIn(Room room) {

    }

    @Override
    public String getName() {
        return name;
    }
}