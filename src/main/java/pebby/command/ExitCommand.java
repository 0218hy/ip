package pebby.command;

import pebby.storage.Storage;
import pebby.task.TaskList;
import pebby.ui.Ui;

/** Ends Pebby's command loop when the user enters {@code bye}. */
public class ExitCommand extends Command {
    /** Does not need to change tasks or display a message before Pebby closes. */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        // Pebby displays the farewell after the command loop ends.
    }

    /** Signals that Pebby should leave its command loop. */
    @Override
    public boolean isExit() {
        return true;
    }
}
