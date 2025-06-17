package classes.rooms.rooms;

import Challenge.ChallengeStrategy;
import classes.impediments.monsters.BossMonster;
import classes.rooms.Room;
import Challenge.MultipleChoiceChallenge;
import Challenge.CategorizationChallenge;
import Challenge.questions.Question;
import classes.impediments.Monster.*;
import classes.items.BoxCutter;
import classes.database.Database;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import classes.nonrooms.Player;
import classes.nonrooms.Game;


public class FinalRoom extends Room {
    String enter = "Press Enter to continue...";
    Scanner input = new Scanner(System.in);
    private final Player player;
    private final Database database;
    private final CategorizationChallenge challenge;
    private final Game game;

    public FinalRoom(Player player, Database database, Game game) {
        this.player = player;
        this.database = database;
        this.game = game;
        this.challenge = new CategorizationChallenge();
        this.player.setRoom(this);
    }

    @Override
    public void showIntroduction() {
        System.out.println("============================");
        System.out.println("Congratulations! You have reached the Final Room.");
        System.out.println(enter);
        input.nextLine();
        System.out.println("You have reached the end boss of the game: The Boss.");
        System.out.println(enter);
        input.nextLine();
        System.out.println("You must defeat the Boss to escape the Scrum Escape Room.");
        System.out.println("============================");
        System.out.println(enter);
        input.nextLine();
        triggerMonster();
    }

    @Override
    public void presentChallenge() {}

    @Override
    public boolean checkAnswer() {
        return false; }

    @Override
    public void giveFeedback() {
        System.out.println("You have successfully defeated the Boss!");
        System.out.println("You can now escape the Scrum Escape Room.");
        System.out.println(enter);
        input.nextLine();
        System.out.println("Thank you for playing!");
        System.out.println("Your progress has been saved.");
        System.out.println(enter);
        input.nextLine();
        try (Connection connection = Database.getConnection()) {
            player.getInventory().saveToDatabase(player.getId(), connection);
            database.updateRoomCompletion(player.getName(), "finalroom_completed", true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        game.startGame(input);
    }

    @Override
    public void searchRoom() {}

    @Override
    public void triggerMonster() {
        System.out.println("You see the Boss sitting in his chair, waiting for you.");
        System.out.println(enter);
        input.nextLine();
        System.out.println("You must answer his questions correctly to defeat him.");
        System.out.println(enter);
        input.nextLine();

        try {
            if (!player.inventory.hasItem("Box Cutter", player.getId(), database.getConnection())) {
                System.out.println("You need a weapon to defeat the Boss!");
                System.out.println("Go back to the previous room and find a Box Cutter to defeat it.");
                System.out.println(enter);
                input.nextLine();
                Room SprintReviewRoom = new SprintReviewRoom(player, database, challenge, game);
                SprintReviewRoom.setName("SprintReviewRoom");
                player.setRoom(SprintReviewRoom);
                game.handleSprintReviewRoom(input);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        BossMonster bossMonster = new BossMonster();
        BoxCutter boxCutter = new BoxCutter();

        MultipleChoiceChallenge multipleChoiceChallenge = new MultipleChoiceChallenge();
        CategorizationChallenge categorizationChallenge = new CategorizationChallenge();

        List<String> bossRoomQuestions = new ArrayList<>();
        multipleChoiceChallenge.Questions.keySet().stream()
                .filter(key -> key.startsWith("BossRoomQ") && key.matches("BossRoomQ([1-9]|10)"))
                .forEach(bossRoomQuestions::add);

        categorizationChallenge.Questions.keySet().stream()
                .filter(key -> key.startsWith("BossRoomQ") && key.matches("BossRoomQ([1-9]|10)"))
                .forEach(bossRoomQuestions::add);

        Collections.shuffle(bossRoomQuestions);

        while (player.getHp() > 0 && bossMonster.getHealthPoints() > 0 && !bossRoomQuestions.isEmpty()) {
            String selectedQuestion = bossRoomQuestions.remove(0);

            if (multipleChoiceChallenge.Questions.containsKey(selectedQuestion)) {
                multipleChoiceChallenge.showQuestion(selectedQuestion);
                System.out.println("Enter your answer:");
                String answer = input.nextLine().trim();
                List<String> playerAnswers = Collections.singletonList(answer);
                if (multipleChoiceChallenge.checkAnswer(selectedQuestion, playerAnswers)) {
                    System.out.println("Correct! You damage the Boss.");
                    boxCutter.attack(bossMonster);
                } else {
                    System.out.println("Wrong! The Boss attacks you.");
                    bossMonster.attack(player);
                }
            } else if (categorizationChallenge.Questions.containsKey(selectedQuestion)) {
                Question question = categorizationChallenge.Questions.get(selectedQuestion);
                System.out.println(question.getQuestion());

                System.out.println("Enter your answers one by one:");
                List<String> playerAnswers = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    System.out.print((i + 1) + ": ");
                    String answer = input.nextLine().trim();
                    playerAnswers.add(answer);
                }

                if (categorizationChallenge.checkAnswer(selectedQuestion, playerAnswers)) {
                    System.out.println("Correct! You damage the Boss.");
                    boxCutter.attack(bossMonster);
                } else {
                    System.out.println("Wrong! The Boss attacks you.");
                    bossMonster.attack(player);
                }
            }

            System.out.println("Player HP: " + player.getHp());
            System.out.println("Boss HP: " + bossMonster.getHealthPoints());
        }

        if (player.getHp() <= 0) {
            System.out.println("Game Over! You have been defeated by the Boss.");
            database.deletePlayer(player.getName());
            game.startGame(input);
        } else if (bossMonster.getHealthPoints() <= 0) {
            System.out.println("You defeated the Boss!");
            try (Connection connection = Database.getConnection()) {
                player.getInventory().saveToDatabase(player.getId(), connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            giveFeedback();
        }
    }
    @Override
    public void skipAssignment() {
    }
}