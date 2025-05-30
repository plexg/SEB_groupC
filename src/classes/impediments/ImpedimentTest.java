package classes.impediments;

import classes.nonrooms.Player;
import classes.rooms.Room;

/**
 * A simple test class for the Impediment functionality.
 * This class demonstrates how to create and use impediments.
 */
public class ImpedimentTest {
    
    /**
     * Main method to run the test.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create a test player
        Player testPlayer = new Player(1, 100, null, "TestPlayer");
        
        System.out.println("Initial player status:");
        System.out.println(testPlayer.getStatus());
        System.out.println();
        
        // Test each type of impediment
        testImpediment("Technical Debt", testPlayer);
        testImpediment("Merge Conflict", testPlayer);
        testImpediment("Scope Creep", testPlayer);
        testImpediment("Communication Breakdown", testPlayer);
        testImpediment("Production Bug", testPlayer);
        
        // Test random impediment
        System.out.println("\n=== Testing Random Impediment ===");
        Impediment randomImpediment = ImpedimentFactory.createRandomImpediment();
        randomImpediment.applyTo(testPlayer);
        
        System.out.println("\nFinal player status after all impediments:");
        System.out.println(testPlayer.getStatus());
    }
    
    /**
     * Helper method to test a specific impediment type.
     * 
     * @param type The type of impediment to test
     * @param player The player to apply the impediment to
     */
    private static void testImpediment(String type, Player player) {
        System.out.println("=== Testing " + type + " Impediment ===");
        Impediment impediment = null;
        
        switch (type) {
            case "Technical Debt":
                impediment = ImpedimentFactory.createTechnicalDebtImpediment();
                break;
            case "Merge Conflict":
                impediment = ImpedimentFactory.createMergeConflictImpediment();
                break;
            case "Scope Creep":
                impediment = ImpedimentFactory.createScopeCreepImpediment();
                break;
            case "Communication Breakdown":
                impediment = ImpedimentFactory.createTeamCommunicationImpediment();
                break;
            case "Production Bug":
                impediment = ImpedimentFactory.createProductionBugImpediment();
                break;
        }
        
        if (impediment != null) {
            impediment.applyTo(player);
            System.out.println("Player status after " + type + ":");
            System.out.println(player.getStatus());
            System.out.println();
        }
    }
}