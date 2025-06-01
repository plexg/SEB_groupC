package classes.rooms.rooms;

import java.util.*;

import classes.items.Donut;
import classes.items.Item;
import classes.nonrooms.Player;
import classes.database.Database;
import classes.rooms.Room;
import classes.hints.Hint;
import classes.hints.HintFactory;

public class SprintRetrospectiveRoom extends Room {
    String enter = "Press Enter to continue...";
    Scanner input = new Scanner(System.in);
    private final Database database;
    private final Player player;
    List<Item> items = new ArrayList<>();
    Donut donut = new Donut();

    public SprintRetrospectiveRoom(Player player, Database database) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (database == null) {
            throw new IllegalArgumentException("Database cannot be null");
        }
        this.player = player;
        this.database = database;
        this.player.setRoom(this);

        // initialize items
        items.add(donut);
    }

    @Override
    public void showIntroduction() {
        System.out.println("Welcome to the Sprint Retrospective Room!");
        System.out.println(enter);
        input.nextLine();
        System.out.println("In this room, you will get some situations that occur in a team and must indicate what the team can learn from them");
        System.out.println(enter);
        input.nextLine();
        System.out.println("Do you want to see the assignment, your status, go back to the previous room, or quit?");
        System.out.println("You can type 'assignment', 'status', 'go back', or 'quit'.");
    }

    @Override
    public void presentChallenge() {
        System.out.println("Below are some situations that occurred in a team. Indicate what the team can learn from each situation:");
        System.out.println("1. A team member consistently delivered their tasks late, causing delays in the sprint.");
        System.out.println("2. The team had a heated argument during a meeting, which disrupted the workflow.");
        System.out.println("3. The team completed the sprint successfully but realized they could have communicated better with stakeholders.");
        System.out.println("4. A team member took on too many tasks, leading to burnout and incomplete work.");
        System.out.println("5. The team failed to identify a critical bug, which caused issues in production.");
        System.out.println();
        System.out.println("What lesson do you learn from each situation: A: 'Time management', B: 'Conflict resolution', C: 'Workload management', D: 'Quality assurance', E: 'Stakeholder communication'");
    }

    @Override
    public boolean checkAnswer() {
        String input = this.input.nextLine().toLowerCase();

        Map<Integer, String> correctAnswers = new HashMap<>();
        correctAnswers.put(1, "a");
        correctAnswers.put(2, "b");
        correctAnswers.put(3, "e");
        correctAnswers.put(4, "c");
        correctAnswers.put(5, "d");

        try {
            String[] entries = input.split(",");
            for (String entry : entries) {
                String[] parts = entry.trim().split(":");
                int situationNumber = Integer.parseInt(parts[0].trim());
                String lesson = parts[1].trim();

                if (!correctAnswers.get(situationNumber).equals(lesson)) {
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid input format. Please try again.");
            return false;
        }

        return true;
    }

    private void offerHint() {
        System.out.println("Would you like a hint? (yes/no)");
        String response = input.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            Random rand = new Random();
            Hint hint;
            if (rand.nextBoolean()) {
                hint = HintFactory.createHint("help", "Match each situation to the lesson: time, conflict, communication, workload, or quality.");
            } else {
                hint = HintFactory.createHint("funny", "If in doubt, just say 'communication'—it solves everything in retros!");
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