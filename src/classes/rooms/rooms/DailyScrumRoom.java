package classes.rooms.rooms;

import classes.items.GreenKey;
import classes.items.Item;
import classes.items.Staplergun;
import classes.nonrooms.Player;
import classes.database.Database;
import classes.rooms.Room;
import classes.hints.Hint;
import classes.hints.HintFactory;
import Challenge.CategorizationChallenge;
import Challenge.ChallengeStrategy;

import java.util.*;

public class DailyScrumRoom extends Room {
    private final Player player;
    private final Database database;
    private final ChallengeStrategy challenge;
    private final List<String> playerAnswers = new ArrayList<>();
    private final Scanner input = new Scanner(System.in);
    private final List<Item> items = new ArrayList<>();
    private final Staplergun staplergun = new Staplergun();
    private final GreenKey greenKey = new GreenKey(3);
    private final String enter = "Press Enter to continue...";

    public DailyScrumRoom(Player player, Database database) {
        this.player = player;
        this.database = database;
        this.challenge = new CategorizationChallenge();
        this.player.setRoom(this);
        items.add(staplergun);
        items.add(greenKey);
    }

    @Override
    public void showIntroduction() {
        System.out.println("Welcome to the Daily Scrum Room!");
        System.out.println(enter);
        input.nextLine();
        System.out.println("Answer questions to proceed to the next room");
        System.out.println(enter);
        input.nextLine();
        System.out.println("Do you want to see the assignment, your status, go back to the previous room, or quit?");
        System.out.println("You can type 'assignment', 'status', 'go back', or 'quit'.");
    }

    @Override
    public void presentChallenge() {
        challenge.showQuestion("DailyScrumQ1");
    }

    @Override
    public boolean checkAnswer() {
        playerAnswers.clear();

        Map<String, String> nameToLetterMap = new HashMap<>();
        nameToLetterMap.put("Kaj", "A");
        nameToLetterMap.put("Jorge", "B");
        nameToLetterMap.put("Szymon", "C");
        nameToLetterMap.put("Lex", "D");

        System.out.println("Please enter the name or letter corresponding to the status update for each number:");

        for (int i = 1; i <= 4; i++) {
            System.out.print(i + ": ");
            String answer = input.nextLine().trim();
            if (nameToLetterMap.containsKey(answer)) {
                answer = nameToLetterMap.get(answer);
            } else {
                answer = answer.toUpperCase();
            }
            playerAnswers.add(answer);
        }

        return challenge.checkAnswer("DailyScrumQ1", playerAnswers);
    }

    private void offerHint() {
        while (true) {
            System.out.println("Would you like a hint? (yes/no)");
            String response = input.nextLine().trim().toLowerCase();
            if (response.equals("yes")) {
                Hint hint = HintFactory.getRandomHint("DailyScrumRoom");
                System.out.println("Hint: " + hint.getHint());
                break;
            } else if (response.equals("no")) {
                break;
            } else {
                System.out.println("Invalid input. Please type 'yes' or 'no'.");
            }
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
        System.out.println("You can type 'go to ScrumBoardRoom' to enter the next room, 'status' to see your status, 'go back' to go to the previous room or 'quit' to exit the game.");
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
        // No monster in this room
    }

    @Override
    public void giveExtraKey() {
    }
}
