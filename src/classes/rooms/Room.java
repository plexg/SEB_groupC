package classes.rooms;

import classes.hints.Hint;

public abstract class Room implements Hint {
    private int id;
    private String name;
    private String hint;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    @Override
    public String getHint() {
        return this.hint;
    }

    public final void playRoom() {
        showIntroduction();
        presentChallenge();
        boolean correct = checkAnswer();
        if (correct) {
            giveFeedback();
        } else {
            triggerMonster();
        }
    }

    public abstract void showIntroduction();
    public abstract void presentChallenge();
    public abstract boolean checkAnswer();
    public abstract void giveFeedback();
    public abstract void searchRoom();

    public void triggerMonster() {
    }
}