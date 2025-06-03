package Challenge.questions;

import classes.impediments.monsters.CoffeeMonster;
import classes.items.Pencil;
import classes.nonrooms.Player;

import java.util.Scanner;

public class CoffeeMonsterQuestions {
    CoffeeMonster coffeeMonster = new CoffeeMonster();
    Pencil pencil = new Pencil();
    Scanner input = new Scanner(System.in);

    public void askQuestion(Question question, Player player) {
        System.out.println("====== MONSTER QUESTION ======");
        System.out.println(question.getQuestion());
        String answer1 = input.nextLine().trim().toLowerCase();

        if (question.check(answer1)) {
            System.out.println("Correct.");
            pencil.attack(coffeeMonster);
        } else {
            System.out.println("Incorrect. The correct answer is: " + question.getAnswer());
            System.out.println("The Clock Monster attacks!");
            coffeeMonster.attack(player);
        }
    }
}
