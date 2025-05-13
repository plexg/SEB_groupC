package classes.rooms;


public abstract class Room {

    public final void playRoom() {
        showIntroduction();
        presentChallenge();
        boolean correct = checkAnswer();
        if (correct) {
            showResult();
            giveFeedback();
        } else {
            triggerMonster();
        }

    }

    protected abstract void showIntroduction();
    protected abstract void presentChallenge();
    protected abstract boolean checkAnswer();
    protected abstract void showResult();
    protected abstract void giveFeedback();

    protected void triggerMonster() {
        System.out.println("You triggered an obstacle! Solve it before continuing.");
    }
}

