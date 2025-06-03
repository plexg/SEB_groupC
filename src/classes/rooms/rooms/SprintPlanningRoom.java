package classes.rooms.rooms;

import classes.database.Database;
import classes.items.CupOfCoffee;
import classes.items.Item;
import classes.items.PurpleKey;
import classes.nonrooms.Player;
import classes.rooms.Room;
import classes.hints.Hint;
import classes.hints.HintFactory;
import Challenge.ChallengeStrategy;
import Challenge.MultipleChoiceChallenge;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SprintPlanningRoom extends Room {
    private final Player player;
    private final Database database;
    private final ChallengeStrategy challenge;
    private final List<String> playerAnswers = new ArrayList<>();
    String enter = "Press Enter to continue...";
    private final Scanner input = new Scanner(System.in);
    private final List<Item> items = new ArrayList<>();
    private final PurpleKey purpleKey = new PurpleKey(5);
    private final CupOfCoffee cupOfCoffee = new CupOfCoffee();

    public SprintPlanningRoom(Player player, Database database) {
        this.player = player;
        this.database = database;
        this.challenge = new MultipleChoiceChallenge();
        this.player.setRoom(this);

        items.add(purpleKey);
        items.add(cupOfCoffee);
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
        challenge.showQuestion("Room1Question1");
    }

    @Override
    public boolean checkAnswer() {
        playerAnswers.clear();

        String answer = input.nextLine().trim();
        playerAnswers.add(answer);

        return challenge.checkAnswer("Room1Question1", playerAnswers);
    }

    private void offerHint() {
        System.out.println("Would you like a hint? (yes/no)");
        String response = input.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            Hint hint = HintFactory.getRandomHint("SprintPlanningRoom");
            System.out.println("Hint: " + hint.getHint());
        } else if (!response.equals("no")) {
            System.out.println("Invalid input. Please type 'yes' or 'no'.");
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
        System.out.println("You can type 'go to DailyScrumRoom' to enter the next room, status to see your status, go back to the previous room or quit to exit the game.");
        database.updateRoomCompletion(player.getName(), "sprintplanningroom_completed", true);
        Room dailyScrumRoom = new DailyScrumRoom(player, database);
        dailyScrumRoom.setName("DailyScrumRoom");
        player.setRoom(dailyScrumRoom);
    }

    @Override
    public void searchRoom() {
        System.out.println("Searching the room...");
        System.out.println("You found a Purple key! Use this to unlock the purple lock.");
        player.addItem(purpleKey);
    }

    @Override
    public void triggerMonster() {
        System.out.println("A Coffee Monster appears! It looks angry.");
        // Add logic for monster interaction if needed
    }
}
