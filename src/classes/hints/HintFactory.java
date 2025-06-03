package classes.hints;

import java.util.Random;

public class HintFactory {
    public static Hint getRandomHint(String context) {
        Random rand = new Random();
        switch (context) {
            case "SprintRetrospectiveRoom":
                if (rand.nextBoolean()) {
                    return createHint("help", "Match each situation to the lesson: time, conflict, communication, workload, or quality.");
                } else {
                    return createHint("funny", "If in doubt, just say 'communication'—it solves everything in retros!");
                }
            case "ScrumBoardRoom":
                if (rand.nextBoolean()) {
                    return createHint("help", "Epics are large features, user stories describe user needs, and tasks are small actionable items.");
                } else {
                    return createHint("funny", "If you can't tell a task from an epic, maybe it's time for a coffee break!");
                }
            case "SprintPlanningRoom":
                if (rand.nextBoolean()) {
                    return createHint("help", "Focus on tasks that are small and essential for a first sprint.");
                } else {
                    return createHint("funny", "Even the best developers need coffee breaks. Maybe your answer does too!");
                }
            case "DailyScrumRoom":
                if (rand.nextBoolean()) {
                    return createHint("help", "Each team member should give a brief update on their work, challenges, and plans.");
                } else {
                    return createHint("funny", "Remember, the daily scrum is not a daily stand-up comedy show!");
                }
            case "SprintReviewRoom":
                if (rand.nextBoolean()) {
                    return createHint("help", "Demonstrate completed work and gather feedback from stakeholders.");
                } else {
                    return createHint("funny", "If your demo fails, just say it's a feature, not a bug!");
                }
            default:
                return createHint("help", "Default hint.");
        }
    }

    public static Hint createHint(String type, String message) {
        return new SimpleHint(type, message);
    }
}