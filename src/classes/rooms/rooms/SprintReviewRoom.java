package classes.rooms.rooms;

import Challenge.ChallengeStrategy;
import classes.database.Database;
import classes.items.Item;
import classes.items.StanleyKnife;
import classes.nonrooms.Player;
import classes.rooms.Room;
import classes.hints.Hint;
import classes.hints.HintFactory;
import classes.impediments.Monster;
import classes.impediments.ImpedimentFactory;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class SprintReviewRoom extends Room {
    private final ChallengeStrategy challenge;
    private Player player;
    private final Database database;
    private final List<Item> items = new ArrayList<>();
    private final StanleyKnife stanleyKnife = new StanleyKnife();

    public SprintReviewRoom(Player player, Database database, ChallengeStrategy challenge) {
        this.player = player;
        this.database = database;
        this.challenge = challenge;
        this.player.setRoom(this);

        items.add(stanleyKnife);
    }

    String enter = "Press Enter to continue...";
    Scanner input = new Scanner(System.in);

    @Override
    public void showIntroduction() {
        System.out.println("Welcome to the Sprint Review Room!");
        System.out.println(enter);
        input.nextLine();
        System.out.println("In this room, you will need to assess given feedback from stakeholders and estimate the impact.");
        System.out.println(enter);
        input.nextLine();
        System.out.println("Do you want to see the assignment, your status, go back to the previous room, or quit?");
        System.out.println("You can type 'assignment', 'status', 'go back', or 'quit'.");
    }

    @Override
    public void presentChallenge() {
        challenge.showQuestion("Room4Question1");
    }

    @Override
    public boolean checkAnswer() {
        List<String> playerAnswers = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            System.out.print(i + ": ");
            String answer = input.nextLine().trim();
            playerAnswers.add(answer);
        }

        return challenge.checkAnswer("Room4Question1", playerAnswers);
    }

    private void offerHint() {
        System.out.println("Would you like a hint? (yes/no)");
        String response = input.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            Hint hint = HintFactory.getRandomHint("SprintReviewRoom");
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
        System.out.println("Correct! You can now proceed to the next room: SprintRetrospectiveRoom.");
        System.out.println("You can type 'go to SprintRetrospectiveRoom' to enter the next room, 'status' to see your status, 'go back' to go to the previous room, or 'quit' to exit the game.");
        database.updateRoomCompletion(player.getName(), "sprintreviewroom_completed", true);
        Room sprintRetrospectiveRoom = new SprintRetrospectiveRoom(player, database);
        sprintRetrospectiveRoom.setName("SprintRetrospectiveRoom");
        player.setRoom(sprintRetrospectiveRoom);
    }

    @Override
    public void searchRoom() {
        System.out.println("Searching the room...");
        System.out.println("You found a Stanley Knife! Use this for offense.");
        player.addItem(stanleyKnife);
    }

    @Override
    public void triggerMonster() {
        System.out.println("Your incorrect answer has summoned a monster!");
        Monster monster = ImpedimentFactory.createRandomMonster();
        monster.encounter();
        monster.attack(player);
        System.out.println("Player HP after monster attack: " + player.getHp());

        if (player.getHp() <= 0) {
            System.out.println("Game Over! The monster defeated you.");
            System.exit(0);
        }
    }
}