package Challenge.questions;

import classes.impediments.monsters.ClockMonster;
import classes.items.Staplergun;
import classes.nonrooms.Player;

import java.util.Scanner;

public class ClockMonsterQuestions {
    Scanner input = new Scanner(System.in);
    ClockMonster clockMonster = new ClockMonster();
    Staplergun staplergun = new Staplergun();
    Question question1 = new Question("Who was the president of the United States in 3167?", "Umlaki Escaldu");
    Question question2 = new Question("In what year did the Boston Tea Party take place?", "1773");

    public void askQuestion(Question question, Player player) {
        System.out.println("====== MONSTER QUESTION ======");
        System.out.println(question.getQuestion());
        String answer1 = input.nextLine().trim().toLowerCase();

        if (question.check(answer1)) {
            System.out.println("Correct.");
            staplergun.attack(clockMonster);
        } else {
            System.out.println("Incorrect. The correct answer is: " + question.getAnswer());
            System.out.println("The Clock Monster attacks!");
            clockMonster.attack(player);
        }
    }
}
