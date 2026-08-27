package pebby.parser;

/** Identifies the command words Pebby supports. */
public enum CommandType {
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    FIND("find"),
    DELETE("delete"),
    BYE("bye"),
    UNKNOWN("");

    private final String keyword;

    /** Associates a command type with the keyword users enter. */
    CommandType(String keyword) {
        this.keyword = keyword;
    }

    /** Returns the type matching the first word of a command, or {@link #UNKNOWN} when none matches. */
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

    /** Returns the trimmed text following this type's command word. */
    public String argumentFrom(String command) {
        return command.trim().substring(keyword.length()).trim();
    }
}
