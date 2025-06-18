package classes.rooms.rooms;

import Challenge.CategorizationChallenge;
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
    private final CategorizationChallenge challenge;
    private Player player;
    private final Database database;
    private final List<Item> items = new ArrayList<>();
    private final BoxCutter boxCutter = new BoxCutter();
    private final Game game;

    public SprintReviewRoom(Player player, Database database, CategorizationChallenge challenge, Game game) {
        this.player = player;
        this.database = database;
        this.challenge = challenge;
        this.player.setRoom(this);
        this.game = game;
        items.add(boxCutter);
    }

    String enter = "🌟 Press Enter to continue... 🌟";
    Scanner input = new Scanner(System.in);

    @Override
    public void showIntroduction() {
        System.out.println("🎉==============================🎉");
        System.out.println("🏆 Welcome to the Sprint Review Room! 🏆");
        System.out.println("🎉==============================🎉");
        System.out.println(enter);
        input.nextLine();
        System.out.println("🗣️ Here, you'll assess feedback from stakeholders and estimate its impact!");
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
        challenge.showQuestion("SprintReviewQ1");
    }

    @Override
    public boolean checkAnswer() {
        List<String> playerAnswers = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            System.out.print("✏️ " + i + ": ");
            String answer = input.nextLine().trim();
            playerAnswers.add(answer);
        }
        return challenge.checkAnswer("SprintReviewQ1", playerAnswers);
    }

    private void offerHint() {
        System.out.println("💡 Would you like a hint? (yes/no)");
        String response = input.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            Hint hint = HintFactory.getRandomHint("SprintReviewRoom");
            System.out.println("🧠 Hint: " + hint.getHint());
        }
    }

    @Override
    public void giveFeedback() {
        while (!checkAnswer()) {
            System.out.println("❌ Oops! That's not quite right. Try again!");
            offerHint();
            presentChallenge();
        }
        System.out.println("✅🎉 Correct! You can now proceed to the next room: SprintRetrospectiveRoom! 🎉✅");
        database.updateRoomCompletion(player.getName(), "sprintreviewroom_completed", true);
    }

    @Override
    public void searchRoom() {
        System.out.println("🔍 Searching the room...");
        System.out.println("✨ You found a 🔪 Box Cutter! Use this for offense. ✨");
        player.addItem(boxCutter);
        try (Connection connection = Database.getConnection()) {
            player.getInventory().saveToDatabase(player.getId(), connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void triggerMonster() {
        System.out.println("👾 Uh-oh! Your incorrect answer has summoned a monster!");
        Monster monster = ImpedimentFactory.createRandomMonster();
        monster.encounter();
        monster.attack(player);
        System.out.println("💔 Player HP after monster attack: " + player.getHp());

        if (player.getHp() <= 0) {
            System.out.println("☠️ Game Over! The monster defeated you. ☠️");
            System.exit(0);
        }
    }

    @Override
    public void skipAssignment() {
        System.out.println("⏭️ You have chosen to skip the assignment.");
        System.out.println("➡️ Proceed to the next room: SprintRetrospectiveRoom!");
        System.out.println("Type 'go to SprintRetrospectiveRoom' to continue, 'search room' to look for treasures,");
        System.out.println("   'status' to check your HP, inventory, and progress, or 'quit' to save and exit.");
        database.updateRoomCompletion(player.getName(), "sprintreviewroom_completed", true);
        Room sprintRetrospectiveRoom = new SprintRetrospectiveRoom(player, database, game);
        sprintRetrospectiveRoom.setName("SprintRetrospectiveRoom");
        player.setRoom(sprintRetrospectiveRoom);
    }
}