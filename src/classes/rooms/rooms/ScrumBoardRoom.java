package classes.rooms.rooms;

import classes.database.Database;
import classes.impediments.monsters.ClockMonster;
import classes.items.GoldKey;
import classes.items.Item;
import classes.nonrooms.Player;
import classes.rooms.Room;
import classes.hints.Hint;
import classes.hints.HintFactory;
import Challenge.CategorizationChallenge;
import Challenge.MultipleChoiceChallenge;
import classes.items.Staplergun;
import classes.nonrooms.Game;
import Challenge.questions.Question;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;

public class ScrumBoardRoom extends Room {
    private final Player player;
    private final Database database;
    private final CategorizationChallenge challenge;
    private final Scanner input = new Scanner(System.in);
    private final List<Item> items = new ArrayList<>();
    private final GoldKey goldKey = new GoldKey(6);
    private final ClockMonster clockMonster = new ClockMonster();
    private final Game game;

    private final String enter = "🌟 Press Enter to continue... 🌟";

    public ScrumBoardRoom(Player player, Database database, Game game) {
        this.player = player;
        this.database = database;
        this.game = game;
        this.challenge = new CategorizationChallenge();
        this.player.setRoom(this);

        items.add(goldKey);
    }

    @Override
    public void showIntroduction() {
        boolean isCompleted = database.isRoomCompleted(player.getName(), "scrumboardroom_completed");
        if (isCompleted) {
            System.out.println("🎉==============================🎉");
            System.out.println("✅ You have already completed the Scrum Board Room!");
            System.out.println("➡️ Type 'go to SprintReviewRoom', 'status' to see your status, 'go back' to return to the previous room, or 'quit' to exit the game.");
            System.out.println("🎉==============================🎉");
            System.out.println(enter);
            input.nextLine();
        } else {
            System.out.println("🎉==============================🎉");
            System.out.println("🗂️ Welcome to the Scrum Board Room! 🗂️");
            System.out.println("🎉==============================🎉");
            System.out.println(enter);
            input.nextLine();
            System.out.println("📝 In this room, you will need to categorize backlog items as Epics, User Stories, or Tasks.");
            System.out.println(enter);
            input.nextLine();
            System.out.println("🎲==============================🎲");
            System.out.println("🤔 What will you do?");
            System.out.println("1️⃣  See the assignment");
            System.out.println("2️⃣  Check your status");
            System.out.println("3️⃣  Search the room");
            System.out.println("4️⃣  Go back to the previous room");
            System.out.println("5️⃣  Quit the game");
            System.out.println("🌟============================🌟");
            System.out.print("Enter your choice (1-5): ");
        }
    }

    @Override
    public void presentChallenge() {
        challenge.showQuestion("ScrumBoardQ1");
    }

    @Override
    public boolean checkAnswer() {
        List<String> playerAnswers = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            System.out.print("✏️ " + i + ": ");
            String answer = input.nextLine().trim();
            playerAnswers.add(answer);
        }

