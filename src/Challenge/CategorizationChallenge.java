package Challenge;

import Challenge.questions.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategorizationChallenge implements ChallengeStrategy {
    public Map<String, Question> Questions = new HashMap<>();

    public CategorizationChallenge() {
        Questions.put("SprintPlanningQ5", new Question(
                "Match the concept to its purpose:\n" +
                        "1. Sprint Goal\n2. Task Breakdown\n3. Estimation\n\n" +
                        "A. Helps divide work into smaller, doable parts\n" +
                        "B. Helps the team decide what fits in the sprint\n" +
                        "C. Helps the team understand the purpose of the sprint",
                "-+"
        ));

        Questions.put("DailyScrumQ1", new Question(
                "Your team consists of:\n" +
                        "A. Kaj (Developer)\nB. Jorge (Tester)\nC. Szymon (Designer)\nD. Lex (Scrum Master)\n\n" +
                        "Match the following status updates to the correct team members:\n" +
                        "1. 'I conducted the sprint planning meeting.'\n" +
                        "2. 'I have completed writing two test cases.'\n" +
                        "3. 'I am still working on the API and fixing a bug.'\n" +
                        "4. 'I have finalized the new dashboard design.'\n\n" +
                        "Enter the name of the team member next to the corresponding status update:",
                "1: D, 2: B, 3: A, 4: C"
        ));

        Questions.put("ScrumBoardQ1", new Question(
                "Categorize the following backlog items:\n" +
                        "1. Implement user authentication and authorization.\n" +
                        "2. As a user, I want to reset my password so that I can regain access.\n" +
                        "3. As an admin, I want to view all registered users.\n" +
                        "4. Create a login page UI.\n" +
                        "5. Write unit tests for the authentication module.\n\n" +
                        "Enter epic, user story or task next to each backlog item:",
                "1: Epic, 2: User Story, 3: User Story, 4: Task, 5: Task"
        ));

        Questions.put("ScrumBoardQ4", new Question(
                "Match the board column to its meaning:\n" +
                        "1. To Do\n2. In Progress\n3. Done\n\n" +
                        "A. Task finished\nB. Task started\nC. Task ready",
                "1: C, 2: B, 3: A"
        ));

        Questions.put("SprintReviewQ1", new Question(
                "Determine the impact level of the following feedback from stakeholders:\n" +
                        "1. The layout of the dashboard is confusing and complicated.\n" +
                        "2. The font size is slightly smaller than expected.\n" +
                        "3. The login functionality is not working for a majority of users.\n\n" +
                        "Assign the correct impact level (High, Medium, Low) to each feedback:",
                "1: Medium, 2: Low, 3: High"
        ));

        Questions.put("SprintRetrospectiveQ1", new Question(
                "Below are some situations that occurred in a team. Indicate what the team can learn from each situation:\n" +
                        "1. A team member consistently delivered their tasks late.\n" +
                        "2. The team had a heated argument during a meeting, which negatively affected morale.\n" +
                        "3. The team completed the sprint successfully but realized they forgot to update the stakeholder on the progress.\n" +
                        "4. A team member took on too many tasks, leading to burnout and incomplete work.\n" +
                        "5. The team failed to identify a critical bug, which caused issues in production.\n\n" +
                        "What lesson do you learn from each situation:\n" +
                        "A: 'Time Management'\nB: 'Conflict Resolution'\nC: 'Workload Management'\nD: 'Quality Assurance'\nE: 'Stakeholder Communication'\n\n" +
                        "Enter the letter corresponding to the lesson learned next to each situation:",
                "1: A, 2: B, 3: E, 4: C, 5: D"
        ));

        Questions.put("BossRoomQ5", new Question(
                "Match the Scrum event with its main goal:\n" +
                        "1. Sprint Planning\n2. Daily Scrum\n3. Sprint Retrospective\n\n" +
                        "A. Plan sprint work\nB. Inspect and adapt process\nC. Synchronize daily work",
                "1: A, 2: C, 3: B"
        ));
    }


    @Override
    public void showQuestion(String name) {
        Question question = Questions.get(name);
        if (question != null) {
            System.out.println(question.getQuestion());
        } else {
            System.out.println("Question not found.");
        }
    }

    @Override
    public boolean checkAnswer(String name, List<String> userAnswers) {
        Question question = Questions.get(name);
        if (question != null) {
            String correctAnswer = question.getAnswer().trim();

            List<String> formattedUserAnswers = new ArrayList<>();
            for (int i = 0; i < userAnswers.size(); i++) {
                formattedUserAnswers.add((i + 1) + ": " + userAnswers.get(i).trim());
            }

            List<String> processedCorrectAnswers = new ArrayList<>();
            String[] correctParts = correctAnswer.split(",\\s*");
            for (String part : correctParts) {
                if (!part.trim().isEmpty()) {
                    processedCorrectAnswers.add(part.trim());
                }
            }

            boolean allMatch = true;
            for (int i = 0; i < formattedUserAnswers.size(); i++) {
                if (!formattedUserAnswers.get(i).equalsIgnoreCase(processedCorrectAnswers.get(i))) {
                    allMatch = false;
                    break;
                }
            }

            return allMatch;
        } else {
            System.out.println("Question not found.");
            return false;
        }
    }
}
