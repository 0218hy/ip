package pebby.task;

/** Represents the shared completion state and description of a task. */
public class Task {
    /** The user-provided text describing this task. */
    protected String description;
    /** Whether this task has been completed. */
    protected boolean isDone;

    /** Creates an incomplete task with the supplied description. */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Marks this task as complete. */
    public void markAsDone() {
        this.isDone = true;
    }

    /** Marks this task as incomplete. */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /** Restores this task's completion state from saved data. */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    /** Returns the text that describes this task. */
    public String getDescription() {
        return description;
    }

    /** Returns {@code X} for a completed task and a space otherwise. */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /** Returns whether this task has been completed. */
    public boolean isDone() {
        return isDone;
    }

    /** Returns the user-facing representation of this task. */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
