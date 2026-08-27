/**
 * Holds the command type and argument extracted from one line of user input.
 *
 * <p>Keeping these values together prevents other classes from repeatedly
 * splitting the original command text.</p>
 */
public class ParsedCommand {
    private final CommandType type;
    private final String argument;

    /** Creates a parsed command with its recognised type and remaining text. */
    public ParsedCommand(CommandType type, String argument) {
        this.type = type;
        this.argument = argument;
    }

    /** Returns the recognised command type. */
    public CommandType getType() {
        return type;
    }

    /** Returns the text after the command word, without surrounding spaces. */
    public String getArgument() {
        return argument;
    }
}
