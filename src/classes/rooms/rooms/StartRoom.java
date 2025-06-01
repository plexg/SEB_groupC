package classes.rooms.rooms;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import classes.items.Item;
import classes.items.Pencil;
import classes.items.WhiteKey;
import classes.nonrooms.Player;
import classes.rooms.Room;

public class StartRoom extends Room {
    private Player player;
    List<Item> items = new ArrayList<>();
    Pencil pencil = new Pencil();
    WhiteKey whiteKey = new WhiteKey(1);

    public StartRoom(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        this.player = player;
        this.player.setRoom(this);

        // initialize items
        items.add(pencil);
        items.add(whiteKey);
    }

    @Override
    public void showIntroduction() {
        Scanner input = new Scanner(System.in);
        System.out.println("============================");
        System.out.println("Welcome to the Scrum Escape Room!");
        System.out.println("Press Enter to continue...");
        System.out.println("============================");
        input.nextLine();
        System.out.println("You are a Scrum Master trapped in a building with different rooms and challenges.");
        System.out.println("Press Enter to continue...");
        System.out.println("============================");
        input.nextLine();
        System.out.println("Your goal is to escape by solving Scrum-related puzzles.");
        System.out.println("Press Enter to continue...");
        System.out.println("============================");
        input.nextLine();
        System.out.println("Good luck!");
        System.out.println("Press Enter to continue...");
        System.out.println("============================");
        input.nextLine();
        System.out.println("You are in the Start Room.");
        System.out.println("Press Enter to continue...");
        System.out.println("============================");
        input.nextLine();
        System.out.println("You can type 'go to SprintPlanningRoom' to enter the next room, 'search room' to search the room for valuable items, 'status' to see your hp, inventory and progress and 'quit' to save and exit the game.");
        System.out.println("============================");
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
    public void searchRoom() {
        System.out.println("Searching the room...");
        System.out.println("You found a Pencil and a White Key! Use the pencil for offense and the white key to unlock the white lock.");
        player.addItem(pencil);
        player.addItem(whiteKey);
    }

    @Override
    public void triggerMonster() {
    }
}