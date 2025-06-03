package classes.jokers;

import classes.rooms.Room;

import java.util.List;

public class KeyJoker extends Joker {
    private final List<String> validRooms = List.of("Daily Scrum", "Review");

    @Override
    protected void performAction(Room room) {
        if (!validRooms.contains(room.getName())) {
            throw new UnsupportedOperationException("This joker cannot be used in this room.");
        }
        room.giveExtraKey();
        System.out.println("You received an extra key!");
    }
}