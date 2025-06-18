package classes.nonrooms;

import Challenge.CategorizationChallenge;
import classes.database.Database;
import classes.items.CupOfCoffee;
import classes.items.Donut;
import classes.items.Healer;
import classes.items.Item;
import classes.joker.HintJoker;
import classes.joker.Joker;
import classes.joker.KeyJoker;
import classes.observer.ConsoleLogger;
import classes.rooms.*;
import classes.rooms.rooms.*;
import Challenge.MultipleChoiceChallenge;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Player player;
    private List<Room> rooms;
    private Database database;
    private List<Item> items = new ArrayList<>();

    CupOfCoffee cupOfCoffee = new CupOfCoffee();
    Donut donut = new Donut();
    Joker hintjoker = new HintJoker();
    Joker keyjoker = new KeyJoker();
    CategorizationChallenge challenge;

    public Game() {
        this.rooms = new ArrayList<>();
        this.database = new Database();

        Player tempPlayer = new Player(0, 100, null, "temp", items);
        this.rooms.add(new DailyScrumRoom(tempPlayer, this.database, this));
        this.rooms.add(new SprintRetrospectiveRoom(tempPlayer, this.database, this));
        this.rooms.add(new FinalRoom(tempPlayer, this.database, this));
        this.rooms.add(new SprintPlanningRoom(tempPlayer, this.database, this));
        this.rooms.add(new SprintReviewRoom(tempPlayer, this.database, new CategorizationChallenge(), this));
        this.rooms.add(new ScrumBoardRoom(tempPlayer, this.database, this));

        this.player = tempPlayer;

    }

    public void startGame(Scanner sc) {
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                                     ║");
        System.out.println("║                  WELCOME TO THE SCRUM ESCAPE BUILDING!                             ║");
        System.out.println("║                                                                                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("1️⃣ Continue");
        System.out.println("2️⃣ New Game");
        System.out.println("3️⃣ Quit");
        System.out.println("🎮============================🎮");

        while (true) {
            System.out.print("👉 Enter your choice: ");
            String choice = sc.nextLine();

            if (choice.equalsIgnoreCase("3") || choice.equalsIgnoreCase("Quit")) {
                System.out.println("🙏 Thank you for playing! See you next time! 🙏");
                break;
            } else if (choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("Continue")) {
                System.out.print("📝 Enter your name: ");
                String name = sc.nextLine().trim();
                Player loadedPlayer = database.loadPlayer(name);
                if (loadedPlayer != null) {
                    this.player = loadedPlayer;
                    continueGame(sc);
                } else {
                    System.out.println("❌ No saved game found for this name. Try again or start a new game.");
                }
                break;
            } else if (choice.equalsIgnoreCase("2") || choice.equalsIgnoreCase("New Game")) {
                newGame(sc);
                break;
            } else {
                System.out.println("❓ Invalid choice. Please type '1', '2', or '3'");
            }
        }
    }

    public void continueGame(Scanner sc) {
        String savedRoom = database.getPlayerRoom(player.getName());
        System.out.println("💾 Loaded room from database: " + savedRoom);

        if (savedRoom != null) {
            switch (savedRoom.toLowerCase()) {
                case "startroom":
                    player.setRoom(new StartRoom(player));
                    handleStartRoom(sc);
                    break;
                case "sprintplanningroom":
                    player.setRoom(new SprintPlanningRoom(player, database, this));
                    handleSprintPlanningRoom(sc);
                    break;
                case "dailyscrumroom":
                    player.setRoom(new DailyScrumRoom(player, database, this));
                    handleDailyScrumRoom(sc);
                    break;
                case "scrumboardroom":
                    player.setRoom(new ScrumBoardRoom(player, database, this));
                    handleScrumBoardRoom(sc);
                    break;
                default:
                    System.out.println("❓ Unknown room: " + savedRoom + ". Starting a new game.");
                    player.setRoom(new StartRoom(player));
                    handleStartRoom(sc);
            }
        } else {
            System.out.println("❌ No saved room found. Starting a new game.");
            player.setRoom(new StartRoom(player));
            handleStartRoom(sc);
        }
    }

    public void newGame(Scanner sc) {
        while (true) {
            System.out.print("📝 What is your name? ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("❌ Name cannot be empty. Please try again.");
                continue;
            }

            if (database.loadPlayer(name) != null) {
                System.out.println("⚠️ A player with this name already exists. Please choose a different name.");
                continue;
            }

            Player newPlayer = new Player(0, 100, null, name, items);

            boolean isSaved = database.savePlayer(newPlayer);
            if (!isSaved) {
                System.out.println("❌ Failed to save the player. Please try again.");
                continue;
            }

            Player savedPlayer = database.loadPlayer(name);
            if (savedPlayer == null) {
                System.out.println("❌ Failed to load the saved player. Please try again.");
                continue;
            }
            player = savedPlayer;

            ConsoleLogger logger = new ConsoleLogger();
            player.addObserver(logger);

            System.out.println("🃏 Choose your Joker:");
            System.out.println("1️⃣ Hint Joker (Get 1 hint in a room to help with the assignment)");
            System.out.println("2️⃣ Key Joker (Skip the Daily Scrum Room and Sprint Review Room and go to the next one)");
            int choicejoker = sc.nextInt();
            sc.nextLine();

            Joker selectedJoker;
            if (choicejoker == 1) {
                selectedJoker = hintjoker;
            } else if (choicejoker == 2) {
                selectedJoker = keyjoker;
            } else {
                System.out.println("❓ Invalid choice. Defaulting to Hint Joker.");
                selectedJoker = hintjoker;
            }

            player.addItem(selectedJoker);
            try (Connection connection = Database.getConnection()) {
                player.getInventory().saveToDatabase(player.getId(), connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("🎉 You have chosen the " + selectedJoker.getName() + "! 🎉");

            Room startRoom = new StartRoom(player);
            startRoom.setName("StartRoom");
            player.setRoom(startRoom);

            System.out.println("🚀 Game is starting... 🚀");

            handleStartRoom(sc);
            break;
        }
    }

    public void handleStartRoom(Scanner sc) {
        player.setPreviousRoom(player.getRoom());
        ((StartRoom) player.getRoom()).showIntroduction();
        while (true) {
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    if (player.isKeyUsed()) {
                        Room sprintPlanningRoom = new SprintPlanningRoom(player, database, this);
                        sprintPlanningRoom.setName("SprintPlanningRoom");
                        player.setRoom(sprintPlanningRoom);
                        player.setIsKeyUsed(false);
                        handleSprintPlanningRoom(sc);
                        return;
                    } else {
                        System.out.println("🚪 The door is locked! Use the White Key to proceed.");
                    }
                    break;
                case "2":
                case "status":
                    System.out.println(player.getStatus());
                    break;
                case "3":
                case "search room":
                    player.getRoom().searchRoom();
                    break;
                case "4":
                case "go back":
                    goBack(sc);
                    return;
                case "5":
                case "quit":
                    saveAndQuit();
                    return;
                default:
                    if (choice.equalsIgnoreCase("use cup of coffee")) {
                        useItem(cupOfCoffee, "Cup of Coffee", sc);
                    } else if (choice.equalsIgnoreCase("use donut")) {
                        useItem(donut, "Donut", sc);
                    } else if (choice.equalsIgnoreCase("use hint joker")) {
                        useHintJoker(sc);
                    } else if (choice.equalsIgnoreCase("use key joker")) {
                        useKeyJoker(sc);
                    } else if (choice.equalsIgnoreCase("use white key")) {
                        try {
                            if (player.getInventory().hasItem("White Key", player.getId(), database.getConnection())) {
                                player.setIsKeyUsed(true);
                                player.getInventory().removeItem("White Key", player.getId(), database.getConnection());
                                System.out.println("🔓 You used the White Key to unlock the door to the next room!");
                            } else {
                                System.out.println("❌ You don't have a White Key.");
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("❓ Invalid choice. Please try again.");
                    }
            }
        }
    }

    public void handleSprintPlanningRoom(Scanner sc) {
        player.setPreviousRoom(player.getRoom());
        ((SprintPlanningRoom) player.getRoom()).showIntroduction();
        while (true) {
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                case "assignment":
                    ((SprintPlanningRoom) player.getRoom()).presentChallenge();
                    ((SprintPlanningRoom) player.getRoom()).giveFeedback();
                    break;
                case "2":
                case "status":
                    System.out.println(player.getStatus());
                    break;
                case "3":
                case "search room":
                    player.getRoom().searchRoom();
                    break;
                case "4":
                case "go back":
                    goBack(sc);
                    return;
                case "5":
                case "go to dailyscrumroom":
                    if (database.isRoomCompleted(player.getName(), "sprintplanningroom_completed")) {
                        Room dailyScrumRoom = new DailyScrumRoom(player, database, this);
                        dailyScrumRoom.setName("DailyScrumRoom");
                        player.setRoom(dailyScrumRoom);
                        handleDailyScrumRoom(sc);
                        return;
                    } else {
                        System.out.println("🚫 You must complete the assignment in this room before proceeding.");
                    }
                    break;
                case "6":
                case "quit":
                    saveAndQuit();
                    return;
                default:
                    if (choice.equalsIgnoreCase("use cup of coffee")) {
                        useItem(cupOfCoffee, "Cup of Coffee", sc);
                    } else if (choice.equalsIgnoreCase("use donut")) {
                        useItem(donut, "Donut", sc);
                    } else if (choice.equalsIgnoreCase("use hint joker")) {
                        useHintJoker(sc);
                    } else if (choice.equalsIgnoreCase("use key joker")) {
                        useKeyJoker(sc);
                    } else if (choice.equalsIgnoreCase("go to DailyScrumRoom")) {
                        if (database.isRoomCompleted(player.getName(), "sprintplanningroom_completed")) {
                            Room dailyScrumRoom = new DailyScrumRoom(player, database, this);
                            dailyScrumRoom.setName("DailyScrumRoom");
                            player.setRoom(dailyScrumRoom);
                            handleDailyScrumRoom(sc);
                            return;
                        } else {
                            System.out.println("🚫 You must complete the assignment in this room before proceeding.");
                        }
                    } else {
                        System.out.println("❓ Invalid choice. Please try again.");
                    }
            }
        }
    }

    public void handleDailyScrumRoom(Scanner sc) {
        player.setPreviousRoom(player.getRoom());
        ((DailyScrumRoom) player.getRoom()).showIntroduction();
        while (true) {
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                case "assignment":
                    ((DailyScrumRoom) player.getRoom()).presentChallenge();
                    ((DailyScrumRoom) player.getRoom()).giveFeedback();
                    break;
                case "2":
                case "status":
                    System.out.println(player.getStatus());
                    break;
                case "3":
                case "search room":
                    player.getRoom().searchRoom();
                    break;
                case "4":
                case "go back":
                    goBack(sc);
                    return;
                case "5":
                case "go to ScrumBoardRoom":
                    if (database.isRoomCompleted(player.getName(), "dailyscrumroom_completed") && player.isKeyUsed()) {
                        Room scrumBoardRoom = new ScrumBoardRoom(player, database, this);
                        scrumBoardRoom.setName("ScrumBoardRoom");
                        player.setRoom(scrumBoardRoom);
                        player.setIsKeyUsed(false);
                        handleScrumBoardRoom(sc);
                        return;
                    } else if (!player.isKeyUsed()) {
                        System.out.println("🚪 The door is locked! Use the Green Key to proceed.");
                    } else {
                        System.out.println("🚫 You must complete the assignment in this room before proceeding.");
                    }
                    break;
                case "6":
                case "quit":
                    saveAndQuit();
                    return;
                default:
                    if (choice.equalsIgnoreCase("use cup of coffee")) {
                        useItem(cupOfCoffee, "Cup of Coffee", sc);
                    } else if (choice.equalsIgnoreCase("use donut")) {
                        useItem(donut, "Donut", sc);
                    } else if (choice.equalsIgnoreCase("use hint joker")) {
                        useHintJoker(sc);
                    } else if (choice.equalsIgnoreCase("use key joker")) {
                        useKeyJoker(sc);
                    } else if (choice.equalsIgnoreCase("use green key")) {
                        try {
                            if (player.getInventory().hasItem("Green Key", player.getId(), database.getConnection())) {
                                player.setIsKeyUsed(true);
                                player.getInventory().removeItem("Green Key", player.getId(), database.getConnection());
                                System.out.println("🔓 You used the Green Key to unlock the door to the next room!");
                            } else {
                                System.out.println("❌ You don't have a Green Key.");
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("❓ Invalid choice. Please try again.");
                    }
            }
        }
    }

    public void handleScrumBoardRoom(Scanner sc) {
        player.setPreviousRoom(player.getRoom());
        ((ScrumBoardRoom) player.getRoom()).showIntroduction();
        while (true) {
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                case "assignment":
                    ((ScrumBoardRoom) player.getRoom()).presentChallenge();
                    ((ScrumBoardRoom) player.getRoom()).giveFeedback();
                    break;
                case "2":
                case "status":
                    System.out.println(player.getStatus());
                    break;
                case "3":
                case "search room":
                    player.getRoom().searchRoom();
                    break;
                case "4":
                case "go back":
                    goBack(sc);
                    return;
                case "5":
                case "go to next room":
                    if (database.isRoomCompleted(player.getName(), "scrumboardroom_completed")) {
                        Room sprintReviewRoom = new SprintReviewRoom(player, database, challenge, this);
                        sprintReviewRoom.setName("SprintReviewRoom");
                        player.setRoom(sprintReviewRoom);
                        handleSprintReviewRoom(sc);
                        return;
                    } else {
                        System.out.println("🚫 You must complete the assignment in this room before proceeding.");
                    }
                    break;
                case "6":
                case "quit":
                    saveAndQuit();
                    return;
                default:
                    if (choice.equalsIgnoreCase("use cup of coffee")) {
                        useItem(cupOfCoffee, "Cup of Coffee", sc);
                    } else if (choice.equalsIgnoreCase("use donut")) {
                        useItem(donut, "Donut", sc);
                    } else if (choice.equalsIgnoreCase("use hint joker")) {
                        useHintJoker(sc);
                    } else if (choice.equalsIgnoreCase("use key joker")) {
                        useKeyJoker(sc);
                    } else if (choice.equalsIgnoreCase("go to SprintReviewRoom")) {
                        if (database.isRoomCompleted(player.getName(), "scrumboardroom_completed")) {
                            Room sprintReviewRoom = new SprintReviewRoom(player, database, new CategorizationChallenge(), this);
                            sprintReviewRoom.setName("SprintReviewRoom");
                            player.setRoom(sprintReviewRoom);
                            handleSprintReviewRoom(sc);
                            return;
                        } else {
                            System.out.println("🚫 You must complete the assignment in this room before proceeding.");
                        }
                    } else {
                        System.out.println("❓ Invalid choice. Please try again.");
                    }
            }
        }
    }

    public void handleSprintReviewRoom(Scanner sc) {
        player.setPreviousRoom(player.getRoom());
        ((SprintReviewRoom) player.getRoom()).showIntroduction();
        while (true) {
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                case "assignment":
                    ((SprintReviewRoom) player.getRoom()).presentChallenge();
                    ((SprintReviewRoom) player.getRoom()).giveFeedback();
                    break;
                case "2":
                case "status":
                    System.out.println(player.getStatus());
                    break;
                case "3":
                case "search room":
                    player.getRoom().searchRoom();
                    break;
                case "4":
                case "go back":
                    goBack(sc);
                    return;
                case "5":
                case "go to next room":
                    if (database.isRoomCompleted(player.getName(), "sprintreviewroom_completed") && player.isKeyUsed()) {
                        Room sprintRetrospectiveRoom = new SprintRetrospectiveRoom(player, database, this);
                        sprintRetrospectiveRoom.setName("ScrumBoardRoom");
                        player.setRoom(sprintRetrospectiveRoom);
                        player.setIsKeyUsed(false);
                        handleSprintRetrospectiveRoom(sc);
                        return;
                    } else if (!player.isKeyUsed()) {
                        System.out.println("🚪 The door is locked! Use the Purple Key to proceed.");
                    } else {
                        System.out.println("🚫 You must complete the assignment in this room before proceeding.");
                    }
                    break;
                case "6":
                case "quit":
                    saveAndQuit();
                    return;
                default:
                    if (choice.equalsIgnoreCase("use cup of coffee")) {
                        useItem(cupOfCoffee, "Cup of Coffee", sc);
                    } else if (choice.equalsIgnoreCase("use donut")) {
                        useItem(donut, "Donut", sc);
                    } else if (choice.equalsIgnoreCase("use hint joker")) {
                        useHintJoker(sc);
                    } else if (choice.equalsIgnoreCase("use key joker")) {
                        useKeyJoker(sc);
                    } else {
                        System.out.println("❓ Invalid choice. Please try again.");
                    }
            }
        }
    }

    public void handleSprintRetrospectiveRoom(Scanner sc) {
        player.setPreviousRoom(player.getRoom());
        ((SprintRetrospectiveRoom) player.getRoom()).showIntroduction();
        while (true) {
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                case "assignment":
                    ((SprintReviewRoom) player.getRoom()).presentChallenge();
                    ((SprintReviewRoom) player.getRoom()).giveFeedback();
                    break;
                case "2":
                case "status":
                    System.out.println(player.getStatus());
                    break;
                case "3":
                case "search room":
                    player.getRoom().searchRoom();
                    break;
                case "4":
                case "go back":
                    goBack(sc);
                    return;
                case "5":
                case "go to next room":
                    if (database.isRoomCompleted(player.getName(), "sprintretrospectiveroom_completed") && player.isKeyUsed()) {
                        Room finalRoom = new FinalRoom(player, database, this);
                        finalRoom.setName("ScrumBoardRoom");
                        player.setRoom(finalRoom);
                        handleFinalRoom(sc);
                        return;
                    } else if (!player.isKeyUsed()) {
                        System.out.println("🚪 The door is locked! Use the Golden Key to proceed.");
                    } else {
                        System.out.println("🚫 You must complete the assignment in this room before proceeding.");
                    }
                    break;
                case "6":
                case "quit":
                    saveAndQuit();
                    return;
                default:
                    if (choice.equalsIgnoreCase("use cup of coffee")) {
                        useItem(cupOfCoffee, "Cup of Coffee", sc);
                    } else if (choice.equalsIgnoreCase("use donut")) {
                        useItem(donut, "Donut", sc);
                    } else if (choice.equalsIgnoreCase("use hint joker")) {
                        useHintJoker(sc);
                    } else if (choice.equalsIgnoreCase("use key joker")) {
                        useKeyJoker(sc);
                    } else {
                        System.out.println("❓ Invalid choice. Please try again.");
                    }
            }
        }
    }

    public void handleFinalRoom(Scanner sc) {
        FinalRoom finalRoom = (FinalRoom) player.getRoom();
        finalRoom.showIntroduction();
    }

    public void saveAndQuit() {
        System.out.println("💾 Saving player with name: " + player.getName());
        database.updatePlayerRoom(player.getName(), player.getRoom().getName());
        database.updatePlayer(player);
        System.out.println("✅ Game saved. Goodbye! 👋");
    }

    public void goBack(Scanner sc) {
        if (player.getPreviousRoom() != null) {

            if (player.getRoom() instanceof StartRoom) {
                player.setRoom(new StartRoom(player));
                System.out.println("🔙 Going back to: " + getPreviousRoomName());
                handleStartRoom(sc);
            } else if (player.getRoom() instanceof SprintPlanningRoom) {
                player.setRoom(new StartRoom(player));
                System.out.println("🔙 Going back to: " + getPreviousRoomName());
                handleStartRoom(sc);
            } else if (player.getRoom() instanceof DailyScrumRoom) {
                player.setRoom(new SprintPlanningRoom(player, database, this));
                System.out.println("🔙 Going back to: " + getPreviousRoomName());
                handleSprintPlanningRoom(sc);
            } else if (player.getRoom() instanceof ScrumBoardRoom) {
                player.setRoom(new DailyScrumRoom(player, database, this));
                System.out.println("🔙 Going back to: " + getPreviousRoomName());
                handleDailyScrumRoom(sc);
            } else if (player.getRoom() instanceof SprintReviewRoom) {
                player.setRoom(new ScrumBoardRoom(player, database, this));
                System.out.println("🔙 Going back to: " + getPreviousRoomName());
                handleScrumBoardRoom(sc);
            } else if (player.getRoom() instanceof SprintRetrospectiveRoom) {
                player.setRoom(new SprintReviewRoom(player, database, challenge, this));
                System.out.println("🔙 Going back to: " + getPreviousRoomName());
                handleSprintReviewRoom(sc);
            } else {
                System.out.println("❓ Unknown room type. Cannot go back.");
            }
        } else {
            System.out.println("🚫 You cannot go back from here.");
        }
    }

    public void useItem(Healer item, String itemName, Scanner sc) {
        try {
            if (player.inventory.hasItem(itemName, player.getId(), database.getConnection())) {
                item.heal(player);
                System.out.println("💊 You used a " + itemName + ". Your HP is now " + player.getHp());
                System.out.println("🌟 Press Enter to continue... 🌟");
                player.inventory.removeItem(itemName, player.getId(), database.getConnection());
                sc.nextLine();
            } else {
                System.out.println("❌ You don't have a " + itemName + " in your inventory.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void useHintJoker(Scanner sc) {
        try {
            if (player.inventory.hasItem("Hint Joker", player.getId(), database.getConnection())) {
                hintjoker.useIn(player.getRoom());
                System.out.println("🃏 You used the Hint Joker.");
                player.inventory.removeItem("Hint Joker", player.getId(), database.getConnection());
                System.out.println("🌟 Press Enter to continue... 🌟");
                sc.nextLine();
            } else {
                System.out.println("❌ You don't have a Hint Joker in your inventory.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void useKeyJoker(Scanner sc) {
        try {
            if (player.inventory.hasItem("Key Joker", player.getId(), database.getConnection())) {
                keyjoker.useIn(player.getRoom());
                System.out.println("🗝️ You used the Key Joker.");
                player.inventory.removeItem("Key Joker", player.getId(), database.getConnection());
                System.out.println("🌟 Press Enter to continue... 🌟");
                sc.nextLine();
            } else {
                System.out.println("❌ You don't have a Key Joker in your inventory.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPreviousRoomName() {
        String res;
        if (player.getRoom() instanceof StartRoom) {
            res = "StartRoom";
        } else if (player.getRoom() instanceof SprintPlanningRoom) {
            res = "StartRoom";
        } else if (player.getRoom() instanceof DailyScrumRoom) {
            res = "SprintPlanningRoom";
        } else if (player.getRoom() instanceof ScrumBoardRoom) {
            res = "DailyScrumRoom";
        } else if (player.getRoom() instanceof SprintReviewRoom) {
            res = "ScrumBoardRoom";
        } else if (player.getRoom() instanceof SprintRetrospectiveRoom) {
            res = "SprintReviewRoom";
        } else {
            res = "Unknown Room";
        }
        return res;
    }


}