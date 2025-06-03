package classes.impediments;

import classes.nonrooms.Player;

public class Impediment {
    private String name;
    private String description;
    private int hpImpact;

    public Impediment(String name, String description, int hpImpact) {
        this.name = name;
        this.description = description;
        this.hpImpact = hpImpact;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getHpImpact() {
        return hpImpact;
    }

    public void applyTo(Player player) {
        System.out.println("=== IMPEDIMENT ENCOUNTERED ===");
        System.out.println(name + ": " + description);

        if (hpImpact != 0) {
            int currentHp = player.getHp();
            player.setHp(currentHp + hpImpact);

            if (hpImpact < 0) {
                System.out.println("You lost " + Math.abs(hpImpact) + " HP!");
            } else {
                System.out.println("You gained " + hpImpact + " HP!");
            }
        }

        System.out.println("===============================");
    }
}
