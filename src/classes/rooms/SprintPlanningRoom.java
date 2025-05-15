package classes.rooms;
import java.util.Scanner;

public class SprintPlanningRoom extends Room {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    protected void showIntroduction() {
        System.out.println("Welcomee to the Sprint Planning Room!");
    }

    @Override
    protected void presentChallenge() {
        System.out.println("Which tasks fit in a 2 week sprint?");
    }

    @Override
    protected boolean checkAnswer() {
        String input = scanner.nextLine().toLowerCase();
        return input.contains("task a") && input.contains("task b") && !input.contains("task d");
    }

    @Override
    protected void showResult() {
        System.out.println("Good! Your sprint planning is realistic.");
    }

    @Override
    protected void giveFeedback() {
        System.out.println("Remember to size your tasks appropriately!");
    }

    @Override
    protected void triggerMonster() {
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

