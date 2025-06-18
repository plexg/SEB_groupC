package classes.rooms.rooms;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import Challenge.CategorizationChallenge;
import classes.items.Donut;
import classes.items.Item;
import classes.nonrooms.Game;
import classes.nonrooms.Player;
import classes.database.Database;
import classes.rooms.Room;
import classes.hints.Hint;
import classes.hints.HintFactory;

public class SprintRetrospectiveRoom extends Room {
    String enter = "🌟 Press Enter to continue... 🌟";
    Scanner input = new Scanner(System.in);
    private final Database database;
    private final Player player;
    private final CategorizationChallenge challenge;
    List<Item> items = new ArrayList<>();
    Donut donut = new Donut();
    private final Game game;

    public SprintRetrospectiveRoom(Player player, Database database, Game game) {
        this.player = player;
        this.database = database;
        this.game = game;
        this.challenge = new CategorizationChallenge();
        this.player.setRoom(this);

        items.add(donut);
    }

    @Override
    public void showIntroduction() {
        System.out.println("🎉==============================🎉");
        System.out.println("🔄 Welcome to the Sprint Retrospective Room! 🔄");
        System.out.println("🎉==============================🎉");
        System.out.println(enter);
        input.nextLine();
        System.out.println("💬 Here, you'll reflect on your team's journey and discover what you can learn from past sprints!");
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
        challenge.showQuestion("SprintRetrospectiveQ1");
    }

    @Override
    public boolean checkAnswer() {
        List<String> playerAnswers = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            System.out.print("✏️ " + i + ": ");
            String answer = input.nextLine().trim();
            playerAnswers.add(answer);
        }

        return challenge.checkAnswer("SprintRetrospectiveQ1", playerAnswers);
    }

    private void offerHint() {
        System.out.println("💡 Would you like a hint? (yes/no)");
        String response = input.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            Hint hint = HintFactory.getRandomHint("SprintRetrospectiveRoom");
            System.out.println("🧠 Hint: " + hint.getHint());
        }
    }

    @Override
    public void giveFeedback() {
        while (!checkAnswer()) {
            System.out.println("❌ Not quite! Give it another shot!");
            offerHint();
            presentChallenge();
        }
        System.out.println("✅🎉 Well done! You can now proceed to the final room: FinalRoom! 🎉✅");
        database.updateRoomCompletion(player.getName(), "sprintretrospectiveroom_completed", true);
    }

    @Override
    public void searchRoom() {
        System.out.println("🔍 Searching the room...");
        System.out.println("🍩 You found a Donut! Use this to restore 20 HP. 🍩");
        player.addItem(donut);
        try (Connection connection = Database.getConnection()) {
            player.getInventory().saveToDatabase(player.getId(), connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void triggerMonster() {}

    @Override
    public void skipAssignment() {}
}