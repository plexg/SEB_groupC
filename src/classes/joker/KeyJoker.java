package classes.joker;

import classes.rooms.Room;
import classes.rooms.rooms.*;

public class KeyJoker implements Joker {
    private boolean used = false;
    private String name;

    public KeyJoker(String name) {
        this.name = name;
    }

    @Override
    public void useIn(Room room) {
        if (used) {
            System.out.println("This joker has already been used.");
            return;
        }
        if (room instanceof DailyScrumRoom || room instanceof SprintReviewRoom) {
            System.out.println("You used the KeyJoker to skip the assignment in this room!");
            room.skipAssignment();
            used = true;
        } else {
            throw new UnsupportedOperationException("KeyJoker cannot be used in this room.");
        }
    }

    @Override
    public String getName() {
        return name;
    }
}