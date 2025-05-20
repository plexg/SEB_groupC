package classes.hints;

public class FunnyHint implements Hint {
    private final String hint;

    public FunnyHint(String hint) {
        this.hint = hint;
    }

    @Override
    public String getHint() {
        return hint;
    }
}