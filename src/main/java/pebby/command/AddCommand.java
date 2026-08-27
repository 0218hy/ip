package pebby.command;

import pebby.parser.CommandType;
import pebby.storage.Storage;
import pebby.task.Deadline;
import pebby.task.Event;
import pebby.task.Task;
import pebby.task.TaskList;
import pebby.ui.Ui;

/** Adds a todo, deadline, or event task described by the user. */
public class AddCommand extends Command {
    private final CommandType type;
    private final String argument;

    /** Creates an add command for the recognised task type and its argument. */
    public AddCommand(CommandType type, String argument) {
        this.type = type;
        this.argument = argument;
    }

    /** Validates the argument, adds the task, saves it, and displays the result. */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            Task task = createTask(tasks);
            String output = "Got it. I've added this task: \n  " + task;
            ui.showLine(output + saveTasks(tasks, storage) + '\n' + taskCountMessage(tasks));
        } catch (IllegalArgumentException exception) {
            ui.showLine("Invalid " + typeName() + ": " + exception.getMessage());
        }
    }

    /** Creates and adds the task represented by this command's type and argument. */
    private Task createTask(TaskList tasks) {
        switch (type) {
        case TODO:
            requireNotBlank(argument, "Please provide a description.");
            return tasks.addTodo(argument);
        case DEADLINE:
            return addDeadline(tasks);
        case EVENT:
            return addEvent(tasks);
        default:
            throw new IllegalArgumentException("Unsupported task type.");
        }
    }

    /** Parses this command's deadline argument and adds the resulting task. */
    private Deadline addDeadline(TaskList tasks) {
        requireNotBlank(argument, "Please provide a description and deadline.");
        int byIndex = argument.indexOf("/by");
        if (byIndex == -1) {
            throw new IllegalArgumentException("Please include /by followed by a deadline.");
        }
        String description = argument.substring(0, byIndex).trim();
        String by = argument.substring(byIndex + "/by".length()).trim();
        requireNotBlank(description, "Please provide a description.");
        requireNotBlank(by, "Please provide a deadline after /by.");
        return tasks.addDeadline(description, Deadline.parseDate(by).toString());
    }

    /** Parses this command's event argument and adds the resulting task. */
    private Event addEvent(TaskList tasks) {
        requireNotBlank(argument, "Please provide a description, start time, and end time.");
        int fromIndex = argument.indexOf("/from");
        int toIndex = argument.indexOf("/to");
        if (fromIndex == -1) {
            throw new IllegalArgumentException("Please include /from followed by a start time.");
        }
        if (toIndex == -1) {
            throw new IllegalArgumentException("Please include /to followed by an end time.");
        }
        if (toIndex < fromIndex) {
            throw new IllegalArgumentException("Please put /from before /to.");
        }
        String description = argument.substring(0, fromIndex).trim();
        String from = argument.substring(fromIndex + "/from".length(), toIndex).trim();
        String to = argument.substring(toIndex + "/to".length()).trim();
        requireNotBlank(description, "Please provide a description.");
        requireNotBlank(from, "Please provide a start time after /from.");
        requireNotBlank(to, "Please provide an end time after /to.");
        return tasks.addEvent(description, from, to);
    }

    /** Rejects blank command fields with the supplied user-facing message. */
    private void requireNotBlank(String value, String message) {
        if (value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    /** Returns Pebby's message stating the current number of tasks. */
    private String taskCountMessage(TaskList tasks) {
        return "Now you have " + tasks.size() + " tasks in the list.";
    }

    /** Returns the user-facing name of the task type for validation errors. */
    private String typeName() {
        switch (type) {
        case TODO:
            return "todo";
        case DEADLINE:
            return "deadline";
        case EVENT:
            return "event";
        default:
            return "task";
        }
    }
}
