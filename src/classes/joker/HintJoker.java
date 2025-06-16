package classes.joker;

import classes.rooms.Room;
import classes.hints.HintFactory;

public class HintJoker implements classes.joker.Joker {
    private boolean used = false;

    @Override
    public void useIn(Room room) {
        if (used) {
            System.out.println("This joker has already been used.");
            return;
        }
        System.out.println("Hint: " + HintFactory.getRandomHint("help").getHint());
        used = true;
    }

    @Override
    public String getName() {
        return "Hint Joker";
    }
}