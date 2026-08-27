import java.util.List;

/**
 * Represents one action that Pebby can perform in response to a user command.
 *
 * <p>Subclasses contain the behavior for one command, keeping command-specific
 * work out of Pebby's main loop.</p>
 */
public abstract class Command {
    /** Performs this command using the current task list and UI. */
    public abstract void execute(List<Task> tasks, Ui ui);

    /** Returns whether this command should end Pebby's command loop. */
    public boolean isExit() {
        return false;
    }
}
