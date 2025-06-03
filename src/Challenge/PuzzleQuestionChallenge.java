package Challenge;

import Challenge.questions.Question;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class PuzzleQuestionChallenge implements ChallengeStrategy {
    Map<String, Question> Questions = new HashMap<>();

    public PuzzleQuestionChallenge() {
        Questions.put("Room2Question1", new Question("""
                Your team consists of:
                A. Kaj (Developer)
                B. Jorge (Tester)
                C. Szymon (Designer)
                D. Lex (Scrum Master)

                Match the following status updates to the correct team members:
                1. 'I conducted the sprint planning meeting.'
                2. 'I have completed writing two test cases.'
                3. 'I am still working on the API and fixing a bug.'
                4. 'I have finalized the new dashboard design.'

                Enter the name of the team member next to the corresponding status update:""",
                "1: D, 2: B, 3: A, 4: C"));

        Questions.put("Room4Question1", new Question("""
                Determine the impact level of the following feedback from stakeholders:
                1. The layout of the dashboard is confusing and complicated.
                2. The font size is slightly smaller than expected.
                3. The login functionality is not working for a majority of users.

                Assign the correct impact level (High, Medium, Low) to each feedback:""",
                "1: High, 2: Medium, 3: High"));

        Questions.put("Room5Question1", new Question("""
                Below are some situations that occurred in a team. Indicate what the team can learn from each situation:
                1. A team member consistently delivered their tasks late.
                2. The team had a heated argument during a meeting, which negatively affected morale.
                3. The team completed the sprint successfully but realized they forgot to update the stakeholder on the progress.
                4. A team member took on too many tasks, leading to burnout and incomplete work.
                5. The team failed to identify a critical bug, which caused issues in production.

                What lesson do you learn from each situation: A: 'Time Management', B: 'Conflict Resolution', C: 'Workload Management', D: 'Quality Assurance', E: 'Stakeholder Communication'
                Enter the letter corresponding to the lesson learned next to each situation:""",
                "1: A, 2: B, 3: E, 4: C, 5: D"));
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
            String[] correctAnswers = correctAnswer.split(",");

            if (userAnswers.size() != correctAnswers.length) {
                return false;
            }

            for (int i = 0; i < correctAnswers.length; i++) {
                String numberedAnswer = (i + 1) + ": " + userAnswers.get(i).trim();
                String numberedCorrectAnswer = correctAnswers[i].trim();

                if (!numberedAnswer.equalsIgnoreCase(numberedCorrectAnswer)) {
                    return false;
                }
            }

            return true;
        } else {
            System.out.println("Question not found.");
            return false;
        }
    }
}
