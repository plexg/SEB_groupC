package classes.rooms.rooms;

import Challenge.ChallengeStrategy;
import classes.database.Database;
import classes.items.Item;
import classes.items.BoxCutter;
import classes.nonrooms.Game;
import classes.nonrooms.Player;
import classes.rooms.Room;
import classes.hints.Hint;
import classes.hints.HintFactory;
import classes.impediments.Monster;
import classes.impediments.ImpedimentFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class SprintReviewRoom extends Room {
    private final ChallengeStrategy challenge;
    private Player player;
    private final Database database;
    private final List<Item> items = new ArrayList<>();
    private final BoxCutter boxCutter = new BoxCutter();
    private final Game game;

    public SprintReviewRoom(Player player, Database database, ChallengeStrategy challenge, Game game) {
        this.player = player;
        this.database = database;
        this.challenge = challenge;
        this.player.setRoom(this);
        this.game = game;

        items.add(boxCutter);
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
        System.out.println("Do you want to see the assignment, your status, search the room, go back to the previous room, or quit?");
        System.out.println("You can type 'assignment', 'status', 'search room', 'go back', or 'quit'.");
    }

    @Override
    public void presentChallenge() {
        challenge.showQuestion("SprintReviewQ1");
    }

    @Override
    public boolean checkAnswer() {
        List<String> playerAnswers = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            System.out.print(i + ": ");
            String answer = input.nextLine().trim();
            playerAnswers.add(answer);
        }

        return challenge.checkAnswer("SprintReviewQ1", playerAnswers);
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
        System.out.println("You can type 'go to SprintRetrospectiveRoom' to enter the next room, 'search room' to search the room for valuable items, 'status' to see your hp, inventory and progress and 'quit' to save and exit the game.");
        database.updateRoomCompletion(player.getName(), "sprintreviewroom_completed", true);
        Room sprintRetrospectiveRoom = new SprintRetrospectiveRoom(player, database, game);
        sprintRetrospectiveRoom.setName("SprintRetrospectiveRoom");
        player.setRoom(sprintRetrospectiveRoom);
    }

    @Override
    public void searchRoom() {
        System.out.println("Searching the room...");
        System.out.println("You found a Box Cutter! Use this for offense.");
        player.addItem(boxCutter);
        try (Connection connection = Database.getConnection()) {
            player.getInventory().saveToDatabase(player.getId(), connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    @Override
    public void skipAssignment() {
        System.out.println("You have chosen to skip the assignment.");
        System.out.println("You can now proceed to the next room: SprintRetrospectiveRoom.");
        System.out.println("You can type 'go to SprintRetrospectiveRoom' to enter the next room, 'search room' to search the room for valuable items, 'status' to see your hp, inventory and progress and 'quit' to save and exit the game.");
        database.updateRoomCompletion(player.getName(), "sprintreviewroom_completed", true);
        Room sprintRetrospectiveRoom = new SprintRetrospectiveRoom(player, database, game);
        sprintRetrospectiveRoom.setName("SprintRetrospectiveRoom");
        player.setRoom(sprintRetrospectiveRoom);
    }
}