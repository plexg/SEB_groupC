package Challenge;

import java.util.List;

public class OpenQuestionChallenge implements ChallengeStrategy {
    @Override
    public void showQuestion(String question) {
        System.out.println(question);
    }

    @Override
    public boolean checkAnswer(String question, List<String> userAnswers) {
        if (userAnswers.size() != 1) {
            return false;
        }
        return userAnswers.get(0).equalsIgnoreCase(question);
    }
}