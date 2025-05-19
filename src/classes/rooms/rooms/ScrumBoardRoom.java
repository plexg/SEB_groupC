package classes.rooms.rooms;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import classes.nonrooms.Player;
import classes.database.Database;
import classes.rooms.Room;

public class ScrumBoardRoom extends Room {
    private Player player;
    private final Database database;

    public ScrumBoardRoom(Player player, Database database) {
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

    String enter = "Press Enter to continue...";
    Scanner input = new Scanner(System.in);

    @Override
    public void showIntroduction() {
        System.out.println("Welcome to the Scrum Board Room!");
        System.out.println(enter);
        input.nextLine();
        System.out.println("In this room, you will need to arrange the board correctly with the given tasks, user stories and epics.");
        System.out.println(enter);
        input.nextLine();
        System.out.println("Do you want to see the assignment, your status, go back to the previous room, or quit?");
        System.out.println("You can type 'assignment', 'status', 'go back', or 'quit'.");
    }

    @Override
    public void presentChallenge() {
        System.out.println("Organize the following items on a Scrum Board:");
        System.out.println("1. Display push notifications");
        System.out.println("2. Mobile App");
        System.out.println("3. Create frontend button");
        System.out.println("4. Login via Google");
        System.out.println("5. Configure API call");
        System.out.println();
        System.out.println("Place each item in the correct category (Epic, User Story, or Task) and assign the appropriate status to each User Story: To Do, In Progress, or Done.");
    }

    @Override
    public boolean checkAnswer() {
        Map<Integer, String> correctAnswers = new HashMap<>();
        correctAnswers.put(1, "User Story");
        correctAnswers.put(2, "Epic");
        correctAnswers.put(3, "Task");
        correctAnswers.put(4, "User Story");
        correctAnswers.put(5, "Task");

        Map<Integer, String> playerAnswers = new HashMap<>();
        String answer = input.nextLine();

        try {
            String[] entries = answer.split(",");
            for (String entry : entries) {
                String[] parts = entry.trim().split(":");
                int itemNumber = Integer.parseInt(parts[0].trim());
                String categoryOrStatus = parts[1].trim();
                playerAnswers.put(itemNumber, categoryOrStatus);
            }
        } catch (Exception e) {
            System.out.println("Invalid input format. Please try again.");
            return false;
        }

        return correctAnswers.entrySet().stream()
                .allMatch(entry -> entry.getValue().equalsIgnoreCase(playerAnswers.getOrDefault(entry.getKey(), "")));
    }

    @Override
    public void giveFeedback() {
        while (!checkAnswer()) {
            System.out.println("Incorrect! Let's try the assignment again.");
            presentChallenge();
        }
        System.out.println("Correct! You can now proceed to the next room: SprintReviewRoom.");
        System.out.println("You can type 'go to SprintReviewRoom' to enter the next room, 'status' to see your status, 'go back' to go to the previous room, or 'quit' to exit the game.");
        database.updateRoomCompletion(player.getName(), "scrumboardroom_completed", true);
        Room sprintReviewRoom = new SprintReviewRoom(player, database);
        sprintReviewRoom.setName("SprintReviewRoom");
        player.setRoom(sprintReviewRoom);
    }

    @Override
    public void triggerMonster() {}
}