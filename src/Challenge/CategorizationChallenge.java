package Challenge;

import Challenge.questions.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategorizationChallenge implements ChallengeStrategy {
    Map<String, Question> Questions = new HashMap<>();

    public CategorizationChallenge() {
        Questions.put("Room3Question1", new Question("Categorize the following backlog items:\n" +
                "1. Implement user authentication and authorization.\n" +
                "2. As a user, I want to reset my password so that I can regain access.\n" +
                "3. As an admin, I want to view all registered users.\n" +
                "4. Create a login page UI.\n" +
                "5. Write unit tests for the authentication module.\n" +
                "\n" +
                "Enter the numbers of the items and their categories, for example: 1:epic, 2:user story, ...",
                "1: epic, 2: user story, 3: user story, 4: task, 5: task"));
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
