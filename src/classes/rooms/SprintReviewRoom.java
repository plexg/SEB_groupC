package classes.rooms;

import Challenge.ChallengeStrategy;

import java.util.Scanner;

public class SprintReviewRoom extends Room {
    private String userInput;
    private final ChallengeStrategy challenge = new ChallengeStrategy() {
        @Override
        public void present() {

        }

        @Override
        public boolean check(String input) {
            return false;
        }
    };

    @Override
    protected void showIntroduction() {
        System.out.println("Welcome to the Sprint Planning Room!");
    }

    @Override
    protected void presentChallenge() {
        challenge.present();
    }

    @Override
    protected boolean checkAnswer() {
        Scanner scanner = new Scanner(System.in);
        userInput = scanner.nextLine();
        return challenge.check(userInput);
    }

    @Override
    protected void showResult() {
        System.out.println("Correct! Your sprint planning is solid.");
    }

    @Override
    protected void giveFeedback() {
        System.out.println("Great job managing your sprint scope.");
    }
}
