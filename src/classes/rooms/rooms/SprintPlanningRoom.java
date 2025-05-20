package classes.rooms.rooms;

import classes.database.Database;
import classes.nonrooms.Player;
import classes.rooms.Room;
import classes.hints.Hint;
import classes.hints.HelpHint;
import classes.hints.FunnyHint;
import java.util.Random;
import java.util.Scanner;

public class SprintPlanningRoom extends Room {
    private Player player;
    private final Database database;
    String enter = "Press Enter to continue...";
    private final Scanner input = new Scanner(System.in);

    public SprintPlanningRoom(Player player, Database database) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (database == null) {
            throw new IllegalArgumentException("Database cannot be null");
        }
        this.player = player;
        this.database = database;
        this.player.setRoom(this);
    }

    @Override
    public void showIntroduction() {
        boolean isCompleted = database.isRoomCompleted(player.getName(), "sprintplanningroom_completed");
        if (isCompleted) {
            System.out.println("You have already completed the Sprint Planning Room.");
            System.out.println("You can type 'go to DailyScrumRoom', 'status' to see your status, 'go back' to return to the previous room, or 'quit' to exit the game.");
        } else {
            System.out.println("Welcome to the Sprint Planning Room!");
            System.out.println(enter);
            input.nextLine();
            System.out.println("In this room, you will need to assess the given tasks and determine which ones fit into a 2-week sprint.");
            System.out.println(enter);
            input.nextLine();
            System.out.println("Do you want to see the assignment, your status, go back to the previous room, or quit?");
            System.out.println("You can type 'assignment', 'status', 'go back', or 'quit'.");
        }
    }

    @Override
    public void presentChallenge() {
        System.out.println("Which tasks fit in a 2 week sprint?");
        System.out.println("task 1: Add a main-class where you can start your project.");
        System.out.println("task 2: Make every class that corresponds with the User.");
        System.out.println("task 3: Add an admin GUI that shows different graphs and information about your project.");
        System.out.println("task 4: Sort your project in several named packages so it’s uncluttered.");
        System.out.println("task 5: Make a Database with the column user, containing userID & username");
    }

    @Override
    public boolean checkAnswer() {
        String input = this.input.nextLine().toLowerCase();
        return input.contains("task 1") && input.contains("task 4") && input.contains("task 5")
                && !input.contains("task 2") && !input.contains("task 3");
    }

    private void offerHint() {
        System.out.println("Would you like a hint? (yes/no)");
        String response = input.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            Random rand = new Random();
            Hint hint;
            if (rand.nextBoolean()) {
                hint = new HelpHint("Focus on tasks that are small and essential for a first sprint.");
            } else {
                hint = new FunnyHint("Even the best developers need coffee breaks. Maybe your answer does too!");
            }
            System.out.println("Hint: " + hint.getHint());
        }
    }

    @Override
    public void giveFeedback() {
        while (!checkAnswer()) {
            System.out.println("Incorrect! Please try again.");
            offerHint();
            presentChallenge();
        }
        System.out.println("Correct! You can now proceed to the next room: DailyScrumRoom.");
        System.out.println("You can type 'go to DailyScrumRoom' to enter the next room, status to see your status, go back to go to the previous room or quit to exit the game.");
        database.updateRoomCompletion(player.getName(), "sprintplanningroom_completed", true);
        Room dailyScrumRoom = new DailyScrumRoom(player, database);
        dailyScrumRoom.setName("DailyScrumRoom");
        player.setRoom(dailyScrumRoom);
    }

    @Override
    public void triggerMonster() {
    }
}