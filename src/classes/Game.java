package classes;

import classes.rooms.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Player player;
    private List<Room> rooms;
    private Database database;

    public Game() {
        this.rooms = new ArrayList<>();
        this.database = new Database();
        Player tempPlayer = new Player(0, 100, null, "temp");
        this.rooms.add(new DailyScrumRoom(tempPlayer));
        this.rooms.add(new SprintRetrospectiveRoom());
        this.rooms.add(new FinalRoom());
        this.rooms.add(new SprintPlanningRoom());
        this.rooms.add(new SprintReviewRoom());
        this.rooms.add(new ScrumBoardRoom());
    }

    public void startGame(Scanner sc) {
        System.out.println("Welcome to the Scrum Escape Building!");
        System.out.println("1. Continue");
        System.out.println("2. New Game");
        System.out.println("3. Quit");

        while (true) {
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            if (choice.equalsIgnoreCase("3") || choice.equalsIgnoreCase("Quit")) {
                System.out.println("Thank you for playing!");
                break;
            } else if (choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("Continue")) {
                continueGame(sc);
                break;
            } else if (choice.equalsIgnoreCase("2") || choice.equalsIgnoreCase("New Game")) {
                newGame(sc);
                break;
            } else {
                System.out.println("Invalid choice. Please type '1', '2', or '3'.");
            }
        }
    }

    private void continueGame(Scanner sc) {
        System.out.print("Enter your name to continue: ");
        String name = sc.nextLine();
        player = database.loadPlayer(name);
        if (player != null && player.getRoom() != null) {
            System.out.println("Continuing the game...");
            if (player.getRoom() instanceof StartRoom) {
                handleStartRoom(sc);
            } else if (player.getRoom() instanceof DailyScrumRoom) {
                handleDailyScrumRoom(sc);
            } else {
                System.out.println("Unknown room. Starting a new game.");
                newGame(sc);
            }
        } else {
            System.out.println("Player not found or no room assigned. Please start a new game.");
        }
    }

    private void newGame(Scanner sc) {
        System.out.print("What is your name? ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty. Please try again.");
            return;
        }
        player = new Player(0, 100, null, name);
        player.setName(name);
        Room startRoom = new StartRoom(player);
        player.setRoom(startRoom);
        database.savePlayer(player);

        if (player.getId() > 0) {
            System.out.println("Game is starting...");
            handleStartRoom(sc);
        } else {
            System.out.println("Failed to save the player. Please try again.");
        }
    }

    private void handleStartRoom(Scanner sc) {
        ((StartRoom) player.getRoom()).showIntroduction();
        while (true) {
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("go to DailyScrumRoom")) {
                player.setRoom(new DailyScrumRoom(player));
                handleDailyScrumRoom(sc);
                break;
            } else if (choice.equalsIgnoreCase("status")) {
                System.out.println(player.getStatus());
            } else if (choice.equalsIgnoreCase("quit")) {
                System.out.println("Saving player with name: " + player.getName());
                database.savePlayer(player);
                System.out.println("Game saved. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleDailyScrumRoom(Scanner sc) {
        ((DailyScrumRoom) player.getRoom()).showIntroduction();
        while (true) {
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("question")) {
                System.out.println("Question: Who should give the status update?");
                System.out.println("Press Enter to continue...");
                sc.nextLine();
            } else if (choice.equalsIgnoreCase("status")) {
                System.out.println(player.getStatus());
            } else if (choice.equalsIgnoreCase("go back")) {
                player.setRoom(new StartRoom(player));
                handleStartRoom(sc);
                break;
            } else if (choice.equalsIgnoreCase("quit")) {
                System.out.println("Saving player with name: " + player.getName());
                database.savePlayer(player);
                System.out.println("Game saved. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}