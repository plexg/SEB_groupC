package classes.rooms.rooms;

import classes.database.Database;
import classes.impediments.monsters.ClockMonster;
import classes.items.GoldKey;
import classes.items.Item;
import classes.nonrooms.Player;
import classes.rooms.Room;
import classes.hints.Hint;
import classes.hints.HintFactory;
import Challenge.CategorizationChallenge;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScrumBoardRoom extends Room {
    private final Player player;
    private final Database database;
    private final CategorizationChallenge challenge;
    private final Scanner input = new Scanner(System.in);
    private final List<Item> items = new ArrayList<>();
    private final GoldKey goldKey = new GoldKey(6);
    private final ClockMonster clockMonster = new ClockMonster();

    public ScrumBoardRoom(Player player, Database database) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (database == null) {
            throw new IllegalArgumentException("Database cannot be null");
        }
        this.player = player;
        this.database = database;
        this.challenge = new CategorizationChallenge();
        this.player.setRoom(this);

        items.add(goldKey);
    }

    @Override
    public void showIntroduction() {
        boolean isCompleted = database.isRoomCompleted(player.getName(), "scrumboardroom_completed");
        if (isCompleted) {
            System.out.println("You have already completed the Scrum Board Room.");
            System.out.println("You can type 'go to SprintReviewRoom', 'status' to see your status, 'go back' to return to the previous room, or 'quit' to exit the game.");
        } else {
            System.out.println("Welcome to the Scrum Board Room!");
            System.out.println("In this room, you will need to categorize backlog items as Epics, User Stories, or Tasks.");
            System.out.println("Do you want to see the assignment, your status, go back to the previous room, or quit?");
            System.out.println("You can type 'assignment', 'status', 'go back', or 'quit'.");
        }
    }

    @Override
    public void presentChallenge() {
        challenge.showQuestion("Room3Question1");
    }

    @Override
    public boolean checkAnswer() {
        List<String> playerAnswers = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            System.out.print(i + ": ");
            String answer = input.nextLine().trim();
            playerAnswers.add(answer);
        }

        return challenge.checkAnswer("Room3Question1", playerAnswers);
    }

    private void offerHint() {
        System.out.println("Would you like a hint? (yes/no)");
        String response = input.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            Hint hint = HintFactory.getRandomHint("ScrumBoardRoom");
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
        System.out.println("Correct! You can now proceed to the next room: SprintReviewRoom.");
        System.out.println("You can type 'go to SprintReviewRoom' to enter the next room, status to see your status, go back to go to the previous room or quit to exit the game.");
        database.updateRoomCompletion(player.getName(), "scrumboardroom_completed", true);
        Room sprintReviewRoom = new SprintReviewRoom(player, database, challenge);
        sprintReviewRoom.setName("SprintReviewRoom");
        player.setRoom(sprintReviewRoom);
    }

    @Override
    public void searchRoom() {
        System.out.println("Searching the room...");
        System.out.println("You found a Golden Key! Use this to unlock the golden lock.");
        player.addItem(goldKey);
    }

    @Override
    public void triggerMonster() {
    }
}
