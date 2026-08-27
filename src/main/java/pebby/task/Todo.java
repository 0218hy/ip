package pebby.task;

/** Represents a task with no date or time information. */
public class Todo extends Task {
    /** Creates an incomplete todo with the supplied description. */
    public Todo(String description) {
        super(description);
    }

    /** Returns the user-facing representation of this todo. */
    @Override
    public String toString() {
        return "[T] " + super.toString();
    }
}
