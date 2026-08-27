package pebby;

import java.io.IOException;
import java.util.List;

import pebby.command.AddCommand;
import pebby.command.Command;
import pebby.command.DeleteCommand;
import pebby.command.ExitCommand;
import pebby.command.ListCommand;
import pebby.parser.CommandType;
import pebby.parser.ParsedCommand;
import pebby.parser.Parser;
import pebby.storage.Storage;
import pebby.task.Task;
import pebby.task.TaskList;
import pebby.ui.Ui;

/**
 * A command-line task chatbot that keeps tasks in a local data file.
 */
public class Pebby {
    private static TaskList tasks = new TaskList();
    private static final Storage storage = new Storage();

    /**
     * Marks the task at the specified zero-based index as complete and saves the change.
     */
    public static String markTask(int taskNo) {
        Task task = tasks.markAsDone(taskNo);
        String output = "Nice! I've marked this task as done: \n";
        return output + "  " + task.toString() + saveTasks();
    }

    /**
     * Marks the task at the specified zero-based index as incomplete and saves the change.
     */
    public static String unmarkTask(int taskNo) {
        Task task = tasks.markAsNotDone(taskNo);
        String output = "OK, I've marked this task as not done yet: \n";
        return output + "  " + task.toString() + saveTasks();
    }

    /**
     * Finds and formats tasks whose descriptions contain the supplied keyword.
     */
    public static String handleFind(String keyword) {
        if (keyword.isBlank()) {
            return "Invalid find: Please provide a keyword to search for.";
        }

        List<Task> matchingTasks = tasks.findTasks(keyword);
        if (matchingTasks.isEmpty()) {
            return "No matching tasks found.\n";
        }

        StringBuilder matches = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int index = 0; index < matchingTasks.size(); index++) {
            matches.append(index + 1).append(". ").append(matchingTasks.get(index)).append('\n');
        }
        return matches.toString();
    }

    /**
     * Saves the current list and converts an I/O failure into a helpful UI message.
     */
    private static String saveTasks() {
        try {
            storage.save(tasks.asList());
            return "";
        } catch (IOException | IllegalArgumentException exception) {
            return "\nWarning: your task was changed, but Pebby could not save it: "
                    + exception.getMessage();
        }
    }

    /**
     * Loads prior tasks without allowing a missing or damaged file to stop Pebby.
     */
    private static String loadTasks() {
        try {
            Storage.LoadResult result = storage.load();
            tasks = new TaskList(result.tasks);
            if (result.skippedRecords > 0) {
                return "Warning: ignored " + result.skippedRecords
                        + " invalid saved task record(s).";
            }
            return "";
        } catch (IOException exception) {
            return "Warning: Pebby could not load saved tasks: " + exception.getMessage();
        }
    }

    /**
     * Converts a valid one-based task number into a zero-based index.
     */
    private static int taskIndexFrom(String argument) {
        try {
            int taskNumber = Integer.parseInt(argument);
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                throw new IllegalArgumentException(
                        "Please choose a task number from 1 to " + tasks.size() + ".");
            }
            return taskNumber - 1;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Please provide a whole task number.");
        }
    }

    /**
     * Starts Pebby and processes commands until the user exits.
     *
     * @param args Command-line arguments, which Pebby does not use.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        String loadMessage = loadTasks();
        ui.showWelcome();
        if (!loadMessage.isEmpty()) {
            ui.showLine(loadMessage);
            ui.showSeparator();
        }

        boolean isExit = false;
        while (ui.hasNextCommand() && !isExit) {
            String command = ui.readCommand();
            ParsedCommand parsedCommand = Parser.parse(command);
            CommandType commandType = parsedCommand.getType();
            String argument = parsedCommand.getArgument();
            if (commandType == CommandType.BYE) {
                Command exitCommand = new ExitCommand();
                exitCommand.execute(tasks, ui, storage);
                isExit = exitCommand.isExit();
                continue;
            }

            ui.showSeparator();
            switch (commandType) {
                case LIST:
                    new ListCommand().execute(tasks, ui, storage);
                    break;
                case MARK: {
                    try {
                        ui.showLine(markTask(taskIndexFrom(argument)));
                    } catch (IllegalArgumentException exception) {
                        ui.showLine("Invalid mark: " + exception.getMessage());
                    }
                    break;
                }
                case UNMARK: {
                    try {
                        ui.showLine(unmarkTask(taskIndexFrom(argument)));
                    } catch (IllegalArgumentException exception) {
                        ui.showLine("Invalid unmark: " + exception.getMessage());
                    }
                    break;
                }
                case TODO: {
                    new AddCommand(commandType, argument).execute(tasks, ui, storage);
                    break;
                }
                case DEADLINE: {
                    new AddCommand(commandType, argument).execute(tasks, ui, storage);
                    break;
                }
                case EVENT: {
                    new AddCommand(commandType, argument).execute(tasks, ui, storage);
                    break;
                }
                case FIND: {
                    ui.show(handleFind(argument));
                    break;
                }
                case DELETE: {
                    new DeleteCommand(argument).execute(tasks, ui, storage);
                    break;
                }
                case BYE:
                default:
                    ui.showLine("Hmmm... What does this mean? My pebble brain cant understand.");
            }
            ui.showSeparator();
        }
        ui.showGoodbye();
    }
}
