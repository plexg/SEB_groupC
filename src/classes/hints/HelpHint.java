
package classes.hints;

public class HelpHint implements Hint {
    private final String hint;

    public HelpHint(String hint) {
        this.hint = hint;
    }

    @Override
    public String getHint() {
        return hint;
    }
}
