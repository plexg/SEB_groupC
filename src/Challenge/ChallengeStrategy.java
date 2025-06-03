package Challenge;

import java.util.List;

public interface ChallengeStrategy {
    boolean checkAnswer(String questionId, List<String> playerAnswers);
    void showQuestion(String questionId);
}