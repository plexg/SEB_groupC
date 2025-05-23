package classes.rooms.rooms;

import classes.database.Database;
import classes.nonrooms.Player;
import classes.rooms.Room;
import classes.hints.Hint;
import classes.hints.HintFactory;
import java.util.Random;
import java.util.Scanner;

public class ScrumBoardRoom extends Room {
    private Player player;
    private final Database database;
    private final Scanner input = new Scanner(System.in);

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
        System.out.println("Categorize the following backlog items:");
        System.out.println("1. Implement user authentication and authorization.");
        System.out.println("2. As a user, I want to reset my password so that I can regain access.");
        System.out.println("3. As an admin, I want to view all registered users.");
        System.out.println("4. Create a login page UI.");
        System.out.println("5. Write unit tests for the authentication module.");
    }

    @Override
    public boolean checkAnswer() {
        String answer = input.nextLine().toLowerCase();
        return answer.contains("1:epic") && answer.contains("2:user story") && answer.contains("3:user story")
                && answer.contains("4:task") && answer.contains("5:task");
    }

    private void offerHint() {
        System.out.println("Would you like a hint? (yes/no)");
        String response = input.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            Random rand = new Random();
            Hint hint;
            if (rand.nextBoolean()) {
                hint = HintFactory.createHint("help", "Epics are large features, user stories describe user needs, and tasks are small actionable items.");
            } else {
                hint = HintFactory.createHint("funny", "If you can't tell a task from an epic, maybe it's time for a coffee break!");
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
        System.out.println("Correct! You can now proceed to the next room: SprintReviewRoom.");
        System.out.println("You can type 'go to SprintReviewRoom' to enter the next room, status to see your status, go back to go to the previous room or quit to exit the game.");
        database.updateRoomCompletion(player.getName(), "scrumboardroom_completed", true);
        Room sprintReviewRoom = new SprintReviewRoom(player, database);
        sprintReviewRoom.setName("SprintReviewRoom");
        player.setRoom(sprintReviewRoom);
    }

    @Override
    public void triggerMonster() {
    }
}