        return challenge.checkAnswer("ScrumBoardQ1", playerAnswers);
    }

    private void offerHint() {
        System.out.println("💡 Would you like a hint? (yes/no)");
        String response = input.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            Hint hint = HintFactory.getRandomHint("ScrumBoardRoom");
            System.out.println("🧠 Hint: " + hint.getHint());
        } else if (!response.equals("no")) {
            System.out.println("❓ Invalid input. Please type 'yes' or 'no'.");
        }
    }

    @Override
    public void giveFeedback() {
        while (!checkAnswer()) {
            System.out.println("❌ Not quite! Try again!");
            offerHint();
            presentChallenge();
        }
        System.out.println("✅🎉 Correct! You can now proceed to the next room: SprintReviewRoom! 🎉✅");
        triggerMonster();
        System.out.println("➡️ Type 'go to SprintReviewRoom' to enter the next room, 'search room' to look for treasures, 'status' to check your HP, inventory and progress, or 'quit' to save and exit the game.");
        database.updateRoomCompletion(player.getName(), "scrumboardroom_completed", true);
        Room sprintReviewRoom = new SprintReviewRoom(player, database, challenge, game);
        sprintReviewRoom.setName("SprintReviewRoom");
        player.setRoom(sprintReviewRoom);
    }

    @Override
    public void searchRoom() {
        System.out.println("🔍 Searching the room...");
        System.out.println("✨ You found a 🗝️ Golden Key! Use this to unlock the door to the final room! ✨");
        player.addItem(goldKey);
        try (Connection connection = Database.getConnection()) {
            player.getInventory().saveToDatabase(player.getId(), connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void triggerMonster() {
        System.out.println("⏰ You hear a ticking sound...");
        System.out.println(enter);
        input.nextLine();
        System.out.println("🕰️ A Clock Monster appears! You must defeat it to go through the door.");
        System.out.println(enter);
        input.nextLine();
        System.out.println("⚔️ Answer the questions correctly to damage and defeat the Clock Monster!");
        System.out.println(enter);
        input.nextLine();

        try {
            if (!player.inventory.hasItem("Staplergun", player.getId(), database.getConnection())) {
                System.out.println("🔫 You need a weapon to defeat the Clock Monster!");
                System.out.println("🔙 Go back to the previous room and find a Stapler Gun to defeat it.");
                System.out.println(enter);
                input.nextLine();
                game.goBack(input);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ClockMonster clockMonster = new ClockMonster();
        Staplergun staplerGun = new Staplergun();

        MultipleChoiceChallenge multipleChoiceChallenge = new MultipleChoiceChallenge();
        CategorizationChallenge categorizationChallenge = new CategorizationChallenge();

        List<String> ScrumBoardQuestions = new ArrayList<>();
        multipleChoiceChallenge.Questions.keySet().stream()
                .filter(key -> key.startsWith("ScrumBoardQ") && key.matches("ScrumBoardQ[2-7]"))
                .forEach(ScrumBoardQuestions::add);

        categorizationChallenge.Questions.keySet().stream()
                .filter(key -> key.startsWith("ScrumBoardQ") && key.matches("ScrumBoardQ[2-7]"))
                .forEach(ScrumBoardQuestions::add);

        while (player.getHp() > 0 && clockMonster.getHealthPoints() > 0) {
            if (ScrumBoardQuestions.isEmpty()) {
                multipleChoiceChallenge.Questions.keySet().stream()
                        .filter(key -> key.startsWith("ScrumBoardQ") && key.matches("ScrumBoardQ[2-7]"))
                        .forEach(ScrumBoardQuestions::add);

                categorizationChallenge.Questions.keySet().stream()
                        .filter(key -> key.startsWith("ScrumBoardQ") && key.matches("ScrumBoardQ[2-7]"))
                        .forEach(ScrumBoardQuestions::add);

                Collections.shuffle(ScrumBoardQuestions);
            }

            String selectedQuestion = ScrumBoardQuestions.remove(0);

            if (multipleChoiceChallenge.Questions.containsKey(selectedQuestion)) {
                multipleChoiceChallenge.showQuestion(selectedQuestion);
                System.out.println("✏️ Enter your answer:");
                String answer = input.nextLine().trim();
                List<String> playerAnswers = Collections.singletonList(answer);
                if (multipleChoiceChallenge.checkAnswer(selectedQuestion, playerAnswers)) {
                    System.out.println("✅ Correct! You damage the Clock Monster! ⏰💥");
                    staplerGun.attack(clockMonster);
                } else {
                    System.out.println("❌ Wrong! The Clock Monster attacks you! ⏰👹");
                    clockMonster.attack(player);
                }
            } else if (categorizationChallenge.Questions.containsKey(selectedQuestion)) {
                Question question = categorizationChallenge.Questions.get(selectedQuestion);
                System.out.println(question.getQuestion());

                System.out.println("✏️ Enter your answers one by one:");
                List<String> playerAnswers = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    System.out.print((i + 1) + ": ");
                    String answer = input.nextLine().trim();
                    playerAnswers.add(answer);
                }

                if (categorizationChallenge.checkAnswer(selectedQuestion, playerAnswers)) {
                    System.out.println("✅ Correct! You damage the Clock Monster! ⏰💥");
                    staplerGun.attack(clockMonster);
                } else {
                    System.out.println("❌ Wrong! The Clock Monster attacks you! ⏰👹");
                    clockMonster.attack(player);
                }
            }

            System.out.println("🧑‍💻 Player HP: " + player.getHp());
            System.out.println("👹 Monster HP: " + clockMonster.getHealthPoints());
        }

        if (player.getHp() <= 0) {
            System.out.println("☠️ Game Over! You have been defeated by the Clock Monster. ☠️");
            database.deletePlayer(player.getName());
            game.startGame(input);
        } else if (clockMonster.getHealthPoints() <= 0) {
            System.out.println("🎉==============================🎉");
            System.out.println("⏰ You defeated the Clock Monster! Your stapler gun ran out! Find a new weapon.");
            System.out.println("🎉==============================🎉");
            try (Connection connection = Database.getConnection()) {
                player.inventory.removeItem(staplerGun.getName(), player.getId(), database.getConnection());
                player.getInventory().saveToDatabase(player.getId(), connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void skipAssignment() {}
}