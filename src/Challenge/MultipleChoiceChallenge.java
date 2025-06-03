package Challenge;

import Challenge.questions.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class MultipleChoiceChallenge implements ChallengeStrategy {
    Map<String, Question> Questions = new HashMap<>();

    public MultipleChoiceChallenge() {
        Questions.put("Room1Question1", new Question("Which tasks fit in a 2 week sprint?\n" +
                "task 1: Add a main-class where you can start your project.\n" +
                "task 2: Make every class that corresponds with the User.\n" +
                "task 3: Add an admin GUI that shows different graphs and information about your project.\n" +
                "task 4: Sort your project in several named packages so it’s uncluttered.\n" +
                "task 5: Make a Database with the column user, containing userID & username\n" +
                "\n" +
                "Enter the numbers of the tasks that fit in a 2 week sprint, for example: 1, 2, 3, ...:",
                "1, 4, 5"));
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

            List<String> processedUserAnswers = new ArrayList<>();
            for (String answer : userAnswers) {
                if (answer.contains(",")) {
                    String[] parts = answer.split(",\\s*");
                    for (String part : parts) {
                        if (!part.trim().isEmpty()) {
                            processedUserAnswers.add(part.trim());
                        }
                    }
                } else {
                    processedUserAnswers.add(answer.trim());
                }
            }

            List<String> processedCorrectAnswers = new ArrayList<>();
            String[] correctParts = correctAnswer.split(",\\s*");
            for (String part : correctParts) {
                if (!part.trim().isEmpty()) {
                    processedCorrectAnswers.add(part.trim());
                }
            }

            List<String> normalizedUserAnswers = processedUserAnswers.stream()
                    .map(String::trim)
                    .map(answer -> answer.replaceAll("\\s+", " "))
                    .sorted()
                    .toList();

            List<String> normalizedCorrectAnswers = processedCorrectAnswers.stream()
                    .map(String::trim)
                    .map(answer -> answer.replaceAll("\\s+", " "))
                    .sorted()
                    .toList();

            return normalizedUserAnswers.equals(normalizedCorrectAnswers);
        } else {
            System.out.println("Question not found.");
            return false;
        }
    }
}
