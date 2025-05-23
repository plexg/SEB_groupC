package classes.hints;

public class HintFactory {
    public static Hint createHint(String type, String hintText) {
        switch (type.toLowerCase()) {
            case "funny":
                return new FunnyHint(hintText);
            case "help":
                return new HelpHint(hintText);
            default:
                throw new IllegalArgumentException("Unknown hint type: " + type);
        }
    }
}