package classes.rooms.rooms;

import java.util.Scanner;
import classes.nonrooms.Player;
import classes.rooms.Room;

public class StartRoom extends Room {
    private Player player;

    public StartRoom(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        this.player = player;
        this.player.setRoom(this);
    }

    @Override
    public void showIntroduction() {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Scrum Escape Room!");
        System.out.println("Press Enter to continue...");
        input.nextLine();
        System.out.println("You are a Scrum Master trapped in a building with different rooms and challenges.");
        System.out.println("Press Enter to continue...");
        input.nextLine();
        System.out.println("Your goal is to escape by solving Scrum-related puzzles.");
        System.out.println("Press Enter to continue...");
        input.nextLine();
        System.out.println("Good luck!");
        System.out.println("Press Enter to continue...");
        input.nextLine();
        System.out.println("You are in the Start Room.");
        System.out.println("Press Enter to continue...");
        input.nextLine();
        System.out.println("You can type 'go to SprintPlanningRoom' to enter the next room, 'status' to see your hp and progress and quit to save and exit the game.");
    }

    @Override
    public void presentChallenge() {
    }

    @Override
    public boolean checkAnswer() {
        return false;
    }

    @Override
    public void giveFeedback() {
    }

    @Override
    public void triggerMonster() {
    }
}