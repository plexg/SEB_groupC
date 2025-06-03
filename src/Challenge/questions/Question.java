package Challenge.questions;

import Challenge.ChallengeStrategy;

public class Question implements ChallengeStrategy {
    String question;
    String answer;

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public void present() {
        System.out.println("Question: " + question);
    }

    @Override
    public boolean check(String userAnswer) {
        return userAnswer.equalsIgnoreCase(answer);
    }
}
