package classes.impediments;

import classes.nonrooms.Player;
import classes.rooms.Room;

/**
 * A simple test class for the Monster functionality.
 * This class demonstrates how the Monster class works with the existing codebase.
 */
public class MonsterTest {

    public static void main(String[] args) {
        System.out.println("=== Testing Monster Creation ===");
        testMonsterCreation();

        System.out.println("\n=== Testing Monster Attack ===");
        testMonsterAttack();

        System.out.println("\n=== Testing Monster Factory ===");
        testMonsterFactory();
    }

    private static void testMonsterCreation() {
        Monster monster = new Monster("Test Monster", "A test monster", 10, 50);
        System.out.println("Monster name: " + monster.getName());
        System.out.println("Monster description: " + monster.getDescription());
        System.out.println("Monster attack power: " + monster.getAttackPower());
        System.out.println("Monster health points: " + monster.getHealthPoints());

        // Verify values
        assert "Test Monster".equals(monster.getName()) : "Name should be 'Test Monster'";
        assert "A test monster".equals(monster.getDescription()) : "Description should be 'A test monster'";
        assert 10 == monster.getAttackPower() : "Attack power should be 10";
        assert 50 == monster.getHealthPoints() : "Health points should be 50";

        System.out.println("Monster creation test passed!");
    }

    private static void testMonsterAttack() {
        Monster monster = new Monster("Test Monster", "A test monster", 10, 50);
        Player player = new Player(1, 100, null, "Test Player");

        // Initial HP
        System.out.println("Initial player HP: " + player.getHp());
        assert 100 == player.getHp() : "Initial HP should be 100";

        // Attack player
        monster.attack(player);

        // HP should be reduced by attackPower
        System.out.println("Player HP after attack: " + player.getHp());
        assert 90 == player.getHp() : "HP should be reduced to 90";

        System.out.println("Monster attack test passed!");
    }

    private static void testMonsterFactory() {
        // Test creating specific monsters
        Monster bugMonster = ImpedimentFactory.createBugMonster();
        System.out.println("Bug Monster name: " + bugMonster.getName());
        assert "Bug Monster".equals(bugMonster.getName()) : "Name should be 'Bug Monster'";

        Monster deadlineMonster = ImpedimentFactory.createDeadlineMonster();
        System.out.println("Deadline Monster name: " + deadlineMonster.getName());
        assert "Deadline Monster".equals(deadlineMonster.getName()) : "Name should be 'Deadline Monster'";

        // Test random monster creation
        Monster randomMonster = ImpedimentFactory.createRandomMonster();
        System.out.println("Random Monster name: " + randomMonster.getName());
        assert randomMonster != null : "Random monster should not be null";

        // Test that monster is included in random impediment selection
        System.out.println("Testing random impediment creation (looking for monsters)...");
        boolean foundMonster = false;
        for (int i = 0; i < 50; i++) {
            Impediment impediment = ImpedimentFactory.createRandomImpediment();
            if (impediment instanceof Monster) {
                System.out.println("Found a monster: " + impediment.getName());
                foundMonster = true;
                break;
            }
        }
        assert foundMonster : "Should have found at least one monster in 50 tries";

        System.out.println("Monster factory test passed!");
    }
}
