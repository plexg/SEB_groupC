package classes;

import classes.rooms.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Player player;
    private List<Room> rooms;

    public Game() {
        this.player = new Player(0, 100, null);
        this.rooms = new ArrayList<>();
        this.rooms.add(new DailyScrumRoom());
        this.rooms.add(new SprintRetrospectiveRoom());
        this.rooms.add(new FinalRoom());
        this.rooms.add(new SprintPlanningRoom());
        this.rooms.add(new SprintReviewRoom());
        this.rooms.add(new ScrumBoardRoom());
    }

    public void startGame(Scanner sc) {
        // this is the start screen of the game
        System.out.println("Welcome to the Scrum Escape Building!");
        System.out.println("Start");
        System.out.println("Quit");

        // this is the way to exit and start the game
        while (true) {
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            if (choice.equalsIgnoreCase("Quit") || choice.equalsIgnoreCase("Start")) {
                if (choice.equalsIgnoreCase("Quit")) {
                    System.out.println("Thank you for playing!");
                    break;
                }
            } else {
                System.out.println("Invalid choice. Please type 'Start' to begin or 'Quit' to exit the game.");
            }
        }
    }
}