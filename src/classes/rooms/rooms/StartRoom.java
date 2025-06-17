package classes.rooms.rooms;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import classes.database.Database;
import classes.items.Item;
import classes.items.Pencil;
import classes.items.WhiteKey;
import classes.joker.HintJoker;
import classes.joker.Joker;
import classes.joker.KeyJoker;
import classes.nonrooms.Player;
import classes.rooms.Room;

import javax.swing.plaf.basic.BasicTreeUI;
import java.sql.Connection;
import java.sql.SQLException;

public class StartRoom extends Room {
    private Player player;
    List<Item> items = new ArrayList<>();
    Pencil pencil = new Pencil();
    WhiteKey whiteKey = new WhiteKey(1);
    Scanner sc = new Scanner(System.in);
    HintJoker hintjoker = new HintJoker();
    KeyJoker keyjoker = new KeyJoker();



    public StartRoom(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        this.player = player;
        this.player.setRoom(this);

        // initialize items
        items.add(pencil);
        items.add(whiteKey);
        items.add(hintjoker);
        items.add(keyjoker);
    }

    @Override
    public void showIntroduction() {
        System.out.println("Choose your Joker:");
        System.out.println("1. Hint Joker (You are able to get 1 hint in a room that will help you with the assignment)");
        System.out.println("2. Key Joker (You are able to skip the room Daily Scrum Room and Sprint Review Room and go to the next one)");
        int choicejoker = sc.nextInt();
        sc.nextLine();

        Joker selectedJoker;
        if (choicejoker == 1) {
            selectedJoker = hintjoker;
        } else if (choicejoker == 2) {
            selectedJoker = keyjoker;
        } else {
            System.out.println("Invalid choice. Defaulting to Hint Joker.");
            selectedJoker = hintjoker;
        }

        player.addItem(selectedJoker);
        try (Connection connection = Database.getConnection()) {
            player.getInventory().saveToDatabase(player.getId(), connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("You have chosen the " + selectedJoker.getName() + ".");

        Scanner input = new Scanner(System.in);
        System.out.println("============================");
        System.out.println("Welcome to the Scrum Escape Room!");
        System.out.println("Press Enter to continue...");
        System.out.println("============================");
        input.nextLine();
        System.out.println("You are a Scrum Master trapped in a building with different rooms and challenges.");
        System.out.println("Press Enter to continue...");
        System.out.println("============================");
        input.nextLine();
        System.out.println("Your goal is to escape by solving Scrum-related puzzles.");
        System.out.println("Press Enter to continue...");
        System.out.println("============================");
        input.nextLine();
        System.out.println("Good luck!");
        System.out.println("Press Enter to continue...");
        System.out.println("============================");
        input.nextLine();
        System.out.println("You are in the Start Room.");
        System.out.println("Press Enter to continue...");
        System.out.println("============================");
        input.nextLine();
        System.out.println("Do you want to see the assignment, your status, \n  search the room, go back to the previous room, or quit?");
        System.out.println("============================");
    }

    @Override
    public void presentChallenge() {
    }

    @Override
    public boolean checkAnswer() {
        return false;
    }

    @Override
    public void giveFeedback() {
    }

    @Override
    public void searchRoom() {
        System.out.println("Searching the room...");
        System.out.println("You found a Pencil and a White Key! Use the pencil for offense and the white key to unlock door to the Sprint Planning Room.");
        player.addItem(pencil);
        player.addItem(whiteKey);
        try (Connection connection = Database.getConnection()) {
            player.getInventory().saveToDatabase(player.getId(), connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void triggerMonster() {
    }

    @Override
    public void skipAssignment() {
    }
}