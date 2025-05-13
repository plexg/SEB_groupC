package Challenge;

import java.util.Scanner;

public class MultipleChoiceChallenge implements Challenge.ChallengeStrategy {

    Scanner scanner = new Scanner(System.in);

    private String correctAnswer = "b";

    @Override
    public void present() {
        System.out.println("What is the role of the Product Owner?");
        System.out.println("a) Lead developer\nb) Owns the product backlog\nc) Scrum Master");
    }

    @Override
    public boolean check(String input) {
        return input.trim().equalsIgnoreCase(correctAnswer);
    }
}
