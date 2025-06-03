package classes.rooms.rooms;

import classes.items.GreenKey;
import classes.items.Item;
import classes.items.Staplergun;
import classes.nonrooms.Player;
import classes.database.Database;
import classes.rooms.Room;
import classes.hints.Hint;
import classes.hints.HintFactory;
import Challenge.ChallengeStrategy;
import Challenge.PuzzleQuestionChallenge;

import java.util.*;

public class DailyScrumRoom extends Room {
    private final Player player;
    private final Database database;
    private final ChallengeStrategy challenge;
    private final List<String> playerAnswers = new ArrayList<>();
    String enter = "Press Enter to continue...";
    Scanner input = new Scanner(System.in);
    List<Item> items = new ArrayList<>();
    Staplergun staplergun = new Staplergun();
    GreenKey greenKey = new GreenKey(3);

    public DailyScrumRoom(Player player, Database database) {
        this.player = player;
        this.database = database;
        this.challenge = new PuzzleQuestionChallenge();
        this.player.setRoom(this);

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
        challenge.showQuestion("Room2Question1");
    }

    @Override
    public boolean checkAnswer() {
        playerAnswers.clear();

        Map<String, String> nameToLetterMap = new HashMap<>();
        nameToLetterMap.put("Kaj", "A");
        nameToLetterMap.put("Jorge", "B");
        nameToLetterMap.put("Szymon", "C");
        nameToLetterMap.put("Lex", "D");

        for (int i = 1; i <= 4; i++) {
            System.out.print(i + ": ");
            String answer = input.nextLine().trim();

            if (nameToLetterMap.containsKey(answer)) {
                answer = nameToLetterMap.get(answer);
            }

            playerAnswers.add(answer);
        }

        return challenge.checkAnswer("Room2Question1", playerAnswers);
    }

    private void offerHint() {
        System.out.println("Would you like a hint? (yes/no)");
        String response = input.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            Hint hint = HintFactory.getRandomHint("DailyScrumRoom");
            System.out.println("Hint: " + hint.getHint());
        } else if (!response.equals("no")) {
            System.out.println("Invalid input. Please type 'yes' or 'no'.");
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