package pebby.parser;

/**
 * Converts a raw line of user input into a command type and argument.
 */
public class Parser {
    /**
     * Identifies the command word and separates it from the user-supplied argument.
     * Unknown commands have an empty argument because Pebby only displays an error for them.
     */
    public static ParsedCommand parse(String fullCommand) {
        CommandType type = CommandType.from(fullCommand);
        String argument = type == CommandType.UNKNOWN ? "" : type.argumentFrom(fullCommand);
        return new ParsedCommand(type, argument);
    }
}
