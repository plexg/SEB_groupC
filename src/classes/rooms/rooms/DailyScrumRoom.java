package classes.rooms.rooms;

import classes.items.GreenKey;
import classes.items.Item;
import classes.items.Staplergun;
import classes.nonrooms.Player;
import classes.database.Database;
import classes.rooms.Room;
import classes.hints.Hint;
import classes.hints.HintFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class DailyScrumRoom extends Room {
    private final Player player;
    private final Database database;
    String enter = "Press Enter to continue...";
    Scanner input = new Scanner(System.in);
    List<Item> items = new ArrayList<>();
    Staplergun staplergun = new Staplergun();
    GreenKey greenKey = new GreenKey(3);

    public DailyScrumRoom(Player player, Database database) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (database == null) {
            throw new IllegalArgumentException("Database cannot be null");
        }
        this.player = player;
        this.database = database;
        this.player.setRoom(this);

        // initialize items
        items.add(staplergun);
        items.add(greenKey);
    }

    @Override
    public void showIntroduction() {
        System.out.println("Welcome to the Daily Scrum Room!");
        System.out.println(enter);
        input.nextLine();
        System.out.println("In this room, you will need to answer an assignment about which team member should give what status-update.");
        System.out.println(enter);
        input.nextLine();
        System.out.println("Do you want to see the assignment, your status, go back to the previous room, or quit?");
        System.out.println("You can type 'assignment', 'status', 'go back', or 'quit'.");
    }

    @Override
    public void presentChallenge() {
        System.out.println("Your team consists of:");
        System.out.println("Kaj (Developer)");
        System.out.println("Jorge (Tester)");
        System.out.println("Szymon (Designer)");
        System.out.println("Lex (Scrum Master)");
        System.out.println();
        System.out.println("Match the following status updates to the correct team member:");
        System.out.println("1. 'I conducted the sprint planning meeting.'");
        System.out.println("2. 'I have completed writing two test cases.'");
        System.out.println("3. 'I am still working on the API and fixing a bug.'");
        System.out.println("4. 'I have finalized the new dashboard design.'");
        System.out.println();
        System.out.println("Enter the name of the team member for each status update.");
    }

    @Override
    public boolean checkAnswer() {
        String[] correctAnswers = {"Lex", "Jorge", "Kaj", "Szymon"};
        String[] playerAnswers = new String[4];

        for (int i = 0; i < 4; i++) {
            System.out.print((i + 1) + ": ");
            playerAnswers[i] = input.nextLine().trim();
        }

        for (int i = 0; i < 4; i++) {
            if (!playerAnswers[i].equalsIgnoreCase(correctAnswers[i])) {
                return false;
            }
        }

        return true;
    }

    private void offerHint() {
        System.out.println("Would you like a hint? (yes/no)");
        String response = input.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            Hint hint = HintFactory.getRandomHint("DailyScrumRoom");
            System.out.println("Hint: " + hint.getHint());
        }
    }

    @Override
    public void giveFeedback() {
        while (!checkAnswer()) {
            System.out.println("Incorrect! Let's try the assignment again.");
            offerHint();
            presentChallenge();
        }
        System.out.println("Correct! You can now proceed to the next room: ScrumBoardRoom.");
        System.out.println("You can type 'go to ScrumBoardRoom' to enter the next room, status to see your status, go back to go to the previous room or quit to exit the game.");
        database.updateRoomCompletion(player.getName(), "dailyscrumroom_completed", true);
        Room scrumBoardRoom = new ScrumBoardRoom(player, database);
        scrumBoardRoom.setName("ScrumBoardRoom");
        player.setRoom(scrumBoardRoom);
    }

    @Override
    public void searchRoom() {
        System.out.println("Searching the room...");
        System.out.println("You found a Green key and a stapler gun! Use the stapler gun for offense and the green key for the green lock.");
        player.addItem(greenKey);
        player.addItem(staplergun);
    }

    @Override
    public void triggerMonster() {

    }
}