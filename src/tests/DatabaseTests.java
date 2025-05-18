package tests;

import classes.Database;

import java.util.Scanner;

public class DatabaseTests {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        Database database = new Database();
        if (input.equalsIgnoreCase("Connect")) {
            try {
                database.connect();
                System.out.println("Connection successful!");
            } catch (Exception e) {
                System.out.println("Connection failed: " + e.getMessage());
            }
        }
        if (input.equalsIgnoreCase("Delete")) {
            System.out.print("Enter the name of the player to delete: ");
            String name = sc.nextLine();
            try {
                database.deletePlayer(name);
                System.out.println("Player deleted successfully!");
            } catch (Exception e) {
                System.out.println("Deletion failed: " + e.getMessage());
            }
        }
    }
}