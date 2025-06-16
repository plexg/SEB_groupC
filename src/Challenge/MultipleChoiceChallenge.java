package Challenge;

import Challenge.questions.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class MultipleChoiceChallenge implements ChallengeStrategy {
    public Map<String, Question> Questions = new HashMap<>();

    public MultipleChoiceChallenge() {
        Questions.put("SprintPlanningQ1", new Question(
                "Which tasks fit in a 2 week sprint?\n" +
                        "task 1: Add a main-class where you can start your project.\n" +
                        "task 2: Make every class that corresponds with the User.\n" +
                        "task 3: Add an admin GUI that shows different graphs and information about your project.\n" +
                        "task 4: Sort your project in several named packages so it’s uncluttered.\n" +
                        "task 5: Make a Database with the column user, containing userID & username\n\n" +
                        "Enter the numbers of the tasks that fit in a 2 week sprint, separated by commas:",
                "1,4,5"
        ));

        Questions.put("SprintPlanningQ2", new Question(
                "What is the output of a Sprint Planning meeting?\n" +
                        "A. Project budget\nB. Sprint Goal and selected backlog items\nC. Retrospective summary\nD. Team report card\n\n",
                "B"
        ));

        Questions.put("SprintPlanningQ3", new Question(
                "The Product Owner is responsible for defining what the team will work on during Sprint Planning.\n(True/False)\n",
                "True"
        ));

        Questions.put("SprintPlanningQ4", new Question(
                "The _____ _____ helps the team break down and estimate the selected backlog items during Sprint Planning.\n",
                "Scrum Master"
        ));

        Questions.put("SprintPlanningQ6", new Question(
                "Why avoid adding too many tasks in a sprint?\n" +
                        "A. Team gets bored\nB. It’s required by Scrum\nC. Prevent overload\nD. More tasks = faster\n\n",
                "C"
        ));

        Questions.put("SprintPlanningQ7", new Question(
                "How long is a typical sprint in Scrum?\n" +
                        "A. 3 months\nB. 6 weeks\nC. 2–4 weeks\nD. 1 day\n\n",
                "C"
        ));

        Questions.put("DailyScrumQ2", new Question(
                "How long should a Daily Scrum meeting last?\n" +
                        "A. 15 minutes\nB. 1 hour\nC. 30 minutes\nD. 5 minutes\n\n",
                "A"
        ));

        Questions.put("DailyScrumQ3", new Question(
                "Who facilitates the Daily Scrum?\n" +
                        "A. Product Owner\nB. Scrum Master\nC. Team Member\nD. Stakeholder\n\n",
                "B"
        ));

        Questions.put("DailyScrumQ4", new Question(
                "During Daily Scrum, team members answer three questions:\n" +
                        "1. What did I do yesterday?\n" +
                        "2. What will I do today?\n" +
                        "3. Do I have any blockers?\n\n(True/False)\n",
                "True"
        ));

        Questions.put("ScrumBoardQ2", new Question(
                "What is the primary purpose of a Scrum board?\n" +
                        "A. To track project budget\nB. To visualize sprint progress\nC. To assign tasks\nD. To manage team members\n\n",
                "B"
        ));

        Questions.put("ScrumBoardQ3", new Question(
                "The 'Done' column on a Scrum board means a task is completed and tested.\n(True/False)\n",
                "True"
        ));

        Questions.put("ScrumBoardQ5", new Question(
                "Which of the following is NOT a Scrum board column?\n" +
                        "A. To Do\nB. In Progress\nC. Under Review\nD. Done\n\n",
                "C"
        ));

        Questions.put("ScrumBoardQ6", new Question(
                "Fill in the blank:\n" +
                        "The Scrum board helps teams track the __________ of tasks.\n",
                "status"
        ));

        Questions.put("ScrumBoardQ7", new Question(
                "How often should the Scrum board be updated?\n" +
                        "A. Daily\nB. Weekly\nC. Monthly\nD. At sprint end\n\n",
                "A"
        ));

        Questions.put("SprintReviewQ2", new Question(
                "Who attends a Sprint Review meeting?\n" +
                        "A. Only Scrum team\nB. Scrum team and stakeholders\nC. Only Product Owner\nD. Project manager only\n\n",
                "B"
        ));

        Questions.put("SprintReviewQ3", new Question(
                "Sprint Review focuses on product demonstration.\n(True/False)\n",
                "True"
        ));

        Questions.put("SprintRetrospectiveQ2", new Question(
                "Retrospective meetings help improve team processes.\n(True/False)\n",
                "True"
        ));

        Questions.put("BossRoomQ1", new Question(
                "What is the primary role of a Scrum Master in a Scrum team?\n" +
                        "A. To manage the team and assign tasks\nB. To facilitate Scrum ceremonies and remove impediments\n" +
                        "C. To write code and develop features\nD. To test the software and report bugs\n\n",
                "B"
        ));

        Questions.put("BossRoomQ2", new Question(
                "The Product Owner is responsible for:\n" +
                        "A. Managing the development team\nB. Maximizing product value\nC. Writing code\nD. Testing features\n\n",
                "B"
        ));

        Questions.put("BossRoomQ3", new Question(
                "Fill in the blank:\n" +
                        "The __________ maintains the Product Backlog.\n",
                "Product Owner"
        ));

        Questions.put("BossRoomQ4", new Question(
                "A Sprint is time-boxed to 1 month or less.\n(True/False)\n",
                "True"
        ));

        Questions.put("BossRoomQ6", new Question(
                "Fill in the blank:\n" +
                        "The team holds a __________ meeting every day to synchronize activities.\n",
                "Daily Scrum"
        ));

        Questions.put("BossRoomQ7", new Question(
                "Which artifact represents the work to be done in a sprint?\n" +
                        "A. Product Backlog\nB. Sprint Backlog\nC. Increment\nD. Burn-down Chart\n\n",
                "B"
        ));

        Questions.put("BossRoomQ8", new Question(
                "What is a Scrum team's ideal size?\n" +
                        "A. 3-9 members\nB. 10-15 members\nC. 20+ members\nD. 1-2 members\n\n",
                "A"
        ));

        Questions.put("BossRoomQ9", new Question(
                "Fill in the blank:\n" +
                        "The result of a sprint is a __________.\n",
                "Potentially shippable product increment"
        ));

        Questions.put("BossRoomQ10", new Question(
                "The Scrum framework is lightweight and simple.\n(True/False)\n",
                "True"
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

            List<String> trimmedAnswers = new ArrayList<>();
            for (String answer : userAnswers) {
                trimmedAnswers.add(answer.trim());
            }
            String normalizedUserAnswer = String.join(", ", trimmedAnswers);
            return normalizedUserAnswer.equalsIgnoreCase(correctAnswer);
        } else {
            System.out.println("Question not found.");
            return false;
        }
    }
}
