package classes.rooms;

public abstract class Room {
    private int id;
    private String name;

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

    public void triggerMonster() {
    }
}