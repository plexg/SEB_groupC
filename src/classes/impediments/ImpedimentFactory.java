package classes.impediments;

import java.util.Random;

/**
 * Factory class for creating different types of impediments and monsters.
 * This class provides methods to create predefined impediments, monsters, or random ones.
 */
public class ImpedimentFactory {
    private static final Random random = new Random();

    /**
     * Creates a technical debt impediment.
     * 
     * @return A technical debt impediment
     */
    public static Impediment createTechnicalDebtImpediment() {
        return new Impediment(
            "Technical Debt",
            "Your previous shortcuts have caught up with you! You need to spend time fixing old code.",
            -10
        );
    }

    /**
     * Creates a merge conflict impediment.
     * 
     * @return A merge conflict impediment
     */
    public static Impediment createMergeConflictImpediment() {
        return new Impediment(
            "Merge Conflict",
            "Your code conflicts with another team member's changes. Time to resolve the conflicts!",
            -15
        );
    }

    /**
     * Creates a scope creep impediment.
     * 
     * @return A scope creep impediment
     */
    public static Impediment createScopeCreepImpediment() {
        return new Impediment(
            "Scope Creep",
            "The requirements have changed mid-sprint! You need to adapt your work.",
            -20
        );
    }

    /**
     * Creates a team communication impediment.
     * 
     * @return A team communication impediment
     */
    public static Impediment createTeamCommunicationImpediment() {
        return new Impediment(
            "Communication Breakdown",
            "There's been a misunderstanding in the team about what needs to be done.",
            -10
        );
    }

    /**
     * Creates a production bug impediment.
     * 
     * @return A production bug impediment
     */
    public static Impediment createProductionBugImpediment() {
        return new Impediment(
            "Production Bug",
            "A critical bug has been found in production! Drop everything and fix it now!",
            -25
        );
    }

    /**
     * Creates a bug monster.
     * 
     * @return A bug monster
     */
    public static Monster createBugMonster() {
        return new Monster(
            "Bug Monster",
            "A terrifying creature made of unresolved bugs that threatens your code quality!",
            15,
            50
        );
    }

    /**
     * Creates a deadline monster.
     * 
     * @return A deadline monster
     */
    public static Monster createDeadlineMonster() {
        return new Monster(
            "Deadline Monster",
            "A looming creature that grows stronger as project deadlines approach!",
            20,
            60
        );
    }

    /**
     * Creates a random monster from the predefined types.
     * 
     * @return A random monster
     */
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

    /**
     * Creates a random impediment from the predefined types.
     * This includes both regular impediments and monsters.
     * 
     * @return A random impediment or monster
     */
    public static Impediment createRandomImpediment() {
        int type = random.nextInt(7);
        switch (type) {
            case 0:
                return createTechnicalDebtImpediment();
            case 1:
                return createMergeConflictImpediment();
            case 2:
                return createScopeCreepImpediment();
            case 3:
                return createTeamCommunicationImpediment();
            case 4:
                return createProductionBugImpediment();
            case 5:
                return createBugMonster();
            case 6:
                return createDeadlineMonster();
            default:
                return createTechnicalDebtImpediment();
        }
    }
}
