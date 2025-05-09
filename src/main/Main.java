package main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
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