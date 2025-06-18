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
        Scanner input = new Scanner(System.in);
        System.out.println("🌟============================🌟");
        System.out.println("🏁 Welcome to the Scrum Escape Room! 🏁");
        System.out.println("Press Enter to continue...");
        System.out.println("🌟============================🌟");
        input.nextLine();
        System.out.println("🧑‍💻 You are a Scrum Master trapped in a mysterious building full of challenges!");
        System.out.println("Press Enter to continue...");
        System.out.println("🌟============================🌟");
        input.nextLine();
        System.out.println("🧩 Your goal: Escape by solving Scrum-related puzzles and riddles!");
        System.out.println("Press Enter to continue...");
        System.out.println("🌟============================🌟");
        input.nextLine();
        System.out.println("🍀 Good luck, brave Scrum Master! 🍀");
        System.out.println("Press Enter to continue...");
        System.out.println("🌟============================🌟");
        input.nextLine();
        System.out.println("🚪 You are in the Start Room. 🚪");
        System.out.println("Press Enter to continue...");
        System.out.println("🌟============================🌟");
        input.nextLine();
        System.out.println("🤔 What will you do?");
        System.out.println("1️⃣  Go to the Sprint Planning Room");
        System.out.println("2️⃣  Check your status");
        System.out.println("3️⃣  Search the room");
        System.out.println("4️⃣  Go back to the previous room");
        System.out.println("5️⃣  Quit the game");
        System.out.println("🌟============================🌟");
        System.out.print("Enter your choice (1-5): ");
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
        System.out.println("🔍 Searching the room...");
        System.out.println("✨ You found a ✏️ Pencil and a 🗝️ White Key! ✨");
        System.out.println("Use the pencil for offense and the white key to unlock the door to the Sprint Planning Room.");
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
}