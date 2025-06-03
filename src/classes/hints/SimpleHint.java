package classes.hints;

public class SimpleHint implements Hint {
    private final String type;
    private final String message;

    public SimpleHint(String type, String message) {
        this.type = type;
        this.message = message;
    }

    @Override
    public String getHint() {
        return message;
    }

    public String getType() {
        return type;
    }
}