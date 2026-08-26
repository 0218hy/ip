/** Identifies supported command words and extracts their user-supplied arguments. */
public enum CommandType {
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    DELETE("delete"),
    BYE("bye"),
    UNKNOWN("");

    private final String keyword;

    CommandType(String keyword) {
        this.keyword = keyword;
    }

    public static CommandType from(String command) {
        if (command == null) {
            return UNKNOWN;
        }

        String trimmedCommand = command.trim();
        for (CommandType type : values()) {
            if (type == UNKNOWN) {
                continue;
            }
            if (trimmedCommand.equals(type.keyword)
                    || trimmedCommand.startsWith(type.keyword + " ")) {
                return type;
            }
        }
        return UNKNOWN;
    }

    public String argumentFrom(String command) {
        return command.trim().substring(keyword.length()).trim();
    }
}
