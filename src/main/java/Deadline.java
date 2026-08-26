/** Represents a task that must be completed by a supplied deadline. */
public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /** Returns the deadline text supplied by the user. */
    public String getBy() {
        return by;
    }

    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + by + ")";
    }
}
