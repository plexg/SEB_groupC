package classes.rooms.rooms;

import Challenge.CategorizationChallenge;
import classes.database.Database;
import classes.impediments.monsters.CoffeeMonster;
import classes.items.CupOfCoffee;
import classes.items.Item;
import classes.items.Pencil;
import classes.items.PurpleKey;
import classes.nonrooms.Player;
import classes.rooms.Room;
import classes.hints.Hint;
import classes.hints.HintFactory;
import Challenge.ChallengeStrategy;
import Challenge.MultipleChoiceChallenge;
import Challenge.questions.Question;
import classes.nonrooms.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SprintPlanningRoom extends Room {
    private final Player player;
    private final Database database;
    private final ChallengeStrategy challenge;
    private final List<String> playerAnswers = new ArrayList<>();
    String enter = "🌟 Press Enter to continue... 🌟";
    private final Scanner input = new Scanner(System.in);
    private final List<Item> items = new ArrayList<>();
    private final PurpleKey purpleKey = new PurpleKey(5);
    private final CupOfCoffee cupOfCoffee = new CupOfCoffee();
    private final Game game;

    public SprintPlanningRoom(Player player, Database database, Game game) {
        this.player = player;
        this.database = database;
        this.challenge = new MultipleChoiceChallenge();
        this.player.setRoom(this);
        this.game = game;

        items.add(purpleKey);
        items.add(cupOfCoffee);
    }

    @Override
    public void showIntroduction() {
        boolean isCompleted = database.isRoomCompleted(player.getName(), "sprintplanningroom_completed");
        if (isCompleted) {
            System.out.println("🎉==============================🎉");
            System.out.println("✅ You have already completed the Sprint Planning Room!");
            System.out.println("➡️ Type 'go to DailyScrumRoom', 'status' to see your status, 'go back' to return to the previous room, or 'quit' to exit the game.");
            System.out.println("🎉==============================🎉");
        } else {
            System.out.println("🎉==============================🎉");
            System.out.println("🗓️ Welcome to the Sprint Planning Room! 🗓️");
            System.out.println("🎉==============================🎉");
            System.out.println(enter);
            input.nextLine();
            System.out.println("📝 In this room, you will need to assess the given tasks and determine which ones fit into a 2-week sprint.");
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
    }

    @Override
    public void presentChallenge() {
        challenge.showQuestion("SprintPlanningQ1");
    }

    @Override
    public boolean checkAnswer() {
        playerAnswers.clear();
        String answer = input.nextLine().trim();
        playerAnswers.add(answer);
        return challenge.checkAnswer("SprintPlanningQ1", playerAnswers);
    }

    private void offerHint() {
        System.out.println("💡 Would you like a hint? (yes/no)");
        String response = input.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            Hint hint = HintFactory.getRandomHint("SprintPlanningRoom");
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
        System.out.println("✅🎉 Correct! You can now proceed to the next room: DailyScrumRoom! 🎉✅");
        triggerMonster();
        System.out.println("🎲==============================🎲");
        System.out.println("🤔 What will you do?");
        System.out.println("1️⃣  See the assignment(Already completed)");
        System.out.println("2️⃣  Check your status");
        System.out.println("3️⃣  Search the room");
        System.out.println("4️⃣  Go back to the previous room");
        System.out.println("5️⃣  Go to the next room");
        System.out.println("6️⃣  Quit the game");
        System.out.println("🌟============================🌟");
        System.out.print("Enter your choice (1-6): ");
        database.updateRoomCompletion(player.getName(), "sprintplanningroom_completed", true);
    }

    @Override
    public void searchRoom() {
        System.out.println("🔍 Searching the room...");
        System.out.println("🔑 You found a Purple Key! Use this to unlock the door to the Sprint Retrospective Room. 🔑");
        player.addItem(purpleKey);
        try (Connection connection = Database.getConnection()) {
            player.getInventory().saveToDatabase(player.getId(), connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void triggerMonster() {
        System.out.println("😱 Oh no! What's that noise?...");
        System.out.println(enter);
        input.nextLine();
        System.out.println("                                                            \n" +
                "                                                            \n" +
                "                                                            \n" +
                "           #@@@@@@@@@@%@@@@@@@@@@@@@@@@@@@@@@@@@#           \n" +
                "         ==         + :*-  :-*+   :**            -+         \n" +
                "         *          :+@%%= -@=@+ =#@*@            *         \n" +
                "         *             ..    :.    .:             *         \n" +
                "         *@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@#         \n" +
                "            *                                  *            \n" +
                "            @====-===@@%%%#%%%%%%#%%%@@===--===@            \n" +
                "            *         :#************%:       % *            \n" +
                "            *            #@@@@@@@@#         .@ *            \n" +
                "            *                                = *            \n" +
                "            *                .                 *            \n" +
                "            *        -====--*==*=-====:        *            \n" +
                "            *       -#               .@@*      *            \n" +
                "            *        @.-:            #+ @      *            \n" +
                "            *        @=#-@=       +*-@  @      *            \n" +
                "            *        -@*@-   :-      % **      *            \n" +
                "            *         @.            #=#@       *            \n" +
                "            *         .*     --     @          *            \n" +
                "            *          *-          @           *            \n" +
                "            *           +%       :@            *            \n" +
                "            *          @@@@@@@@@@@@@@          *            \n" +
                "           :%        *@=------------=@*        %-           \n" +
                "         +==.            ---::::---              .*         \n" +
                "          @+-.          :=--------=-          #@=@          \n" +
                "            :==================================:            \n" +
                "                                                            \n" +
                "                                                            \n" +
                "                                                            \n" +
                "                                                            \n" +
                "                                                            ");
        System.out.println("☕ A Coffee Monster appears in front of the door! It looks angry...");
        System.out.println(enter);
        input.nextLine();
        System.out.println("⚔️ Answer the questions correctly to damage and defeat the Coffee Monster and escape the room!");
        System.out.println(enter);
        input.nextLine();

        try {
            if (!player.inventory.hasItem("Pencil", player.getId(), database.getConnection())) {
                System.out.println("🖊️ You need a weapon to defeat the Coffee Monster!");
                System.out.println("🔙 Go back to the previous room and find a Pencil to defeat it.");
                System.out.println(enter);
                input.nextLine();
                game.goBack(input);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        CoffeeMonster coffeeMonster = new CoffeeMonster();
        Pencil pencil = new Pencil();

        MultipleChoiceChallenge multipleChoiceChallenge = new MultipleChoiceChallenge();
        CategorizationChallenge categorizationChallenge = new CategorizationChallenge();

        List<String> sprintPlanningQuestions = new ArrayList<>();
        multipleChoiceChallenge.Questions.keySet().stream()
                .filter(key -> key.startsWith("SprintPlanningQ") && key.matches("SprintPlanningQ[2-7]"))
                .forEach(sprintPlanningQuestions::add);

        categorizationChallenge.Questions.keySet().stream()
                .filter(key -> key.startsWith("SprintPlanningQ") && key.matches("SprintPlanningQ[2-7]"))
                .forEach(sprintPlanningQuestions::add);

        while (player.getHp() > 0 && coffeeMonster.getHealthPoints() > 0) {
            if (sprintPlanningQuestions.isEmpty()) {
                multipleChoiceChallenge.Questions.keySet().stream()
                        .filter(key -> key.startsWith("SprintPlanningQ") && key.matches("SprintPlanningQ[2-7]"))
                        .forEach(sprintPlanningQuestions::add);

                categorizationChallenge.Questions.keySet().stream()
                        .filter(key -> key.startsWith("SprintPlanningQ") && key.matches("SprintPlanningQ[2-7]"))
                        .forEach(sprintPlanningQuestions::add);

                Collections.shuffle(sprintPlanningQuestions);
            }

            String selectedQuestion = sprintPlanningQuestions.remove(0);

            if (multipleChoiceChallenge.Questions.containsKey(selectedQuestion)) {
                multipleChoiceChallenge.showQuestion(selectedQuestion);
                System.out.println("✏️ Enter your answer:");
                String answer = input.nextLine().trim();
                List<String> playerAnswers = Collections.singletonList(answer);
                if (multipleChoiceChallenge.checkAnswer(selectedQuestion, playerAnswers)) {
                    System.out.println("✅ Correct! You damage the Coffee Monster! ☕💥");
                    pencil.attack(coffeeMonster);
                } else {
                    System.out.println("❌ Wrong! The Coffee Monster attacks you! ☕👹");
                    coffeeMonster.attack(player);
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
                    System.out.println("✅ Correct! You damage the Coffee Monster! ☕💥");
                    pencil.attack(coffeeMonster);
                } else {
                    System.out.println("❌ Wrong! The Coffee Monster attacks you! ☕👹");
                    coffeeMonster.attack(player);
                }
            }

            System.out.println("🧑‍💻 Player HP: " + player.getHp());
            System.out.println("👹 Monster HP: " + coffeeMonster.getHealthPoints());
        }

        if (player.getHp() <= 0) {
            System.out.println("☠️ Game Over! You have been defeated by the Coffee Monster. ☠️");
            database.deletePlayer(player.getName());
            game.startGame(input);
        } else if (coffeeMonster.getHealthPoints() <= 0) {
            System.out.println("🎉 You defeated the Coffee Monster! 🎉");
            System.out.println("☕ You received a Cup of Coffee and your Pencil broke! Use the Cup of Coffee to heal yourself for 50 HP and find a new weapon for the next challenge.");
            player.addItem(cupOfCoffee);
            try (Connection connection = Database.getConnection()) {
                player.getInventory().saveToDatabase(player.getId(), connection);
                player.inventory.removeItem(pencil.getName(), player.getId(), database.getConnection());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}