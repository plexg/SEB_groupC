package classes.impediments;

import java.util.Random;

public class ImpedimentFactory {
    private static final Random random = new Random();

    public static Monster createBugMonster() {
        return new Monster(
            "Bug Monster",
            "A terrifying creature made of unresolved bugs that threatens your code quality!",
            15,
            50
        );
    }

    public static Monster createDeadlineMonster() {
        return new Monster(
            "Deadline Monster",
            "A looming creature that grows stronger as project deadlines approach!",
            20,
            60
        );
    }

    public static Monster createRandomMonster() {
        int type = random.nextInt(2);
        switch (type) {
            case 0:
                return createBugMonster();
            case 1:
                return createDeadlineMonster();
            default:
                return createBugMonster();
        }
    }
}
