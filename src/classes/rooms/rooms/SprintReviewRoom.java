package classes.rooms.rooms;

import Challenge.ChallengeStrategy;
import classes.database.Database;
import classes.nonrooms.Player;
import classes.rooms.Room;
import classes.hints.Hint;
import classes.hints.HelpHint;
import classes.hints.FunnyHint;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SprintReviewRoom extends Room {
    private final List<String> feedbackItems = new ArrayList<>();
    private Player player;
    private final Database database;

    public SprintReviewRoom(Player player, Database database) {
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
        feedbackItems.clear();
        feedbackItems.add("1. The color scheme of the dashboard is not aligned with the company's branding guidelines.");
        feedbackItems.add("2. The font size of the footer text is slightly smaller than expected.");
        feedbackItems.add("3. The login functionality is not working for a significant portion of users, preventing them from accessing the application.");

        System.out.println("Determine the impact level of the following feedback from stakeholders:");
        feedbackItems.forEach(System.out::println);
        System.out.println();
        System.out.println("Assign the correct impact level (Very Impactful, Medium Impactful, Little Impactful) to each feedback item.");
        System.out.println("Format: 1:Very Impactful, 2:Medium Impactful, 3:Little Impactful");
    }

    @Override
    public boolean checkAnswer() {
        Map<String, String> correctAnswers = new HashMap<>();
        correctAnswers.put("The login functionality is not working for a significant portion of users, preventing them from accessing the application.", "Very Impactful");
        correctAnswers.put("The color scheme of the dashboard is not aligned with the company's branding guidelines.", "Medium Impactful");
        correctAnswers.put("The font size of the footer text is slightly smaller than expected.", "Little Impactful");

        Map<String, String> playerAnswers = new HashMap<>();
        String answer = input.nextLine();

        try {
            String[] entries = answer.split(",");
            for (String entry : entries) {
                String[] parts = entry.trim().split(":");
                int itemNumber = Integer.parseInt(parts[0].trim());
                String impactLevel = parts[1].trim();

                String question = feedbackItems.get(itemNumber - 1).substring(3).trim();
                playerAnswers.put(question, impactLevel);
            }
        } catch (Exception e) {
            System.out.println("Invalid input format. Please try again.");
            return false;
        }

        return correctAnswers.entrySet().stream()
                .allMatch(entry -> entry.getValue().equalsIgnoreCase(playerAnswers.getOrDefault(entry.getKey(), "")));
    }

    private void offerHint() {
        System.out.println("Would you like a hint? (yes/no)");
        String response = input.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            Random rand = new Random();
            Hint hint;
            if (rand.nextBoolean()) {
                hint = new HelpHint("Think about which issues block users, which are cosmetic, and which are in between.");
            } else {
                hint = new FunnyHint("If users can't log in, that's a big deal. If the font is small, maybe just squint!");
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
        System.out.println("Correct! You can now proceed to the next room: SprintRetrospectiveRoom.");
        System.out.println("You can type 'go to SprintRetrospectiveRoom' to enter the next room, 'status' to see your status, 'go back' to go to the previous room, or 'quit' to exit the game.");
        database.updateRoomCompletion(player.getName(), "sprintreviewroom_completed", true);
        Room sprintRetrospectiveRoom = new SprintRetrospectiveRoom(player, database);
        sprintRetrospectiveRoom.setName("SprintRetrospectiveRoom");
        player.setRoom(sprintRetrospectiveRoom);
    }

    @Override
    public void triggerMonster() {
    }
}