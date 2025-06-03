package classes.rooms.rooms;

import java.util.*;

import classes.items.Donut;
import classes.items.Item;
import classes.nonrooms.Player;
import classes.database.Database;
import classes.rooms.Room;
import classes.hints.Hint;
import classes.hints.HintFactory;
import Challenge.MultipleChoiceChallenge;

public class SprintRetrospectiveRoom extends Room {
    String enter = "Press Enter to continue...";
    Scanner input = new Scanner(System.in);
    private final Database database;
    private final Player player;
    private final MultipleChoiceChallenge challenge;
    List<Item> items = new ArrayList<>();
    Donut donut = new Donut();

    public SprintRetrospectiveRoom(Player player, Database database) {
        this.player = player;
        this.database = database;
        this.challenge = new MultipleChoiceChallenge();
        this.player.setRoom(this);

        items.add(donut);
    }

    @Override
    public void showIntroduction() {
        System.out.println("Welcome to the Sprint Retrospective Room!");
        System.out.println(enter);
        input.nextLine();
        System.out.println("In this room, you will get some situations that occur in a team and must indicate what the team can learn from them.");
        System.out.println(enter);
        input.nextLine();
        System.out.println("Do you want to see the assignment, your status, go back to the previous room, or quit?");
        System.out.println("You can type 'assignment', 'status', 'go back', or 'quit'.");
    }

    @Override
    public void presentChallenge() {
        challenge.showQuestion("Room5Question1");
    }

    @Override
    public boolean checkAnswer() {
        List<String> playerAnswers = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            System.out.print(i + ": ");
            String answer = input.nextLine().trim();
            playerAnswers.add(answer);
        }

        return challenge.checkAnswer("Room5Question1", playerAnswers);
    }

    private void offerHint() {
        System.out.println("Would you like a hint? (yes/no)");
        String response = input.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            Hint hint = HintFactory.getRandomHint("SprintRetrospectiveRoom");
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
        System.out.println("Correct! You can now proceed to the final room: FinalRoom.");
        System.out.println("You can type 'go to FinalRoom' to enter the final room, 'status' to see your status, 'go back' to go to the previous room, or 'quit' to exit the game.");
        database.updateRoomCompletion(player.getName(), "sprintretrospectiveroom_completed", true);
        Room finalRoom = new FinalRoom();
        finalRoom.setName("FinalRoom");
        player.setRoom(finalRoom);
    }

    @Override
    public void searchRoom() {
        System.out.println("Searching the room...");
        System.out.println("You found a Donut! Use this to restore 20 HP.");
        player.addItem(donut);
    }

    @Override
    public void triggerMonster() {}
}