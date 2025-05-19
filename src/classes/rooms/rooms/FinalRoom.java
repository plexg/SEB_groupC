package classes.rooms.rooms;

import classes.rooms.Room;

import java.util.Scanner;

public class FinalRoom extends Room {
    String enter = "Press Enter to continue...";
    Scanner input = new Scanner(System.in);
    @Override
    public void showIntroduction() {}

    @Override
    public void presentChallenge() {}

    @Override
    public boolean checkAnswer() {
        return false;}

    @Override
    public void giveFeedback() {}

    @Override
    public void triggerMonster() {}
}