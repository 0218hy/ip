package pebby.task;

/** Represents the shared completion state and description of a task. */
public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

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

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /** Returns whether this task has been completed. */
    public boolean isDone() {
        return isDone;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
