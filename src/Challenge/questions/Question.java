package Challenge.questions;

    import Challenge.ChallengeStrategy;
    import java.util.List;

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

        public void showQuestion(String name) {
            System.out.println("Question: " + question);
        }

        public boolean check(String userAnswer) {
            return userAnswer.equalsIgnoreCase(answer);
        }

        @Override
        public boolean checkAnswer(String name, List<String> userAnswers) {
            if (userAnswers.size() != 1) {
                return false;
            }
            return userAnswers.get(0).equalsIgnoreCase(answer);
        }
    }