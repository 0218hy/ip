package pebby;

import java.io.IOException;
import java.time.LocalDate;

import pebby.command.AddCommand;
import pebby.command.Command;
import pebby.command.DeleteCommand;
import pebby.command.ExitCommand;
import pebby.command.ListCommand;
import pebby.parser.CommandType;
import pebby.parser.ParsedCommand;
import pebby.parser.Parser;
import pebby.storage.Storage;
import pebby.task.Deadline;
import pebby.task.Task;
import pebby.task.TaskList;
import pebby.ui.Ui;

/** A command-line task chatbot that keeps tasks in a local data file. */
public class Pebby {
    private static TaskList tasks = new TaskList();
    private static final Storage storage = new Storage();

//    No longer usse pure Task
//    public static String addTask(String description) {
//        Task newTask = new Task(description);
//        tasks.add(newTask);
//        taskCount++;
//        return "added: " + newTask.description + '\n';
//    }

    public static String markTask(int taskNo) {
        Task task = tasks.markAsDone(taskNo);
        String output = "Nice! I've marked this task as done: \n";
        return output + "  " + task.toString() + saveTasks();
    }

    public static String unmarkTask(int taskNo) {
        Task task = tasks.markAsNotDone(taskNo);
        String output = "OK, I've marked this task as not done yet: \n";
        return output + "  " + task.toString() + saveTasks();
    }

    /** Finds and displays every deadline that occurs on the date supplied by the user. */
    public static String handleFind(String dateText) {
        try {
            if (dateText.isBlank()) {
                throw new IllegalArgumentException("Please provide a date to search for.");
            }

            LocalDate date = Deadline.parseDate(dateText);
            StringBuilder matches = new StringBuilder("Deadlines on ")
                    .append(date).append(":\n");
            int matchCount = 0;
            for (int index = 0; index < tasks.size(); index++) {
                Task task = tasks.get(index);
                if (task instanceof Deadline && ((Deadline) task).isOn(date)) {
                    matches.append(index + 1).append(". ").append(task).append('\n');
                    matchCount++;
                }
            }
            if (matchCount == 0) {
                return "No deadlines found on " + date + ".\n";
            }
            return matches.toString();
        } catch (IllegalArgumentException exception) {
            return "Invalid find: " + exception.getMessage();
        }
    }

    /** Saves the current list and turns an I/O failure into a helpful UI message. */
    private static String saveTasks() {
        try {
            storage.save(tasks.asList());
            return "";
        } catch (IOException | IllegalArgumentException exception) {
            return "\nWarning: your task was changed, but Pebby could not save it: "
                    + exception.getMessage();
        }
    }

    /** Loads prior tasks without allowing a missing or damaged file to stop Pebby. */
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

    /** Validates the 1-based task number used by mark, unmark, and delete commands. */
    private static int taskIndexFrom(String argument) {
        try {
            int taskNumber = Integer.parseInt(argument);
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                throw new IllegalArgumentException("Please choose a task number from 1 to " + tasks.size() + ".");
            }
            return taskNumber - 1;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Please provide a whole task number.");
        }
    }

    public static void main(String[] args) {
        Ui ui = new Ui();
        String loadMessage = loadTasks();
        ui.showWelcome();
        if (!loadMessage.isEmpty()) {
            ui.showLine(loadMessage);
            ui.showSeparator();
        }

        // Getting user input
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
