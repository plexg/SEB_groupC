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
import classes.items.inventory.Inventory;
import Challenge.ChallengeStrategy;
import Challenge.MultipleChoiceChallenge;
import classes.nonrooms.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SprintPlanningRoom extends Room {
    private final Player player;
    private final Database database;
    private final ChallengeStrategy challenge;
    private final List<String> playerAnswers = new ArrayList<>();
    String enter = "Press Enter to continue...";
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
            System.out.println("You have already completed the Sprint Planning Room.");
            System.out.println("You can type 'go to DailyScrumRoom', 'status' to see your status, 'go back' to return to the previous room, or 'quit' to exit the game.");
        } else {
            System.out.println("Welcome to the Sprint Planning Room!");
            System.out.println(enter);
            input.nextLine();
            System.out.println("In this room, you will need to assess the given tasks and determine which ones fit into a 2-week sprint.");
            System.out.println(enter);
            input.nextLine();
            System.out.println("Do you want to see the assignment, your status, go back to the previous room, or quit?");
            System.out.println("You can type 'assignment', 'status', 'go back', or 'quit'.");
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
        System.out.println("Would you like a hint? (yes/no)");
        String response = input.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            Hint hint = HintFactory.getRandomHint("SprintPlanningRoom");
            System.out.println("Hint: " + hint.getHint());
        } else if (!response.equals("no")) {
            System.out.println("Invalid input. Please type 'yes' or 'no'.");
        }
    }

    @Override
    public void giveFeedback() {
        while (!checkAnswer()) {
            System.out.println("Incorrect! Please try again.");
            offerHint();
            presentChallenge();
        }
        System.out.println("Correct! You can now proceed to the next room: DailyScrumRoom.");
        triggerMonster();
        System.out.println("You can type 'go to DailyScrumRoom' to enter the next room, 'status' to see your status, go back to the previous room or quit to exit the game.");
        database.updateRoomCompletion(player.getName(), "sprintplanningroom_completed", true);
        Room dailyScrumRoom = new DailyScrumRoom(player, database);
        dailyScrumRoom.setName("DailyScrumRoom");
        player.setRoom(dailyScrumRoom);
    }

    @Override
    public void searchRoom() {
        System.out.println("Searching the room...");
        System.out.println("You found a Purple key! Use this to unlock the purple lock.");
        player.addItem(purpleKey);
    }

    @Override
    public void triggerMonster() {
        System.out.println("Oh no! What's that noise?...");
        System.out.println(enter);
        input.nextLine();
        System.out.println("A Coffee Monster appears in front of the door! It looks angry...");
        System.out.println(enter);
        input.nextLine();

        if (!player.inventory.hasItem("Pencil")) {
            System.out.println("You need a weapon to defeat the Coffee Monster!");
            System.out.println("Go back to the previous room and find a Pencil to defeat it.");
            System.out.println(enter);
            input.nextLine();
            Room startRoom = new StartRoom(player);
            startRoom.setName("StartRoom");
            player.setRoom(startRoom);
            game.handleStartRoom(input);
            return;
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

        Collections.shuffle(sprintPlanningQuestions);

        while (!sprintPlanningQuestions.isEmpty() && player.getHp() > 0 && coffeeMonster.getHealthPoints() > 0) {
            String selectedQuestion = sprintPlanningQuestions.remove(0);

            if (multipleChoiceChallenge.Questions.containsKey(selectedQuestion)) {
                multipleChoiceChallenge.showQuestion(selectedQuestion);
            } else if (categorizationChallenge.Questions.containsKey(selectedQuestion)) {
                categorizationChallenge.showQuestion(selectedQuestion);
            }

            System.out.println("Enter your answer:");
            String answer = input.nextLine().trim();
            List<String> playerAnswers = new ArrayList<>();
            playerAnswers.add(answer);

            boolean isCorrect = false;
            if (multipleChoiceChallenge.Questions.containsKey(selectedQuestion)) {
                isCorrect = multipleChoiceChallenge.checkAnswer(selectedQuestion, playerAnswers);
            } else if (categorizationChallenge.Questions.containsKey(selectedQuestion)) {
                isCorrect = categorizationChallenge.checkAnswer(selectedQuestion, playerAnswers);
            }

            if (isCorrect) {
                System.out.println("Correct! You damage the Coffee Monster.");
                pencil.attack(coffeeMonster);
            } else {
                System.out.println("Wrong! The Coffee Monster attacks you.");
                coffeeMonster.attack(player);
            }

            System.out.println("Player HP: " + player.getHp());
            System.out.println("Monster HP: " + coffeeMonster.getHealthPoints());
        }

        if (player.getHp() <= 0) {
            System.out.println("Game Over! You have been defeated by the Coffee Monster.");
            database.deletePlayer(player.getName());
            game.startGame(input);
        } else if (coffeeMonster.getHealthPoints() <= 0) {
            System.out.println("You defeated the Coffee Monster!");
            System.out.println("Resetting questions for the next encounter...");
            triggerMonster();
        }
    }

    @Override
    public void giveExtraKey() {
    }
}
