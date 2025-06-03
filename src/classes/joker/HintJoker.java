package classes.joker;

import classes.rooms.Room;

public class HintJoker extends classes.jokers.Joker {
    @Override
    protected void performAction(Room room) {
        System.out.println("Hint: " + room.getHint());
    }
}