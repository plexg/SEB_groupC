package classes.rooms.rooms;

import classes.items.GreenKey;
import classes.items.Item;
import classes.items.Staplergun;
import classes.nonrooms.Game;
import classes.nonrooms.Player;
import classes.database.Database;
import classes.rooms.Room;
import classes.hints.Hint;
import classes.hints.HintFactory;
import Challenge.CategorizationChallenge;
import Challenge.ChallengeStrategy;

import java.sql.Connection;
import java.sql.SQLException;
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
    private final String enter = "🌟 Press Enter to continue... 🌟";
    private final Game game;

    public DailyScrumRoom(Player player, Database database, Game game) {
        this.player = player;
        this.database = database;
        this.game = game;
        this.challenge = new CategorizationChallenge();
        this.player.setRoom(this);
        items.add(staplergun);
        items.add(greenKey);
    }

    @Override
    public void showIntroduction() {
        System.out.println("🎉==============================🎉");
        System.out.println("⏰ Welcome to the Daily Scrum Room! ⏰");
        System.out.println("🎉==============================🎉");
        System.out.println(enter);
        input.nextLine();
        System.out.println("🗣️ Answer questions to proceed to the next room!");
        System.out.println(enter);
        input.nextLine();
        System.out.println("🎲==============================🎲");
        System.out.println("🤔 What will you do?");
        System.out.println("1️⃣  See the assignment");
        System.out.println("2️⃣  Check your status");
        System.out.println("3️⃣  Search the room");
        System.out.println("4️⃣  Go back to the previous room");
        System.out.println("5️⃣  Go to the next room");
        System.out.println("6️⃣  Quit the game");
        System.out.println("🌟============================🌟");
        System.out.print("Enter your choice (1-6): ");
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

        System.out.println("✏️ Please enter the name or letter corresponding to the status update for each number:");

        for (int i = 1; i <= 4; i++) {
            System.out.print("✏️ " + i + ": ");
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
            System.out.println("💡 Would you like a hint? (yes/no)");
            String response = input.nextLine().trim().toLowerCase();
            if (response.equals("yes")) {
                Hint hint = HintFactory.getRandomHint("DailyScrumRoom");
                System.out.println("🧠 Hint: " + hint.getHint());
                break;
            } else if (response.equals("no")) {
                break;
            } else {
                System.out.println("❓ Invalid input. Please type 'yes' or 'no'.");
            }
        }
    }

    @Override
    public void giveFeedback() {
        while (!checkAnswer()) {
            System.out.println("❌ Not quite! Let's try the assignment again!");
            offerHint();
            presentChallenge();
        }
        System.out.println("✅🎉 Correct! You can now proceed to the next room: ScrumBoardRoom! 🎉✅");
        System.out.println("🎲==============================🎲");
        System.out.println("🤔 What will you do?");
        System.out.println("1️⃣  See the assignment(already completed)");
        System.out.println("2️⃣  Check your status");
        System.out.println("3️⃣  Search the room");
        System.out.println("4️⃣  Go back to the previous room");
        System.out.println("5️⃣  Go to the next room");
        System.out.println("6️⃣  Quit the game");
        System.out.println("🌟============================🌟");
        System.out.print("Enter your choice (1-6): ");
        database.updateRoomCompletion(player.getName(), "dailyscrumroom_completed", true);

    }

    @Override
    public void searchRoom() {
        System.out.println("🔍 Searching the room...");
        System.out.println("✨ You found a 🟩 Green Key and a 🔫 Stapler Gun! Use the stapler gun for offense and the green key for the green lock. ✨");
        player.addItem(greenKey);
        player.addItem(staplergun);
        try (Connection connection = Database.getConnection()) {
            player.getInventory().saveToDatabase(player.getId(), connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void triggerMonster() {}
}