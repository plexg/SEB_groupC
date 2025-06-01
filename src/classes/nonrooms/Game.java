package classes.nonrooms;

import classes.database.Database;
import classes.items.Item;
import classes.rooms.*;
import classes.rooms.rooms.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Player player;
    private List<Room> rooms;
    private Database database;
    private List<Item> items = new ArrayList<>();

    public Game() {
        this.rooms = new ArrayList<>();
        this.database = new Database();
        Player tempPlayer = new Player(0, 100, null, "temp", items);
        this.rooms.add(new DailyScrumRoom(tempPlayer, this.database));
        this.rooms.add(new SprintRetrospectiveRoom(tempPlayer, this.database));
        this.rooms.add(new FinalRoom());
        this.rooms.add(new SprintPlanningRoom(tempPlayer, this.database));
        this.rooms.add(new SprintReviewRoom(tempPlayer, this.database));
        this.rooms.add(new ScrumBoardRoom(tempPlayer, this.database));
        this.player = tempPlayer;
    }

    public void startGame(Scanner sc) {
        System.out.println("============================");
        System.out.println("Welcome to the Scrum Escape Building!");
        System.out.println("============================");
        System.out.println("1. Continue");
        System.out.println("2. New Game");
        System.out.println("3. Quit");
        System.out.println("============================");

        while (true) {
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            if (choice.equalsIgnoreCase("3") || choice.equalsIgnoreCase("Quit")) {
                System.out.println("Thank you for playing!");
                break;
            } else if (choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("Continue")) {
                System.out.print("Enter your name: ");
                String name = sc.nextLine().trim();
                Player loadedPlayer = database.loadPlayer(name);
                if (loadedPlayer != null) {
                    this.player = loadedPlayer;
                    continueGame(sc);
                } else {
                    System.out.println("No saved game found for this name. Try again or start a new game.");
                    continue;
                }
                break;
            } else if (choice.equalsIgnoreCase("2") || choice.equalsIgnoreCase("New Game")) {
                newGame(sc);
                break;
            } else {
                System.out.println("Invalid choice. Please type '1', '2', or '3'");
            }
        }
    }

    private void continueGame(Scanner sc) {
        String savedRoom = database.getPlayerRoom(player.getName());
        System.out.println("Loaded room from database: " + savedRoom);

        if (savedRoom != null) {
            switch (savedRoom.toLowerCase()) {
                case "startroom":
                    player.setRoom(new StartRoom(player));
                    handleStartRoom(sc);
                    break;
                case "sprintplanningroom":
                    player.setRoom(new SprintPlanningRoom(player, database));
                    handleSprintPlanningRoom(sc);
                    break;
                case "dailyscrumroom":
                    player.setRoom(new DailyScrumRoom(player, database));
                    handleDailyScrumRoom(sc);
                    break;
                case "scrumboardroom":
                    player.setRoom(new ScrumBoardRoom(player, database));
                    handleScrumBoardRoom(sc);
                    break;
                default:
                    System.out.println("Unknown room: " + savedRoom + ". Starting a new game.");
                    player.setRoom(new StartRoom(player));
                    handleStartRoom(sc);
            }
        } else {
            System.out.println("No saved room found. Starting a new game.");
            player.setRoom(new StartRoom(player));
            handleStartRoom(sc);
        }
    }

    private void newGame(Scanner sc) {
        while (true) {
            System.out.print("What is your name? ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Please try again.");
                continue;
            }

            if (database.loadPlayer(name) != null) {
                System.out.println("A player with this name already exists. Please choose a different name.");
                continue;
            }

            player = new Player(0, 100, null, name, items);
            Room startRoom = new StartRoom(player);
            startRoom.setName("StartRoom");
            player.setRoom(startRoom);

            boolean isSaved = database.savePlayer(player);
            if (isSaved) {
                System.out.println("Game is starting...");
                handleStartRoom(sc);
            } else {
                System.out.println("Failed to save the player. Please try again.");
            }
            break;
        }
    }

    private void handleStartRoom(Scanner sc) {
        ((StartRoom) player.getRoom()).showIntroduction();
        while (true) {
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("go to SprintPlanningRoom")) {
                Room sprintPlanningRoom = new SprintPlanningRoom(player, database);
                sprintPlanningRoom.setName("SprintPlanningRoom");
                player.setRoom(sprintPlanningRoom);
                handleSprintPlanningRoom(sc);
                break;
            } else if (choice.equalsIgnoreCase("search room")) {
                player.getRoom().searchRoom();
            } else if (choice.equalsIgnoreCase("status")) {
                System.out.println(player.getStatus());
            } else if (choice.equalsIgnoreCase("quit")) {
                saveAndQuit();
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleSprintPlanningRoom(Scanner sc) {
        ((SprintPlanningRoom) player.getRoom()).showIntroduction();
        while (true) {
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("assignment")) {
                ((SprintPlanningRoom) player.getRoom()).presentChallenge();
                System.out.println("Please write your answer below: (e.g. task 1, task 4)");
                ((SprintPlanningRoom) player.getRoom()).giveFeedback();
            } else if (choice.equalsIgnoreCase("status")) {
                System.out.println(player.getStatus());
            } else if (choice.equalsIgnoreCase("go to DailyScrumRoom")) {
                if (database.isRoomCompleted(player.getName(), "sprintplanningroom_completed")) {
                    Room dailyScrumRoom = new DailyScrumRoom(player, database);
                    dailyScrumRoom.setName("DailyScrumRoom");
                    player.setRoom(dailyScrumRoom);
                    handleDailyScrumRoom(sc);
                    break;
                } else {
                    System.out.println("You must complete the assignment in this room before proceeding.");
                }
            } else if (choice.equalsIgnoreCase("quit")) {
                saveAndQuit();
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
            if (choice.equalsIgnoreCase("assignment")) {
                ((DailyScrumRoom) player.getRoom()).presentChallenge();
                System.out.println("Please write your answer below: (e.g. 1. Lex, 2. ...)");
                ((DailyScrumRoom) player.getRoom()).giveFeedback();
            } else if (choice.equalsIgnoreCase("status")) {
                System.out.println(player.getStatus());
            } else if (choice.equalsIgnoreCase("go to ScrumBoardRoom")) {
                if (database.isRoomCompleted(player.getName(), "dailyscrumroom_completed")) {
                    Room scrumBoardRoom = new ScrumBoardRoom(player, database);
                    scrumBoardRoom.setName("ScrumBoardRoom");
                    player.setRoom(scrumBoardRoom);
                    handleScrumBoardRoom(sc);
                    break;
                } else {
                    System.out.println("You must complete the assignment in this room before proceeding.");
                }
            } else if (choice.equalsIgnoreCase("quit")) {
                saveAndQuit();
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleScrumBoardRoom(Scanner sc) {
        ((ScrumBoardRoom) player.getRoom()).showIntroduction();
        while (true) {
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("assignment")) {
                ((ScrumBoardRoom) player.getRoom()).presentChallenge();
                System.out.println("Enter your answers in the format '1:Epic, 2:User Story, 3:User Story, 4:Task, 5:Task' Below:");
                ((ScrumBoardRoom) player.getRoom()).giveFeedback();
            } else if (choice.equalsIgnoreCase("status")) {
                System.out.println(player.getStatus());
            } else if (choice.equalsIgnoreCase("go to SprintReviewRoom")) {
                if (database.isRoomCompleted(player.getName(), "scrumboardroom_completed")) {
                    Room sprintReviewRoom = new SprintReviewRoom(player, database);
                    sprintReviewRoom.setName("SprintReviewRoom");
                    player.setRoom(sprintReviewRoom);
                    handleSprintReviewRoom(sc);
                    break;
                } else {
                    System.out.println("You must complete the assignment in this room before proceeding.");
                }
            } else if (choice.equalsIgnoreCase("quit")) {
                saveAndQuit();
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleSprintRetrospectiveRoom(Scanner sc) {
        SprintRetrospectiveRoom sprintRetrospectiveRoom = (SprintRetrospectiveRoom) player.getRoom();
        sprintRetrospectiveRoom.showIntroduction();
        while (true) {
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("assignment")) {
                sprintRetrospectiveRoom.presentChallenge();
                System.out.println("Enter your answers in the format '1:A, 2:B, 3:C, 4:D, 5:E' Below:");
                sprintRetrospectiveRoom.giveFeedback();
            } else if (choice.equalsIgnoreCase("status")) {
                System.out.println(player.getStatus());
            } else if (choice.equalsIgnoreCase("go to FinalRoom")) {
                if (database.isRoomCompleted(player.getName(), "sprintretrospectiveroom_completed")) {
                    Room finalRoom = new FinalRoom();
                    finalRoom.setName("FinalRoom");
                    player.setRoom(finalRoom);
                    System.out.println("You have entered the Final Room!");
                    break;
                } else {
                    System.out.println("You must complete the assignment in this room before proceeding.");
                }
            } else if (choice.equalsIgnoreCase("quit")) {
                saveAndQuit();
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleSprintReviewRoom(Scanner sc) {
        SprintReviewRoom sprintReviewRoom = (SprintReviewRoom) player.getRoom();
        sprintReviewRoom.showIntroduction();
        while (true) {
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("assignment")) {
                sprintReviewRoom.presentChallenge();
                System.out.println("Enter your answers in the format '1:Very Impactful, 2:Medium Impactful, 3:Little Impactful' Below:");
                sprintReviewRoom.giveFeedback();
            } else if (choice.equalsIgnoreCase("status")) {
                System.out.println(player.getStatus());
            } else if (choice.equalsIgnoreCase("go to SprintRetrospectiveRoom")) {
                if (database.isRoomCompleted(player.getName(), "sprintreviewroom_completed")) {
                    Room sprintRetrospectiveRoom = new SprintRetrospectiveRoom(player, database);
                    sprintRetrospectiveRoom.setName("SprintRetrospectiveRoom");
                    player.setRoom(sprintRetrospectiveRoom);
                    handleSprintRetrospectiveRoom(sc);
                    break;
                } else {
                    System.out.println("You must complete the assignment in this room before proceeding.");
                }
            } else if (choice.equalsIgnoreCase("quit")) {
                saveAndQuit();
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void saveAndQuit() {
        System.out.println("Saving player with name: " + player.getName());
        database.updatePlayerRoom(player.getName(), player.getRoom().getName());
        database.updatePlayer(player);
        System.out.println("Game saved. Goodbye!");
    }
}

