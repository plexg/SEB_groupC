package classes.rooms;
import java.util.Scanner;

public class SprintPlanningRoom extends Room {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void showIntroduction() {
        System.out.println("Welcomee to the Sprint Planning Room!");
    }

    @Override
    public void presentChallenge() {
        System.out.println("Which tasks fit in a 2 week sprint?");
    }

    @Override
    public boolean checkAnswer() {
        String input = scanner.nextLine().toLowerCase();
        return input.contains("task a") && input.contains("task b") && !input.contains("task d");
    }

    @Override
    public void showResult() {
        System.out.println("Good! Your sprint planning is realistic.");
    }

    @Override
    public void giveFeedback() {
        System.out.println("Remember to size your tasks appropriately!");
    }

    @Override
    public void triggerMonster() {
        System.out.println("You triggered the monster!");
        System.out.println("Solve this to defeat it");
        String input = scanner.nextLine().toLowerCase();
        if (input.contains("better estimates") || input.contains("clear goals")) {
            System.out.println("Monster defeated! You may proceed.");
        } else {
            System.out.println("The monster still blocks your path.");
        }
    }
}

