package classes.main;

import java.util.Scanner;
import classes.nonrooms.Game;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Game game = new Game();
        game.startGame(sc);
    }
}