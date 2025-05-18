package classes.rooms;

import classes.Player;

import java.util.Scanner;

public class DailyScrumRoom extends Room {
    private Player player;
    String enter = "Press Enter to continue...";
    Scanner input = new Scanner(System.in);

    public DailyScrumRoom(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        this.player = player;
        this.player.setRoom(this);
    }

    @Override
    public void showIntroduction() {
        System.out.println("Welcome to the Daily Scrum Room!");
        System.out.println(enter);
        input.nextLine();
        System.out.println("In this room, you will need to answer a question about which team member should give what status-update.");
        System.out.println(enter);
        input.nextLine();
        System.out.println("Do you want to see the question, your status, or go back to the previous room?");
        System.out.println("You can type 'question', 'status', 'go back', or 'quit'.");

    }

    @Override
    public void presentChallenge() {
    }

    @Override
    public boolean checkAnswer() {
        return false;
    }

    @Override
    public void showResult() {
    }

    @Override
    public void giveFeedback() {
    }

    @Override
    public void triggerMonster() {
    }
}